package com.wasupstudio.enums;

public final class ProjectConstant {

    public final class APIStatus {
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
        public final static int FAIL = 1;
        public final static int CANCEL = 2;
    }
}
