package com.mimsoft.informesblackboard;

public class Configuration {
    public static final boolean SERVER = false;
    public static final String PATH_FILE = SERVER
            ?"/opt/wildfly/informes_blackboard/storage/"
            :"C:/Users/amcode1402/Desktop/mimsoft/Sistema de Informes Blackboard/InformesBlackboard/development/storage/";
    public static final String PATH_FILE_UPLOADS = PATH_FILE + "upload/";
    public static final long MILLIS_EXPIRED_TIME_SESSION = 40 * 60000;
    public static final long DURATION_MAX_UPDATE_SIMULATE_CACHE = 120 * 60000;
    public static final Integer TRANSACTION_TIME_SQL = 5000;
    public static final Integer MAX_BATCH_SIZE_MYSQL = 10000;
    public static String THEME_CSS = "sea-green";
}
