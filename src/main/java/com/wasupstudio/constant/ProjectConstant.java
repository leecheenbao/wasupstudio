package com.wasupstudio.constant;

/**
 * ProjectConstant
 *
 * @author Paul
 **/
public final class ProjectConstant {

    public final class SystemAdminStatus {

        public final static int NOT_ENABLED = 0;// 未啟用
        public final static int NORMAL = 1;// 正常
        public final static int DISABLE = 2;// 停用
        public final static int DELETED = 3;// 删除
    }

    public final class GoogleOAuthPath {
        public final static String REDRIEC = "https://wasupstudionobullying.com";
        public final static String SIGNUP = "/signup2callback";
        public final static String LOGIN = "/login2callback";
    }

    public static final class FilePath {
        //        public final static String MAINPATH = "https://storage.cloud.google.com/";
        public final static String MAINPATH = "https://storage.googleapis.com/";

        public final static String BUCKET_NAME = "wasupstudio-bucket";
        public final static String IMAGE = "/image/";

        public final static String VIDEO = "/video/";

        public final static String PDF = "/pdf/";
    }


    public static final class FileType {
        public final static String DOCS = "application/docs";
        public final static String PDF = "application/pdf";
        public final static String IMAGE = "application/image";
        public final static String VIDEO = "application/video";
        public final static String UNKNOWN = "Unknown file type";

    }

    public static final class APIStatus {
        public final static String API_RESPONSE_IS_SUCCESS = "SUCCESS";
        public final static String API_RESPONSE_IS_FAIL = "FAIL";
    }

    public final class LiceenseStatus {
        public final static int NOT_SETTLE = 0;//未開始結算
        public final static int UNDONE = 1;//未完成
        public final static int DONE = 2;//已完成
    }

    public final class OrderStatus {
        public final static int SUCCESS = 0;
        public final static int UNDONE = 1;
        public final static int FAIL = 2;
    }

    public final class MailType {
        public final static String FORGET = "forget";
        public final static String SIGNUP = "signup";
        public final static String START_KEY = "start_key";
        public final static String LICENSING = "licensing";
    }

    public final class LoginType {
        public final static int NORMAL = 0;
        public final static int GOOGLE_AUTH = 1;
    }
}
