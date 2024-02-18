package com.wasupstudio.enums;

/**
 * 響應碼枚舉，參考HTTP狀態碼的語義
 *
 * <p>
 * 10000-19999 => 系統
 * 20000-29999 => 用戶
 * 30000-39999 => 社區
 * 40000-49999 => 直播
 * 50000-59999 => 風控
 * 60000-69999 => 後台
 * 70000-79999 => 交易、錢包
 * 80000-89999 => 消息
 * 90000-99999 => 代理
 */
public enum ResultCode {
    /**
     * 錯誤的格式
     */
    FAIL_FORMATTER(501, "{}"),
    SUCCESS(1, "登入成功"),
    FAIL(400, "無法進行訪問"),
    SYSTEM_ERROR(409, "系統異常"),
    UNAUTHORIZED(401, "簽名錯誤"),
    TOEKNUNVALIBLE(405, "TOKEN過期"),
    TOEKN_DEVICE(408, "該帳號於其他設備登入"),
    NOT_FOUND(404, "此位置不存在"),
    NOT_ACCESS(406, "無權限訪問"),
    NO_SENSE_ERROR(407, "圖片滑動驗證失敗"),
    INTERNAL_SERVER_ERROR(500, "系統繁忙,請稍後再試"),

    //操作提示
    ADD_SUCCESS(1001, "添加成功"),
    ADD_FAILD(1002, "添加失敗"),
    SAVE_SUCCESS(1003, "保存成功"),
    SAVE_FAILD(1004, "保存失敗"),
    DELETE_SUCCESS(1005, "刪除成功"),
    DELETE_FAILD(1006, "刪除失敗"),
    OPEN_SUCCESS(1007, "開啓成功"),
    OPEN_FAILD(1008, "開啓失敗"),
    CLOSE_SUCCESS(1009, "關閉成功"),
    CLOSE_FAILD(1010, "關閉失敗"),
    RECEIVE_SUCCESS(1011, "領取成功"),
    RECEIVE_FAILD(1012, "領取失敗"),
    APPLY_SUCCESS(1013, "申請成功"),
    APPLY_FAILD(1014, "申請失敗"),
    APPROVE_SUCCESS(1015, "審核成功"),
    APPROVE_FAILD(1016, "審核失敗"),
    SEND_SUCCESS(1017, "發送請求成功"),
    WITHDRAWAL_FAILD(1018, "提款撤銷失敗，請聯繫客服處理"),
    SAVE_REPEAT_FAILD(1019, "保存失敗，資料重復"),

    OPERATION_SUCCESS(2000, "操作成功"),
    OPERATION_FAILD(2001, "操作失敗"),
    UPDATE_SUCCESS(2002, "更新成功"),

    INVALID_PARAM(10000, "參數錯誤"),
    DATA_NOT_EXIST(10003, "該資料不存在"),
    UPLOAD_FORMAT_ERROR(10007, "文件格式有誤"),

    UPLOAD_MAX_ERROR(10010, "%s過大，請勿超過%s"),
    NOT_AUTH_ERROR(10011, "該帳號無權限訪問"),

    APP_LAST_VERSION_ALLREADY(10022, "當前已是最新版本"),
    UPLOAD_SUCCESS(10021, "文件上傳成功"),

    // 劇本
    SCRIPT_DATA_NOT_EXIST(10030, "該劇本資料不存在"),

    //用戶
    USER_LOGIN_SUCCESS(20000, "登入成功"),
    USER_LOGIN_FAILED(21000, "登入失敗"),
    USER_REGISTER_FAILED(22000, "註冊失敗"),
    USER_NAME_EXIST(20001, "該用戶名已存在"),
    VALIDATAE_CODE_ERROR(20002, "帳號驗證已經驗證過！"),
    VALIDATAE_CODE_SUCCESS(20003, "帳號驗證成功！您可以登錄您的帳號了。"),
    ACCOUNT_NOT_ENABLE(20004, "帳號尚未啟動！"),

    EMAIL_NOT_EXIST(20018, "該郵箱帳號有誤，請確認後再嘗試"),
    MAIL_MESSAGE_SEND_ERROR(20059, "郵件發送過於頻繁，請稍後再試"),
    PASSWORD_CHANGE_SUCCESS(20060, "密碼修改成功"),
    PASSWORD_CHANGE_FAILD(20061, "密碼修改失敗，請重新嘗試"),
    SEND_MAIL_SUCCESS(20082, "信件已發送, 如果您未收到信件, 請檢查您輸入的信箱是否正確"),
    SEND_MAIL_FAIL(20083, "信件發送失敗, 請重新嘗試"),

    /* 任務 */

    TASK_INVALID(20100, "任務時間已經結束 %s - %s"),

    /* 序號 */
    LICENSE_ACTIVATED_SUCCESS(20134, "啟動碼啟用成功"),
    LICENSE_ACTIVATED_FAIL(20134, "啟動碼啟用失敗"),
    LICENSE_NOT_INVALID(20135, "序號驗證失敗"),
    LICENSE_IS_USED(20136, "您嘗試啟動的序號無效或已被其他帳戶使用。請檢查後重新輸入。"),
    LICENSE_OF_REDEMPTION_TOO_MANY_TIMES(20137, "該帳戶已經啟用過啟動碼"),
    LICENSE_OF_REDEMPTION_EXPIRED(20138, "序號已過期"),
    LICENSE_OF_REDEMPTION_IS_USED(20139, "序號已被使用"),
    LICENSE_OF_REDEMPTION_HAPPEN_ERROR(20140, "其他原因"),
    LICENSE_OF_ACCOUNT_NOT_FOUND(20141,"該電子信箱尚未在本平台註冊"),

    //後台
    MATERIAL_INFO_NOT_EXIST(60108, "素材不存在"),


    // 消息
    SMS_COUNT_ERROR(80000, "驗證碼發送過於頻繁，請稍後再試");



    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(String text, String variable) {

        return String.format(message, text, variable);
    }
}

