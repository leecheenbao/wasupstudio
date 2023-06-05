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
    DEPRECATED_ERROR(501, "接口廢棄"),
    BACKGROUND_SETTING_ERROR(998, "後台設定錯誤"),
    AUTO_LOGIN_SUCCESS(999, "自動登入成功"),

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
    INSERT_FAILD(10001, "新增添數據失敗，請稍後再試"),
    UPDATE_FAILD(10002, "更新數據失敗，請稍後再試"),
    DATA_NOT_EXIST(10003, "該資料不存在"),
    NOT_ACCESS_ERROR(10004, "請登入本人帳號進行操作"),
    UPLOAD_ERROR(10005, "上傳失敗，請稍後再試"),
    UPLOAD_DELETE_ERROR(10006, "文件刪除失敗，請稍後再試"),
    UPLOAD_FORMAT_ERROR(10007, "文件格式有誤"),
    TOKEN_FORMAT_ERROR(10008, "TOKEN格式錯誤"),
    INTENSITY_PASSWORD_ERROR(10009, "請設定至少8-16個字符、1個大寫字母、1個小寫字母和1個數字"),
    UPLOAD_MAX_ERROR(10010, "%s過大，請勿超過%s"),
    NOT_AUTH_ERROR(10011, "該帳號無權限訪問"),

    OPERATION_INVALID(10012, "此操作無效，請重新嘗試"),
    QUERY_FAILD(10013, "查詢內容失敗，請稍後再試"),
    TIME_INVALID_PARAM(10014, "日期參數有誤，請輸入正確日期"),
    ADMIN_NOT_EXIST(10015, "該帳號已失效，請重新登入"),
    DOWNLOAD_FAILD(10016, "下載失敗"),
    DOMAIN_FORMAT_ERROR(10017, "域名格式錯誤"),
    RATE_LIMIT_ERROR(10018, "請求太過頻繁，請稍後再試"),
    RESPONSE_JSON_ERROR(10019, "返回JSON數據異常"),
    APP_DATA_NOT_EXIST(10020, "無法獲取有效下載鏈接"),
    APP_LAST_VERSION_ALLREADY(10022, "當前已是最新版本"),
    UPLOAD_SUCCESS(10021, "文件上傳成功"),
    UNLOGIN_ERROR(10023,"請先登入您的帳號"),
    UPLOAD_IMAGE_SIZE_ERROR(10027, "上傳圖片大小不符合規定"),

    // 劇本
    SCRIPT_DATA_NOT_EXIST(10030, "該劇本資料不存在"),

    //用戶
    USER_LOGIN_SUCCESS(20000, "登入成功"),
    USER_LOGIN_FAILED(21000, "登入失敗"),
    USER_REGISTER_FAILED(22000, "註冊失敗"),
    USER_NAME_EXIST(20001, "該用戶名已存在"),
    VALIDATAE_CODE_ERROR(20002, "驗證碼錯誤"),
    USER_NAME_NOT_EXIST(20003, "該用戶名不存在"),
    PASSWOWRD_WRONG(20004, "用戶名或密碼不正確"),
    USER_NAME_ERROR(20005, "帳號必須為英文+數字且長度在6-10以內"),
    PASSWORD_ERROR(20077, "密碼不符合要求"),
    INVITE_CODE_NOT_EXIST(20006, "該邀請碼不存在，確認後請再嘗試"),
    MOBILE_EXIST(20007, "該手機號碼已存在"),
    EMAIL_EXIST(20008, "該郵箱已存在"),
    EMAIL_NOT_EXIST(20018, "該郵箱帳號有誤，請確認後再嘗試"),
    TOKEN_UNAVAILABLE(20009, "登入失效"),
    TRANSFER_UNAVAILABLE(20010, "我們檢測到您的帳號有異常行為，為您的財產安全考慮，此賬戶已經被自動鎖定，請聯繫客服解決"),
    TRANSFER_COUNT_MAX(20011, "今日無轉賬次數，請明日再嘗試。"),
    TRANSFER_PLATFORM_NOT_ENOUGH_LEFT(20012, "該賬戶平台餘額不足"),
    TRANSFER_GAME_NOT_ENOUGH_LEFT(20013, "該賬戶平台遊戲餘額不足"),
    TRANSFER_AMOUNT_LOCK(20014, "該賬戶資金已鎖定，請先解鎖資金"),
    TRANSFER_ERROR(20015, "轉賬失敗，請稍後嘗試"),
    NOT_ENGOUH_BALANCE(20016, "該賬戶餘額不足"),
    INVALID_USERNAME(20017, "以上用戶名無效"),
    CARD_VALIDATE_ERROR(20020, "銀行卡校驗錯誤，請重新校驗"),
    NAME_VALIDATE_ERROR(20028, "銀行卡姓名不匹配"),
    CARD_EXIST_ERROR(20029, "該銀行卡已存在"),
    CARD_SUPPORT_ERROR(20099, "銀行卡不支持"),
    CARD_NAME_IS_NOT_MATCH(20101,"銀行卡號與所選銀行不匹配"),
    MOBILE_NOT_EXIST(20021, "該手機號碼不存在"),
    INVALID_SMS_CODE(20022, "驗證碼輸入有誤"),
    VALID_SMS_CODE(20023, "驗證碼驗證成功"),
    RECHARGE_CHANNEL_CLOSED(20024, "充值渠道已關閉，請使用其它渠道"),
    IMG_CODE_ERROR(20025, "圖片驗證碼錯誤"),
    GAME_CODE_XJ_ERROR(20026, "請求超時"),
    GAME_CODE_FETCHDATA_ERROR(20027, "定時任務出錯"),
    PASSWOWRD_FORMAT_ERROR(20028, "密碼格式不正確"),
    TRANSFER_AMOUNT_ERROR(20029, "轉賬金額格式有誤"),
    TRANSFER_CLOSE_ERROR(20030, "該遊戲轉賬暫時關閉，請稍後再試"),
    TRANSFER_DAY_MAX_ERROR(20031, "今日轉入遊戲已達上限"),
    TRANSFER_CONFIG_ERROR(20032, "請檢查轉賬賬戶"),
    TOKEN_NOT_EXIST(20033, "TOKEN不存在"),
    PROXY_DOMAIN_ERROR(20034, "代理域名格式錯誤"),
    MOBILE_FORMAT_ERROR(20035, "手機號碼格式錯誤"),
    NEW_USER_ERROR(20036, "新用戶請修改登陸密碼"),
    SENSTIVE_KEYWORD_IS_EXIST(20037, "存在非法敏感詞"),
    NICK_NAME_ERROR(20075, "用戶名規定長度：1到25位字符"),
    PROXY_BING_ERROR(20038, "代理綁定失敗"),
    NICKNAME_NOT_EXIST(20039, "暱稱不能為空"),
    USER_SECURITY_INFO_NOT_EXIST(20040, "用戶保護信息不存在"),
    USER_NAME_PWD_EQUAL_ERROR(20041, "用戶名與密碼不能一致"),
    USER_OLD_NEW_PWD_EQUAL_ERROR(20042, "新舊密碼不能一致"),
    USER_LOCK_ERROR(20043, "系統檢測到您的帳號有異常行為，為了您的賬戶和資金安全，系統已暫時鎖定了您的帳號，請聯繫客服解鎖。"),
    USER_BIRFTHDAY_ERROR(20047, "生日格式錯誤，正確格式為：yyyy-MM-dd"),
    WITHDRAW_SUB_ORDER_NOT_EXIST(20048, "提現子訂單不存在或者已被審核"),
    VALIDATE_USERNAME_ERROR(20049, "驗證用戶名失敗"),
    ABNORMAL_WITHDRAWAL_RECORD_NOT_EXIST_OR_APPROVED(20050, "異常提現記錄不存在或已審核"),
    WITHDRAWAL_RECORD_NOT_EXIST(20051, "提現記錄不存在"),
    WITHDRAWAL_STATUS_NOT_APPENDING(200051, "提現記錄狀態錯誤"),
    ADDRESS_ERROR(20052, "最多只能添加5個收貨地址"),
    CITY_NOT_EXIST(20053, "該城市不存在"),
    ADDRESS_NOT_EXIST(20054, "該地址不存在"),
    PASSWORD_FIND_FAILD(20055, "密碼找回失敗"),
    USER_IPHONE_BING_ERROR(20056, "該用戶未綁定手機"),
    IPHONE_MESSAGE_SEND_ERROR(20057, "短信發送過於頻繁，請稍後再試"),
    USER_MAIL_BING_ERROR(20058, "該用戶未綁定郵箱"),
    MAIL_MESSAGE_SEND_ERROR(20059, "郵件發送過於頻繁，請稍後再試"),
    PASSWORD_FIND_MODE_ERROR(20060, "密碼找回方式有誤，請咨詢客服"),
    PASSWORD_CHANGE_FAILD(20061, "密碼修改失敗，請重新嘗試"),
    BANKCARD_BING_ERROR(20062, "已超出最高五張綁卡限制，請刪除多餘的銀行卡後再次進行綁卡"),
    CARD_NOT_EXSIT(20063, "該銀行卡不存在"),
    NICKNAME_EXSIT(20064, "暱稱已存在"),
    VALIDWATER_ERROR(20065, "流水不足，暫時無法提現"),
    WITHDRAWAL_ERROR(20066, "系統維護中，詳情請聯繫客服"),
    VALIDWATER_OR_BALANCE_ERROR(20067, "流水或餘額不足"),
    USER_WITHDRAWAL_ERROR(20068, "提現金額錯誤"),
    WITHDRAWAL_BANKCARD_ERROR(20069, "提款銀行卡信息錯誤"),
    BIRTHDAY_ERROR(20070, "生日格式錯誤，正確格式為：yyyy-MM-dd"),
    BIRTHDAY_CANNOT_CHANGE(20071, "生日填寫後不可修改，請再次確認"),
    CREDIT_AMOUNT_CHANGE(20072, "上分金額錯誤"),
    USER_INFO_NOT_EXIST(20073, "用戶信息不存在"),
    BANK_INFO_NOT_EXIST(20074, "商戶收款卡不存在"),
    USER_IS_UPDATE_NICK_NAME(20075, "暱稱只能修改一次"),
    EMAIL_FORMAT_ERROR(20076, "該郵箱格式錯誤"),
    USER_WITHDRAW_MAX_LIMIT_ERROR(20077, "提現金額超過最大限額"),
    USER_WITHDRAW_MIN_LIMIT_ERROR(20078, "提現金額未過最小限額"),
    USER_WITHDRAW_DAILY_LIMIT_ERROR(20079, "提現次數已達每日最大上限"),
    USER_GROUP_ERROR(20080, "用戶群組不存在"),
    TRANSFER_DAY_LIMIT_ERROR(20081, "遊戲金額已被限額"),
    SEND_CODE_SUCCESS(20082, "驗證碼已發送, 如果您未收到驗證碼, 請檢查您輸入的手機號是否正確"),
    WITHDRAW_LENGTH_ERROR(20083, "提現記錄無法操作"),
    WITHDRAW_NOT_EXIST(20084, "提現記錄不存在"),
    NICKNAME_LENGTH_ERROR(20085, "暱稱長度在1-25字以內"),
    WITHDRAW_TIP_IS_APPROVERING(20086, "這筆提現已經成功出款，無法取消了。"),
    WITHDRAW_TIP_APPROVERED(20087, "這筆提現正在出款，無法取消了。"),
    BIRTHDAY_UPDATE_SUCCESS(20088, "生日更新成功"),
    SEX_UPDATE_SUCCESS(20089, "性別更新成功"),
    AVATAR_UPDATE_SUCCESS(20090, "頭像更新成功"),
    NICKNAME_UPDATE_SUCCESS(20091, "暱稱更新成功"),
    NOT_USERBACKLIST(20092, "非指定老用戶暫時無法註冊"),
    VALIDATAE_CODE_RETRY_ERROR(20093, "驗證碼驗證失敗，請重發後再試"),
    TRANSFER_TIME_LIMIT_ERROR(20094, "遊戲轉賬5秒限制，請稍後再試"),
    WITHDRAWAL_MAX_LIMIT(20095, "提現金額已超過每日最高限額"),
    USDT_RECHARGE_CHECK(20133, "在本次USDT提現前，請先進行USDT充值，謝謝！"),
    PLATFORM_RECHARGE_CHECK(20134, "在本次提現前，請先進行平台幣充值，謝謝！"),
    DEVICE_BIND_ERROR(20096, "登入失敗，賬戶已綁定設備號登入"),
    NAME_IS_EMPTY_ERROR(20097, "請確定姓名已設置"),
    BIND_CARD_IS_EXIST(20098, "你已經綁定過銀行卡, 不能在設置姓名"),
    REALLY_NAME_LENGTH_ERROR(20099, "真實長度在20字以內"),
    REALLY_NAME_UPDATE_SUCCESS(20100, "姓名更新成功"),
    EXIST_TRANS_SN(20101, "代充交易編號重復"),
    REPLACE_PAY_FAILED(20102, "代充失敗"),
    IMG_CODE_EMPTY(20103, "圖片驗證碼為空值"),
    IMG_CODE_USED(20104, "圖片驗證碼已失效"),
    WITHDRAW_SUCCESS(20105, "提現成功"),
    WITHDRAW_ADDRESS_ERROR(20106, "提現地址不存在"),
    WITHDRAW_PLATFORM_WALLET_ERROR(20107, "平台幣提現地址不存在"),
    WITHDRAW_ADDRESS_IS_EXIST(20107, "提示該地址已被綁定"),
    ESTIMATION_FEE_AMBIGUOUS(10000, "只能選一種提款方式"),
    CARD_BIND_ERROR(20108, "銀行卡綁定校驗錯誤"),
    DIFFERENT_BANK_AND_BANK_NO(20103, "所選銀行與卡號不同"),
    CARD_NOT_SELF(20104, "此卡名稱非本人"),
    CARD_NOT_OTHER(20105, "此卡名稱非他人"),
    NEED_BIND_SELF_CARD(20106, "請先綁本人卡"),
    KEEP_AT_LEAST_ONE_BANK_CARD_OF_YOUR_OWN(20107, "請至少保留一張自己的銀行卡"),
    WITHDRAW_NOT_MATCH_RECHARGE_ERROR(20108, "未滿足提現[充值]條件，暫時無法提現"),
    WITHDRAW_NOT_MATCH_VIP_ERROR(20109, "未滿足提現[VIP]條件，暫時無法提現"),
    OLD_MOBILE_EMPTY(20108, "原手機號碼不得為空"),
    NEW_MOBILE_EMPTY(20109, "新手機號碼不得為空"),
    NEW_OLD_MOBILE_SAME(20110, "新舊號碼不可一樣"),
    SMS_CODE_EMPTY(20111, "驗證碼不得為空"),
    VALIDATAE_NM_CODE_RETRY_ERROR(20112, "驗證碼驗證失敗，請重發後再試"),
    INVALID_NM_SMS_CODE(20113, "驗證碼輸入有誤"),
    USER_ID_NOT_EXISTS(20114, "使用者id不存在"),
    USER_NAME_PASSWORD_NOT_MATCH(20115, "您輸入的用戶名與密碼不匹配，請檢查後重試，或點擊下方聯繫在線客服"),
    ENTER_CODE_ERROR(20116, "您輸入的驗證碼有誤，請檢查後重試"),
    VALIDATAE_MOBILE_IP_LOGIN_TIMES(20117, "同ip 1小時內最多發送5次手機短信驗證碼"),
    VALIDATAE_MOBILE_VOICE_LOGIN_TIMES(20118, ""),
    SEND_CODE_TO_PHONE(20119, "驗證碼已發送到手機號 %s。如果您長時間未收到驗證碼，請檢查輸入的手機號是否正確，或改用其他輔助驗證方式"),
    NOT_ALLOW_TO_SEND_VOICE_CODE(20120, "國內(+86號碼段)每天22:00至次日08:00禁止發送"),
    OPEN_GRAPHICS_VERIFICATION(20121, "開啓圖型化驗證"),
    GRAPHICS_VERIFICATION_AUTH_VALID(20122, "圖型化驗證機制已啟用"),
    GRAPHICS_VERIFICATION_AUTH_INVALID(20123, "很抱歉，您暫時無法繼續提交。請等待%d分鐘後重試。如有疑問，請聯繫客服。"),
    DUPLICATE_TRANSACTION(20124, "重復的交易"),
    CAPTCHA_LOCK_OF_PARAMS(20125, "%s 缺失參數，驗證不通過。"),
    NOT_AVAILABLE_CAPTCHA_VALIDATION(20126, "無可用驗證機制。"),
    CAPTCHA_REGISTRT_FAILED(20127,"驗證系統繁忙中，請稍候再試"),
    CAPTCHA_NOT_FOUND_SETTING(20128,"該驗證未設定SETTING"),
    CAPTCHA_VERIFICATION_FAILED(20129,"CAPTCHA 驗證失敗"),
    LOGIN_TOO_MANY_TIMES(20130, "登入過於頻繁, 請稍後再試"),
    LOGIN_FAILED_RETRY_LOGIN(20131, "為了您帳號安全，本次登入已失效，請重新登入"),
    WITHDRAW_ADDRESS_NOT_MATCH_RULE(20132, "提幣地址非協議地址格式，請核對後重新輸入"),
    LIMITWATER_ERROR(20133, "平台限制流水不足，暫時無法提現"),
    UNIDENTIFIED(20134, "尚未實名認證"),
    NO_SUCCESSFUL_DIGITAL_RECHARGE(20135, "無數字貨幣充值成功"),
    NO_SUCCESSFUL_FIAT_WITHDRAW(20136, "無傳統提款成功"),
    NO_BOUND_ADDRESSES(20137, "無綁定的地址"),


    /* 序號 */
    LICENSC_IS_USED(20136, "您嘗試啟動的序號無效或已被其他帳戶使用。請檢查後重新輸入。"),
    GRAPHICS_VERIFICATION_AUTH_INVALID_WITHOUT_TIME_LIMIT(20137, "很抱歉，您暫時無法繼續提交。如有疑問，請聯繫客服。"),
    LICENSE_OF_REDEMPTION_TOO_MANY_TIMES(20137, "該帳戶尚有未經啟動的序號，請先將其他未經啟用的序號註銷"),
    LICENSE_OF_REDEMPTION_EXPIRED(20138, "序號已過期"),
    LICENSE_OF_REDEMPTION_IS_USED(20139, "序號已被使用"),
    LICENSE_OF_REDEMPTION_HAPPEN_ERROR(20140, "其他原因"),
    LICENSE_OF_ACCOUNT_NOT_FOUND(20141,"該電子信箱尚未在本平台註冊"),

    //後台
    USER_BALANCE_ERROR(60025, "用戶餘額大於5元，不可進行流水清零操作"),
    ADMIN_PASSWOWRD_WRONG(60026, "提示: 您還有最後%d次(帳號將被凍結)，用戶名或密碼不正確"),
    ADMIN_LOCK_ERROR(60027, "提示: 您的帳號已經被凍結"),
    CALLBACK_RECEIVE_ERROR(60028, "電話回撥記錄不存在或已被領取"),
    CALLBACK_APPLY_ERROR(60029, "尊敬的會員您好！很抱歉，您申請3個等待的電話回撥，無法申請更多。"),
    TASK_NOT_MINE_ERROR(60030, "尚未領取該任務，操作失敗"),
    HUJIAOYI_CALL_ERROR(60031, "呼叫失敗，請稍後重試"),
    ADMIN_BIND_SIPNUM_ERROR(60032, "帳號未綁定分機號"),
    HUJIAOYI_HANDUP_ERROR(60033, "掛斷電話失敗，請稍後重試"),
    TELLSALE_RECEIVE_END_ERROR(60040, "任務已經被領取完，領取失敗"),
    TASK_NOT_HANDLER_ERROR(60041, "任務並未在處理中狀態，操作失敗"),
    TASK_IS_FINISH_ERROR(60042, "任務已經完成，操作失敗"),
    RECORD_NOT_MINE_ERROR(60043, "非自己的通話記錄，無法補充"),
    TELLSALE_REGISTER_EXT_ERROR(60044, "抽取指定用戶註冊客戶池數據失敗"),
    MESSAGE_REPLY_CONTENT_LENGTH_ERROR(60045, "回復內容大於300字"),
    THE_TASK_IS_IN_PROGRESS(60047, "任務進行中，無法在執行"),
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

