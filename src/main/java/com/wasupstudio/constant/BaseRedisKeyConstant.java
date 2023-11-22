package com.wasupstudio.constant;

/**
 * Redis Key
 */
public class BaseRedisKeyConstant {



    public static String LOGIN_CHECKED = "login_checked::LOGIN_CHECKED_%s";

    /*
    * 檔案下載緩存
    */
    public static String FILE_DOWNLOAD = "file_download::FILE_DOWNLOAD_%s_%s";

    public static String ORDER_LICENCE = "order_license::ORDER_LICENSE_%s";
    public static Integer FIVE_MIN = 5 * 60;
}
