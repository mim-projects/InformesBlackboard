package com.mimsoft.boilerplate;

public class Configuration {
    public static final boolean SERVER = false;
    public static final String PATH_FILE = SERVER
            ?"/opt/wildfly/xxxx-xxxx-xxxx/storage/"
            :"yyyy-yyyy-yyyy/";
    public static final String PATH_FILE_UPLOADS = PATH_FILE + "upload/";
    public static long MillisExpiredTimeSession = 40 * 60000;
    public static String ThemeCSS = "apple";
}
