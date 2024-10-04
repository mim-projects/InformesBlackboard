sudo yum update -y
sudo yum upgrade -y
sudo yum install -y epel-release wget libxml2 unzip java-1.8.0-openjdk ufw firewalld

# --- Dirs
sudo mkdir /opt/maven/
sudo mkdir /opt/wildfly/
sudo mkdir /opt/downloads/
sudo mkdir /etc/wildfly


# --- Wildfly [1]
sudo wget -P /opt/downloads/ https://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip
sudo unzip /opt/downloads/wildfly-10.1.0.Final.zip /opt/downloads/
sudo mv /opt/downloads/wildfly-10.1.0.Final/* /opt/wildfly/
sudo /opt/wildfly/bin/add-user.sh -u root -p password --silent
sudo echo -e "export WildFly_BIN=/opt/wildfly/bin/\nexport PATH=\$PATH:\$WildFly_BIN" >> ~/.bashrc
source ~/.bashrc
sudo groupadd --system wildfly
sudo useradd -s /sbin/nologin --system -d /opt/wildfly -g wildfly wildfly
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/
sudo cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
sudo chmod +rwx /opt/wildfly/bin/launch.sh
sudo chown -R wildfly:wildfly /opt/wildfly
# Ignore if using containers (indicate properties in *-compose.yml)
sudo sed -i "s/\$WILDFLY_HOME\/bin\/standalone.sh -c \$2 -b \$3/\$WILDFLY_HOME\/bin\/standalone.sh -c \$2 -b \$3 -bmanagement=0.0.0.0/g" /opt/wildfly/bin/launch.sh
sudo systemctl daemon-reload
sudo systemctl start wildfly
sudo systemctl enable wildfly
sudo ss -tunelp | grep 8080
sudo ss -tunelp | grep 9990


# --- Maven
sudo wget -P /opt/downloads/ https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip
sudo unzip /opt/downloads/apache-maven-3.9.9-bin.zip -d /opt/downloads/
sudo mv /opt/downloads/apache-maven-3.9.9/* /opt/maven/
sudo echo -e "export M2_HOME=/opt/maven\nexport PATH=\$M2_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc


# --- Mysql
sudo yum install -y https://dev.mysql.com/get/mysql-8.0.28-1.el9.x86_64.rpm
sudo yum install -y https://dev.mysql.com/get/mysql-community-server-8.0.28-1.el8.x86_64.rpm
sudo yum install -y https://dev.mysql.com/get/mysql-community-release-el9-1.noarch.rpm
sudo yum install -y https://dev.mysql.com/get/mysql84-community-release-el9-1.noarch.rpm
sudo yum repolist enabled | grep mysql
sudo yum install -y mysql-community-server
sudo systemctl set-environment MYSQLD_OPTS="--skip-grant-tables"
sudo systemctl start mysqld
sudo systemctl enable mysqld
sudo mysql -u root
# > FLUSH PRIVILEGES;
# > ALTER USER 'root'@'localhost' IDENTIFIED BY '20$_iguGb';
sudo systemctl stop mysqld
sudo systemctl unset-environment MYSQLD_OPTS
sudo systemctl start mysqld
sudo sed -i 's/[mysqld]/[mysqld]\nport=3306/g' /etc/my.cnf
sudo netstat -tuln | grep 3306


# --- Wilfly/Mysql Datasource
sudo mkdir /opt/wildfly/modules/system/layers/base/com/mysql
sudo mkdir /opt/wildfly/modules/system/layers/base/com/mysql/main
sudo wget -P /opt/downloads/ https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.17/mysql-connector-java-8.0.17.jar
sudo cp /opt/downloads/mysql-connector-java-8.0.17.jar /opt/wildfly/modules/system/layers/base/com/mysql/main/
echo "<module xmlns=\"urn:jboss:module:1.5\" name=\"com.mysql\"> <resources> <resource-root path=\"mysql-connector-java-8.0.17.jar\"/> </resources> <dependencies> <module name=\"javax.api\"/> <module name=\"javax.transaction.api\"/> </dependencies></module>" | sudo tee -a /opt/wildfly/modules/system/layers/base/com/mysql/main/module.xml
sudo sed -i "/<drivers>/,/<\/drivers>/c\\\t\t\t\t<drivers>\n\t\t\t\t\t<driver name=\"h2\" module=\"com.h2database.h2\">\n\t\t\t\t\t\t<xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>\n\t\t\t\t\t</driver>\n\t\t\t\t\t<driver name=\"mysql\" module=\"com.mysql\">\n\t\t\t\t\t\t<driver-class>com.mysql.cj.jdbc.Driver</driver-class>\n\t\t\t\t\t\t<xa-datasource-class>com.mysql.cj.jdbc.MysqlXADataSource</xa-datasource-class>\n\t\t\t\t\t</driver>\n\t\t\t\t</drivers>" /opt/wildfly/standalone/configuration/standalone.xml
sudo sed -i "/<datasources>/a\\\t\t\t\t<datasource jta=\"true\" jndi-name=\"java:/informes_blackboard\" pool-name=\"informes_blackboard\" enabled=\"true\" use-ccm=\"true\"> \n\t\t\t\t\t<connection-url>jdbc:mysql://localhost:3306/informes_blackboard?serverTimezone=America/Tijuana</connection-url> \n\t\t\t\t\t<driver-class>com.mysql.cj.jdbc.Driver</driver-class> \n\t\t\t\t\t<driver>mysql</driver> \n\t\t\t\t\t<security> \n\t\t\t\t\t\t<user-name>root</user-name> \n\t\t\t\t\t\t<password>20\$_iguGb</password> \n\t\t\t\t\t</security> \n\t\t\t\t\t<validation> \n\t\t\t\t\t\t<valid-connection-checker class-name=\"org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker\"/> \n\t\t\t\t\t\t<validate-on-match>true</validate-on-match> \n\t\t\t\t\t\t<exception-sorter class-name=\"org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter\"/> \n\t\t\t\t\t</validation> \n\t\t\t\t</datasource>" /opt/wildfly/standalone/configuration/standalone.xml


# --- Deployment
sudo wget -P /opt/downloads/ https://github.com/Medina1402/wildfly10-centos9stream/archive/refs/heads/main.zip
sudo unzip /opt/downloads/main.zip -d /opt/downloads/
sudo cd /opt/downloads/wildfly10-centos9stream/
sudo mvn dependency:sources dependency:resolve -Dclassifier=javadoc


# --- PHPMyAdmin


# --- Ports
sudo ufw allow 9990 # CONSOLE
sudo ufw allow 8080 # APP HOST (redirect to port 80)
sudo ufw allow 80   #
#sudo ufw allow 3306 # MySQL (Ignore)
sudo ufw allow 8081 # PHPMyAdmin
sudo firewall-cmd --add-port=9990/tcp --permanent
sudo firewall-cmd --add-port=8080/tcp --permanent
sudo firewall-cmd --add-port=80/tcp --permanent
sudo firewall-cmd --add-port=8081/tcp --permanent
sudo firewall-cmd --add-port=3306/tcp --permanent
sudo firewall-cmd --reload