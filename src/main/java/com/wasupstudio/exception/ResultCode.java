package com.wasupstudio.exception;

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
    UPLOAD_MAX_ERROR(10010, "文件圖片過大，請勿超過%s"),
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
    NO_FORCE_UPDATE_VERSION(10024, "沒找到需要強更的版本"),
    APP_VERSION_NO_SIGN_TYPE(10025, "請選擇簽名類型"),
    APP_LATEST_VERSION_NOT_FOUND(10026, "找不到最新版本"),
    UPLOAD_IMAGE_SIZE_ERROR(10027, "上傳圖片大小不符合規定"),
    CARD_NUMBER_OVER_SIZE(10028, "最多只能綁定5張銀行卡！"),
    NOT_CLOSED_CUSTOMER_INTERFACE(10029, "客服系統預設啓用，不能關閉"),

    NOT_AUTH_MODIFY_DEFAULT_FRONT_PAGE(10030, "該帳號無法使用此預設首頁,修改失敗"),

    //活動
    ACTIVETY_NOT_ONGOING_ROUND(11021, "對不起，此時非該活動申請時間。"),
    ACTIVETY_CLOSE(11022, "對不起，該活動已關閉。"),
    ACTIVETY_HAVE_BONUS(11023, "對不起，您已參與過該活動。"),
    ACTIVETY_HAVE_BETHISTORY(11024, "對不起，您不滿足活動條件。本活動僅限尚未進行投注的用戶才可以申請參加。"),
    ACTIVETY_RECHARGE_NOT_ENOUGH(11025, "對不起，您不滿足活動條件。您的最後一筆充值必須大於等於100元才可以申請參加活動。"),
    ACTIVETY_YUEBAO_CLOSED(11030, "餘額寶活動已關閉"),
    ACTIVETY_YUEBAO_BALANCE_LIMIT(11031, "用戶總餘額不足，無法開啓餘額寶"),
    ACTIVETY_YUEBAO_RATE_ERROR(11032, "選擇利率錯誤，無法開啓餘額寶"),
    ACTIVETY_YUEBAO_RATE_CHANGE_ERROR(11033, "變更利率後7天內無法再次變更利率"),
    ACTIVETY_YUEBAO_USER_UNOPEN_ERROR(11034, "用戶未開啓或已關閉餘額寶"),
    ACTIVETY_YUEBAO_USER_OPENED_ERROR(11035, "用戶已開啓餘額寶"),
    ACTIVETY_YUEBAO_REOPEN_ERROR(11036, "關閉餘額寶後1天內無法重新開啓餘額寶"),
    ACTIVETY_NOT_EXIST(11037, "活動不存在或已下架"),
    ACTIVETY_PHONE_NOT_EXIST(11039, "需綁定手機號和銀行卡才能申請參與哦！"),
    ACTIVETY_BANK_CARD_NOT_EXIST(11040, "需綁定銀行卡才能申請參與哦！"),
    ACTIVETY_HAVE_EXIST(11041, "您已參與該活動"),
    ACTIVETY_ADDTIMES_ERROR(11042, "領取抽獎次數失敗，請稍後重試"),
    ACTIVETY_GETREWARD_ERROR(11043, "領取獎品失敗，請稍後重試"),
    ACTIVETY_ADDREWARD_ERROR(11044, "請添加獎品！"),
    ACTIVETY_ADDREWARDCOUNT_ERROR(11045, "請添加日庫存數量！"),
    ACTIVETY_USERVIP_ERROR(11046, "沒有找到對應的VIP獎勵！"),
    ACTIVETY_ADDTIMES_FINISH_ERROR(11047, "任務已經完成，不可重復領取！"),

    ACTIVETY_NO_WHITELIST_IMPORT(11047, "白名單文件中無數據可導入"),
    ACTIVETY_WHITELIST_READ_ERROR(11048, "白名單文件讀取失敗"),
    ACTIVETY_WHITELIST_IMPORT_ERROR(11049, "白名單導入失敗"),
    ACTIVETY_WHITELIST_VIP_NOT_IN_EXIST(11050, "VIP等級不在預設範圍內"),
    ACTIVETY_TURNTABLE_BAGTYPE_EXIST(11051, "對不起！不能選擇組合類型為'全部獲得'的獎品組合！請重新選擇！"),
    ACTIVETY_ADDTIMES_OVER_ERROR(11053,"抽獎次數不可超過5次！"),
    ACTIVETY_SUBTIMES_OVER_ERROR(11054,"扣減失敗，次數已經用完！"),
    ACTIVETY_UPDATE_ERROR(11055,"抽獎失敗，請稍後重試！"),

    ACTIVETY_SIGN_CONFIG_NOT_EXIST(110056, "簽到配置信息不存在"),
    ACTIVETY_SIGN_DAY_EXIST(110057, "已經簽到過了"),
    ACTIVETY_SIGN_REPLENISH_ERROR(110058, "無需補簽"),
    ACTIVETY_SIGN_REPLENISH_NUM_ERROR(110059, "補簽次數不足，無法補簽"),
    ACTIVETY_SIGN_TOTAL_DRAW_ERROR(110060, "累計簽到獎品開箱配置異常"),
    ACTIVETY_SIGN_TOTAL_DRAW_DAYNUM_ERROR(110061, "累計簽到獎品開箱有誤"),
    ACTIVETY_SIGN_TOTAL_DAYNUM_LIMIT_ERROR(110062, "累計簽到天數未達到要求，不可領取"),
    ACTIVETY_SIGN_TOTAL_DRAW_EXIST(110063, "累計簽到獎品已完成開箱"),
    ACTIVETY_GETREWARD_EXPIRED_ERROR(11064, "獎品已過期，無法領取"),
    ACTIVETY_PARYINGCONFIG_EXPIRED_ERROR(11065, "祈福獎品配置錯誤！"),
    ACTIVETY_SIGN_CONDITION_NOT_MATCH(11066, "不符合簽到條件"),
    ACTIVITY_EXPIRED_OR_RECEIVED_OR_NO_REWARD(11067, "沒有領獎資格或已領獎或已過領獎期限"),
    ACTIVITY_BET_AMOUNT_NOT_ENOUGH(11077, "有效流水不足"),
    ACTIVITY_NO_SIGNED_UP_THIS_WEEK(11078, "本週尚未簽到"),
    ACTIVITY_INVALID_WEEK_DAYS(11079, "傳入的本週參數錯誤"),
    ACTIVITY_PAY_BONUS_FAILED(11080, "派獎失敗"),
    ACTIVITY_BONUS_SIGN_UP_FAILED(11081, "補簽失敗"),
    ACTIVITY_NO_REWARD_FOUND(11082, "沒有找到對應的獎勵！"),
    ACTIVITY_RECEIVE_AWARD_DATE_HAS_EXPIRED(11083, "領獎期限已過期！"),
    ACTIVITY_RECEIVE_AWARD_ERROR(11084, "領獎失敗，請聯繫客服處理！"),
    ACTIVITY_UNFINISHED_TASK_DO_NOT_RECEIVE_AWARD(11085, "尚未完成任務，無法領取獎勵！"),
    ACTIVITY_DRAW_CHANCES_NOT_ENOUGH(11091, "抽獎次數不足"),
    ACTIVITY_NOT_START_OR_ENDED(11092, "活動尚未開始或已結束"),
    ACTIVITY_CODE_ERROR(11093, "活動代號衝突，請修改後再提交"),
    ACTIVITY_ALREADY_TAKE_REWARD(11097, "您已領取過該獎勵"),
    ACTIVITY_REWARD_NOT_EXIST(11098, "您未達到獎勵門檻"),
    ACTIVITY_RED_PACKET_TOOK(11099, "本輪已領過紅包"),
    ACTIVITY_RED_PACKET_NOT_PERIOD(11100, "非紅包雨時段"),
    ACTIVITY_LIGHTUP_CHANCES_NOT_ENOUGH(11101, "點燈次數不足"),
    ACTIVITY_RED_PACKET_UNQUALIFIED(11102, "不符合參與條件"),
    ACTIVITY_NO_BONUS_TO_CLAIM(11103, "尚無彩金可領取"),
    ACTIVITY_FAIL_APPLY(11104, "不可參加,聯繫客服"),
    ACTIVITY_FAIL_RECEIVE_AWARD(11105, "您被限制參與優惠活動，請聯繫客服瞭解詳情"),
    ACTIVITY_NOT_START(11106, "活動尚未開始,敬請關注"),
    ACTIVITY_STARTED(11107, "活動進行中,不能新建或刪除獎勵,只能編輯名稱,數量,圖片"),
    ACTIVITY_REAL_PRIZE_CODE_EXISTS(11108, "該獎勵代號已存在"),
    ACTIVITY_REAL_PRIZE_CHANCE_NOT_100(11109, "實際獎勵預設機率總和錯誤，總和必需100%，請至活動獎勵管理確認配置是否正確"),
    ACTIVITY_NOT_IN_DISPLAY_TIME(11110, "活動不再露出區間內"),
    ACTIVITY_NOT_IN_DRAW_TIME(11111, "不再抽獎期間內"),
    ACTIVITY_BINGO_GRID_HAS_BEEN_MARKED(11112, "該賓果燈已被點亮"),
    ACTIVITY_SETTING_NULL(11113, "世界杯活動配置錯誤"),
    ACTIVITY_QUEST_TIME_ERROR(11114, "比賽時間已經開啓，無法進行預測"),
    ACTIVITY_QUEST_FORMATE(11115, "預測格式錯誤"),
    ACTIVITY_QUEST_NUM_NOT_ENOUGH(11116, "預測次數不足"),
    ACTIVITY_QUEST_ERROR(11117, "預測失敗"),
    ACTIVITY_TIME_END(11118, "活動已結束"),

    ACTIVITY_INVALID_STATUS(11098, "暫未滿足活動要求，感謝您的參與"),
    ACTIVITY_DRAW_CHANCES_NOT_ENOUGH_YEAR_DM(11119, "採摘機會不足"),
    ACTIVITY_DRAW_CHANCES_NOT_ENOUGHYEAR_VS(11120, "開運簽數量不足"),
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


    /* 人工綁卡 */
    BIND_CARD_HAVE_UNDONE_CARD(20134,"您有進行中的【其他銀行】（人工）綁定提款銀行卡申請，請耐心等待審核結果。如有疑問，請聯繫客服。"),
    BIND_CARD_MORE_THAN_THREE_TIMES_IN_DAY(20135, "24小時內已提交過3次人工綁定提款銀行卡"),
    BIND_CARD_EXIST_CARD_NUMBER_OR_TELEPHONE(20136, "您嘗試綁定的銀行卡無效或已被其他賬戶使用。請檢查後重新輸入。"),
    GRAPHICS_VERIFICATION_AUTH_INVALID_WITHOUT_TIME_LIMIT(20137, "很抱歉，您暫時無法繼續提交。如有疑問，請聯繫客服。"),
    RECHARGE_OF_REDEMPTION_TOO_MANY_TIMES(20137, "尊敬的客戶，您嘗試兌換的次數過多，請稍後再試(織夢商城)"),
    RECHARGE_OF_REDEMPTION_EXPIRED(20138, "兌換碼已過期"),
    RECHARGE_OF_REDEMPTION_IS_USED(20139, "兌換碼已被使用"),
    RECHARGE_OF_REDEMPTION_HAPPEN_ERROR(20140, "其他原因"),
    USER_NOT_ALLOW_RECHARGE(20141, "您的帳號不可充值"),
    VALIDATE_RECHARGE_MERCHANT_ERROR(20142, "商戶驗證異常"),
    RECHARGE_UPPER_LIMIT(20143, "今日充值已達上限"),
    EXCEED_RECHARGE_AMOUNT(20144, "超過充值限額"),
    BANK_SUPPORT_ERROR(20145, "該商戶不支援綁定其他銀行"),
    TEL_MAINTAIN_TEXT_GREATER_THAN_MAX(20146, "電維內文大於最大長度"),

    TEL_MAINTAIN_ERROR_OF_MERCHANT(20147, "接口錯誤"),
    TEL_MAINTAIN_PHONES_NOT_SETTING(20148, "手機號未配置正確"),
    TEL_MAINTAIN_OF_FILE_FAILED(20149,"選擇文件不合規格"),

    TEL_MAINTAIN_OF_CARRIER_FAILED(20150,"營運商配置錯誤"),
    SPONSOR_IMAG_TURN_MIN5_MAX20(20151,"贊助商輪撥圖最少5張,最多20張"),
    SPONSOR_STATUS_ILLEGAL(20152,"status:狀態異常,請輸入正常狀態"),
    SPONSOR_TEAM_COLOR_ILLEGAL(20153,"teamColor:無此贊助商色系,請輸入正常色系"),

    SPONSOR_HIGHLIGHTS_VIDEO_SOURCE_ILLEGAL(20154,"videoSource:無此視頻選擇,請輸入正確視頻種類"),

    SPONSOR_HIGHLIGHTS_SEASON_ILLEGAL(20155,"SEASON:無此賽季選擇,請輸入正確賽季"),

    SPONSOR_SPONSOR_PARTNER_ICONS_MAX20(20156,"子贊助商圖最多20張"),

    SPONSOR_HIGHLIGHTS_DETAIL_TYPE_ILLEGAL(20157,"Type:請填入正確Type"),

    EVERY_ONE_DISABLE(20151,"暫時無法加入【推薦計劃】"),
    EVERY_ONE_SYS_DISABLE(20152,"功能維護中"),
    EVERY_ONE_NO_USER(20153,"代理無法加入【推薦計劃】"),
    EVERY_ONE_AMOUNT(20154,"尊敬的玩家，很抱歉，您的待領取金額尚未達到最低領取金額"),
    PLATFORM_PROTOCOL_EXISTS(20155, "該交易所已綁定錢包地址"),
    PLATFORM_EXCHANGE_NOT_EXISTS(20156, "該交易所不存在"),
    INVALID_F_PAY_ADDRESS(20157, "FPAY 地址長度必須為17碼"),
    INVALID_F_PAY_PREFIX(20158, "首個字母為大寫F"),
    PLATFORM_ADDRESS_EXISTS(20159, "該錢包地址已被其他人使用"),
    BIND_CARD_ID_NUMBER_FORMAT_ERROR(20160,"您輸入的身份證號碼格式不正確"),
    INVALID_PLATFORM_ADDRESS(20161, "平台地址錯誤"),
    PLATFORM_EXCHANGE_CLOSED(20162, "該交易所已下架"),

    //遊戲
    CREATE_ACCOUNT_ERROR(40002, "創建用戶失敗"),
    GET_HISTORY_ERROR(40003, "獲取記錄失敗"),
    CHECK_BALANCE_ERROR(40004, "檢查餘額失敗"),
    LOGIN_ERROR(40005, "登陸遊戲失敗，請稍後嘗試"),
    GAME_GET_BET_ERROR(40000, "獲取投注記錄失敗"),
    GAME_LOGIN_ERROR(40001, "遊戲平入失敗，請稍後嘗試"),
    GAME_CREATE_ACCOUNT_ERROR(40006, "創建用戶失敗"),
    GAME_GET_HISTORY_ERROR(40007, "獲取記錄失敗"),
    GAME_CHECK_BALANCE_ERROR(40008, "無法檢查餘額，請聯繫客服處理"),
    GAME_WITHDRAW_ERROR(40009, "提款失敗，請稍後嘗試"),
    GAME_DEPOSIT_ERROR(40010, "存款失敗，請稍後嘗試"),
    GAME_URLCHECK_ERROR(40011, "獲取遊戲鏈接失敗"),
    GAME_HISTORY_ERROR(40012, "獲取投注記錄失敗"),
    GAME_TRANSFER_ERROR(40013, "轉賬失敗，請稍後嘗試"),
    GAME_MAINTAIN_ERROR(40014, "該遊戲正在維護，請稍後再試"),
    GAME_USER_NOT_EXIST(40015, "您的餘額為0，請充值後進行操作"),
    GAME_MERCHANT_NOT_EXIST(40016, "第三方商戶信息不存在"),
    GAME_NOT_EXIST(40017, "遊戲信息不存在"),
    GAME_TRANSFER_ORDER_NOT_EXIST(40018, "轉賬訂單不存在"),
    GAME_ERROR(40019, "遊戲已經關閉！"),
    GAME_DUPLICATE_HIT_ERROR(40020, "當前遊戲類型已有1個主打遊戲，如要開啓請先關閉其他"),
    GAME_MAINTAIN_TIME_ERROR(40021, "請先配置遊戲維護時間"),
    GAME_MAINTAIN_BUFFER_TIME_ERROR(40022, "遊戲維護緩衝時間配置錯誤"),
    GAME_MAINTAIN_MB_INTRO_IMAGES_FORMAT_ERROR(40023, "遊戲介紹圖配置錯誤"),

    //直播
    SOLAR_TECH_EPGS_AUTH_ERROR(41001, "賽事信息接口授權失敗"),
    SOLAR_TECH_EPGS_GET_ERROR(41002, "賽事信息接口獲取失敗"),

    //風控
    RISK_DIMENSION_NOT_EXIST(50000, "緯度不能為空"),
    RISK_TYPE_NOT_EXIST(50001, "類型不能為空"),
    RISK_BLACKLIST_MOBILE_ERROR(50002, "您的賬戶存在異常，請與客服聯繫"),
    RISK_BLACKLIST_USERNAME_ERROR(50003, "您的賬戶存在異常，請與客服聯繫"),
    RISK_SENSTIVE_KEYWORD_IS_EXIST(50004, "敏感詞已存在"),
    RISK_USER_BLACK_IS_EXIST(50005, "用戶黑名單記錄已經存在"),
    RISK_USER_GROUP_DATA_IS_EXIST(50006, "群組下還有用戶數據"),
    RISK_USER_LOGIN_ERROR(51000, "帳號存在風險，請輸入手機驗證碼確認操作"),

    RISK_USER_REGISTER_ERROR(52000, "註冊失敗，您的註冊行為已列入風控！"),
    RISK_BIND_CARD_ERROR(53000, "已超過當日可驗證次數，請聯繫客服"),

    RISK_SMS_LIMITS_ERROR(52001, "您已於24小時內超過發送過多短信，且沒有驗證成功，請聯繫客服！"),
    RISK_MOBILE_SMS_ERROR(52002, "驗證碼不正確"),


    //後台
    ADMIN_POST_NOT_EXIST(60001, "該崗位不存在"),
    COMMISSION_NOT_EXIST(60002, "傭金配置不存在"),
    COMMISSION_REPORT_NOT_EXIST_OR_EXAMINE(60003, "返傭記錄不存在或已審核"),
    COMMISSION_REPORT_NOT_EXIST(60004, "返傭記錄不存在"),
    OPERATION_RECORD_NOT_EXIST_OR_EXAMINE(60005, "操作記錄不存在或已審核"),
    RECHARGE_ORDER_NOT_EXIST(60006, "該充值訂到不存在，請與客服進行聯繫"),
    SUPPLEMENT_ORDER_NOT_EXIST_OR_APPROVED(60007, "補單申請不存在或已審核"),
    SUPPLEMENT_ORDER_APPROVED(60008, "補單申請已審核"),
    ADMIN_POST_EXIST(60009, "崗位已存在"),
    ADMIN_MENU_NOT_EXIST(60010, "菜單不存在"),
    ADMIN_COPY_POST_NOT_EXIST(60011, "被複製的崗位不存在"),
    NOTICE_NOT_EXIST_OR_DELET(60012, "公告不存在或已刪除"),
    POPUP_NOT_EXIST_OR_DELET(60013, "用戶彈窗不存在或已刪除"),
    USER_LEVEL_NOT_EXIST(60014, "用戶VIP層級不存在"),
    DEFAULT_LEVEL_DONT_DELET(60015, "無法刪除默認層級"),
    ADMIN_USER_NOT_APPROVED(60016, "該會員有未審核的操作，請先審核相關記錄"),
    USER_LEVEL_RECORD_NOT_EXIST(60017, "用戶層級審核記錄不存在"),
    ADMIN_RECORD_NOT_EXIST_OR_APPROVED(60018, "審核記錄不存在或已審核"),
    USER_BALANCE_RECORD_NOT_EXSIT(60024, "用戶餘額記錄不存在"),
    USER_BALANCE_ERROR(60025, "用戶餘額大於5元，不可進行流水清零操作"),
    ADMIN_PASSWOWRD_WRONG(60026, "提示: 您還有最後%d次(帳號將被凍結)，用戶名或密碼不正確"),
    ADMIN_LOCK_ERROR(60027, "提示: 您的帳號已經被凍結"),
    CALLBACK_RECEIVE_ERROR(60028, "電話回撥記錄不存在或已被領取"),
    CALLBACK_APPLY_ERROR(60029, "尊敬的會員您好！很抱歉，您申請3個等待的電話回撥，無法申請更多。"),
    TASK_NOT_MINE_ERROR(60030, "尚未領取該任務，操作失敗"),
    HUJIAOYI_CALL_ERROR(60031, "呼叫失敗，請稍後重試"),
    ADMIN_BIND_SIPNUM_ERROR(60032, "帳號未綁定分機號"),
    HUJIAOYI_HANDUP_ERROR(60033, "掛斷電話失敗，請稍後重試"),
    TELLSALE_DELETE_NOTSTART_ERROR(60034, "任務進行中或者已完成，無法刪除"),
    TELLSALE_EXT_ERROR(60035, "抽取歷史客戶池數據失敗"),
    TELLSALE_EDIT_NOTSTART_ERROR(60036, "任務進行中或者已完成，無法修改"),
    TELLSALE_RECEIVE_ERROR(60037, "超出最大領取數量限制，最大數量30"),
    TELLSALE_NOT_START_ERROR(60038, "當前沒有正在進行的任務池，領取失敗"),
    TELLSALE_RECOVERY_MUTI_ERROR(60039, "重復申請回收，申請失敗"),
    TELLSALE_RECEIVE_END_ERROR(60040, "任務已經被領取完，領取失敗"),
    TASK_NOT_HANDLER_ERROR(60041, "任務並未在處理中狀態，操作失敗"),
    TASK_IS_FINISH_ERROR(60042, "任務已經完成，操作失敗"),
    RECORD_NOT_MINE_ERROR(60043, "非自己的通話記錄，無法補充"),
    TELLSALE_REGISTER_EXT_ERROR(60044, "抽取指定用戶註冊客戶池數據失敗"),
    MESSAGE_REPLY_CONTENT_LENGTH_ERROR(60045, "回復內容大於300字"),
    THE_TASK_IS_IN_PROGRESS(60047, "任務進行中，無法在執行"),


    USERBACK_DATETIME_ERROR(60102, "活動開始時間必須小於結束時間"),
    USERBACK_EXSIT_NAME_ERROR(60103, "活動已存在，活動名稱重復"),
    USERBACK_NOT_EXSIT_ERROR(60104, "活動不存在"),
    USERBACK_CONFIG_ERROR(60105, "活動配置錯誤"),
    JOB_NICKNAME_EXSIT(60106, "暱稱已存在"),
    PERMISSION_APPLY_EXIST(60107, "已申請過該權限"),
    MATERIAL_INFO_NOT_EXIST(60108, "素材不存在"),
    ADVERT_INFO_NOT_EXIST(60109, "廣告不存在或已刪除"),
    DEPT_INFO_NOT_EXIST(60110, "部門不存在或已刪除"),
    MENU_INFO_NOT_EXIST(60111, "菜單不存在或已刪除"),
    PERSONNEL_INFO_NOT_EXIST(60112, "員工不存在或已刪除"),
    DEPT_PERSONNEL_RELATION_NOT_EXIST(60113, "員工未與部門關聯"),
    DEPT_SUPER_NOT_EDIT(60114, "默認超級角色不允許更新"),
    OLD_PWD_ERROR(60115, "原密碼不正確"),
    PERSONNEL_SUPER_NOT_EDIT(60116, "默認超級角色不允許更新"),
    DEPT_INFO_ERROR(60117, "無效部門信息"),
    ROLE_INFO_ERROR(60118, "無效系統角色"),
    DUTY_ROLE_INFO_ERROR(60118, "無效職能角色"),
    PERSONNEL_LOGIN_EXIST(60119, "已登入過的帳號不能刪除"),
    TESTFLIGHT_VERSION_EXIST(60122, "該版本已存在"),
    TESTFLIGHT_VERSION_ERROR(60123, "您輸入的版本號必須比當前最新的版本號【%s】更高。"),
    TESTFLIGHT_VERSION_FORMAT_ERROR(60124, "格式錯誤！版本號只能是一個整數或小數的數字，例如：1或者1.1。"),
    CONFIG_EXIST(60127, "設定已存在"),
    TASK_LIMIT_EXCEEDED(60128, "當前任務已有5個，如要開啓請先下架其他任務"),
    TASK_NOT_ALLOWED_DELETION(60129, "任務上架中，不可刪除"),

    //支付
    PAY_AMOUNT_ERROR(70000, "充值金額錯誤"),
    PAY_WITHDRAW_MAX_SUB_ERROR(70001, "拆單已經超過最大數限制"),
    ORDER_IS_DISPATH_ERROR(70002, "訂單已被分配"),
    PAY_AMOUNT_LIMIT_ERROR(70003, "充值金額有誤"),
    PAY_CHANNEL_NOT_EXIST(70004, "該充值渠道不存在，請嘗試其他渠道"),
    PAY_BANK_NOT_SUPPORT(70005, "收款銀行不支持"),
    PAY_BANKCARD_NOT_EXIST(70006, "收款銀行卡不存在"),
    PAY_BANKCARD_EXIST(70012, "收款銀行卡已存在"),
    PAY_THIRD_PARTY_NOT_EXIST(70007, "第三方應用不存在"),
    RECEIVABLES_NUMBER_NOT_EXIST(70008, "收款帳號不存在"),
    PAYMENT_NOT_EXIST(70009, "不支持以上支付方式"),
    BANKCARD_NOT_EXIST(700010, "不支持以上銀行卡"),
    SIGN_ERROR(700011, "簽名錯誤"),
    QUERY_MERCHANT_LEFTAMOUNT_ERROR(70016, "商戶餘額查詢失敗"),
    FUND_ROUTE_NOT_EXIST(70017, "未找到路由策略配置"),
    PAY_ADDRESS_NOT_EXIST(70018, "錢包地址不存在"),
    NEED_CURRENCY_TYPE(70019,"安全支付需要選擇幣種"),


    // 提現
    WITHDRAW_MERCHANT_NOT_EXIST(71000, "未配置對應出款渠道"),
    WITHDRAW_STATUS_ERROR(71003, "提現訂單已被操作[狀態異常]"),
    WITHDRAW_MERCHANT_TYPE_ERROR(71005, "出款渠道錯誤"),



    // 消息
    SMS_COUNT_ERROR(80000, "驗證碼發送過於頻繁，請稍後再試"),



    // Banner
    DIALOG_CONTENT_IS_EMPTY(140005, "內容不能為空"),
    DIALOG_INVALID_SUMMERY_PARAM(140006, "簡介為空 or 超出長度"),
    DIALOG_PC_AND_MB_IMAGE_IS_EMPTY(140007, "pc image and mb image 不能為空"),
    DIALOG_CLIENT_IS_EMPTY(140009, "顯示端不能為空"),

    //test
    TEST_EXCEPTION(666666, "測試"),

    /**
     * 2FA
     */
    SECRET_KEY_ERROR(180001, "密鑰不正確"),
    OTP_ERROR(180002, "認證碼不正確"),
    OTP_VERIFY_LIMITS_ERROR(180003, "認證碼嘗試太過頻繁"),
    AUDIT_2FA_ORDERID_NOT_EXIST(180004,"關閉雙重驗證申請單不存在"),
    AUDIT_2FA_APPROVING_OR_ALREADYCLOSE(180005,"雙重驗證已關閉或申請中"),
    AUDIT_2FA_ALREADYAUDIT(180006,"此申請單已審核")
    ;

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
}

