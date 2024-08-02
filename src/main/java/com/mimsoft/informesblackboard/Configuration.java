package com.mimsoft.informesblackboard;

public class Configuration {
    public static final boolean SERVER = true;
    public static final String PATH_FILE = SERVER
            ?"/opt/wildfly/informes_blackboard/storage/"
            :"C:/Users/amcod/Desktop/mimsoft/SistemaInformesBlackboard/InformesBlackboard/development/storage/";
    public static final String PATH_FILE_UPLOADS = PATH_FILE + "upload/";
    public static final long MILLIS_EXPIRED_TIME_SESSION = 40 * 60000;
    public static final Integer TRANSACTION_TIME_SQL = 5000;
    public static String THEME_CSS = "wedgewood";
}
