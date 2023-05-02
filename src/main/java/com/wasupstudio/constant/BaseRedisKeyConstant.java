package com.wasupstudio.constant;

/**
 * Redis Key
 */
public class BaseRedisKeyConstant {

    /**
     * 用户绑定银行卡
     */
    public static String USER_BANCK_CARD = "USER_BANCK_CARD_%d";
    /**
     * 用户绑定银行卡次數
     */
    public static String USER_BANK_CARD_TIMES = "USER_BANK_CARD_TIMES_%s";
    /**
     * 用户后台操作ID
     */
    public static final String USER_MANAGE_ID = "USER_MANAGE_ID";
    /**
     * 用户前台操作ID
     */
    public static final String USER_OPERATION_ID = "USER_OPERATION_ID";
    /**
     * 用户登录操作ID
     */
    public static final String USER_LOGIN_ID = "USER_LOGIN_ID";
    /**
     * 系统通知上次获取时间
     */
    public static String USER_NOTICE_LAST_TIME = "user_notice::USER_NOTICE_LAST_TIME_%d";
    /**
     * 注册用锁
     */
    public static String USER_REGISTER_LOCK = "USER_REGISTER_LOCK_%s";

    public static String USER_REGISTER_IP_TIMES = "register_times::REGISTER_TIMES_IP_%s";

    public static String USER_REGISTER_DEVICE_TIMES = "register_times::REGISTER_TIMES_DEVICE_%s";

    public static String USER_REGISTER_VALIDATE_MOBILE_SMS = "register_validate_mobile_sms::VALIDATE_MOBILE_SMS_%s_%s_%s";
    /**
     * 用户登录失败次数
     */
    public static String LOGIN_FAIL_TIME = "login_fail::LOGIN_FAIL_TIME_%d";

    public static String LOGIN_TIMES = "login_times::LOGIN_TIMES_%s";

    public static String REG_FAIL_TIME = "reg_fail::REG_FAIL_TIME_%s";

    public static String IMAGE_CODE_CACHE = "image_code::IMAGE_CODE_%s";

    public static String CANCEL_RECHARGE_TIME = "cancel_recharge::CANCEL_RECHARGE_TIME_%d";

    public static String VALIDATE_MOBILE_FAIL_TIME = "validate_mobile_fail::VALIDATE_MOBILE_FAIL_TIME%s_%s";

    public static String VALIDATE_EMAIL_FAIL_TIME = "validate_email_fail::VALIDATE_EMAIL_FAIL_TIME%s_%s";

    public static String USED_IMG_TOKEN = "login_check:USED_IMG_TOKEN_%s";

    public static String LOGIN_CHECKED = "login_checked::LOGIN_CHECKED_%s";

    /**
     * 邮箱验证码发送时间
     */
    public static String EMAIL_MSG_SEND_TIME = "email_msg::EMAIL_MSG_SEND_TIME_%s_%s";
    /**
     * 邮箱验证码
     */
    public static String EMAIL_MSG_SEND = "email_msg::EMAIL_MSG_SEND_%s_%s";
    /**
     * 短信验证码发送时间
     */
    public static String MOBILE_MSG_SEND_TIME = "mobile_msg::MOBILE_MSG_SEND_TIME_%s_%s";
    /**
     * 短信验证码
     */
    public static String MOBILE_MSG_SEND = "keys %s_%s_%s";
    /**
     * 用户层级列表缓存
     */
    public static String USER_LEVEL_CACHE = "USER_LEVEL_CACHE";

    /**
     * VIP层级列表缓存
     */
    public static String VIP_LEVEL_CACHE = "VIP_LEVEL_CACHE";

    /**
     * 用户层级（群组）支付方式缓存
     */
    public static String USER_LEVEL_PAYMENT_CACHE = "USER_LEVEL_PAYMENT_CACHE_%d";
    /**
     * 用户层级支付方式缓存
     */
    public static String USER_LEVEL_FAST_AMOUNT_CACHE = "USER_LEVEL_FAST_AMOUNT_CACHE_%d";

    /**
     * 应用级别前缀
     */
    public static String REQ_TOKEN = "token::TOKEN_%s";
    /**
     * 商城使用(供三方)
     */
    public static String MALL_REQ_TOKEN = "token::mall::TOKEN_%s";
    /**
     * 商城使用，第一次導頁使用(供三方)
     */
    public static String MALL_EXCHANGE_REQ_TOKEN = "token::mall::EXCHANGE_TOKEN_%s";
    /**
     * 遊戲使用(供三方)
     */
    public static String GAME_REQ_TOKEN = "token::game::TOKEN_%s";
    /**
     * 其它设备
     */
    public static String REQ_TOKEN_DEVICE = "token::TOKEN_DEVICE_%s";
    /**
     * 登录用户ID前缀
     */
    public static String REQ_LOGIN = "login_uid::LOGIN_%s";
    /**
     * 登录用户ID前缀，商城使用(供三方)
     */
    public static String MALL_REQ_LOGIN = "login_uid::mall::LOGIN_%s";
    /**
     * 登录用户ID前缀，商城使用，第一次導頁使用(供三方)
     */
    public static String MALL_EXCHANGE_REQ_LOGIN = "login_uid::mall::EXCHANGE_LOGIN_%s";

    /**
     * 前端心跳紀錄
     */
    public static String USER_HEARTBEAT = "HEARTBEAT_MALL_%S";

    /**
     * 转账消费锁
     */
    public static String GAME_TRANSFER_LOCK_KRY = "GAME_TRANSFER_LOCK_KRY_%d_%d";
    /**
     * 余额消费锁
     */
    public static String GAME_USER_UPDATE_BALANCE_LOCK_KRY = "GAME_TRANSFER_LOCK_KRY_%d_%d";

    /**
     * 代理域名点击数量
     */
    public static String PROXY_DOMAIN_COUNT_ = "PROXY_DOMAIN_COUNT_";

    /**
     * 转账锁
     */
    public static String TRANSFER_LOCK_KEY = "TRANSFER_SUBMIT";

    /**
     * 提现锁
     */
    public static String WITHDRAW_LOCK_KEY = "WITHDRAW_SUBMIT";

    /**
     * 提现出款审核通过锁
     */
    public static String WITHDRAW_APPROVE_KEY = "WITHDRAW_APPROVE_KEY";

    /**
     * 提现地址锁
     */
    public static String WITHDRAW_ADDRESS_LOCK_KEY = "WITHDRAW_ADDRESS_SUBMIT_%s";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_INFO = "ACTIVITY_INFO_%d";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_LOCK_KEY = "ACTIVITY_SUBMIT";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_JOIN_LOCK_KEY = "ACTIVITY_JOIN_";

    /**
     * 活動結算
     */
    public static String ACTIVITY_SETTLEMENT_LOCK_KEY = "ACTIVITY_SETTLEMENT_%s";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_DRAW_LOCK_KEY = "ACTIVITY_DRAW_SUBMIT";
    /**
     * 活动用锁
     */
    public static String ACTIVITY_ADDTIMES_LOCK_KEY = "ACTIVITY_ADDTIMES_LOCK";
    /**
     * 活动用锁
     */
    public static String ACTIVITY_GETREWARD_LOCK_KEY = "ACTIVITY_GETREWARD_LOCK";
    /**
     * 活动用锁
     */
    public static String ACTIVITY_USER_GET_REWARD_LOCK_KEY = "ACTIVITY_GET_REWARD_LOCK_%s";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_USER_BONUS_SIGN_UP_KEY = "ACTIVITY_BONUS_SIGN_UP_%s";

    /**
     * 活动领奖锁
     */
    public static String ACTIVITY_USER_RECEIVE_AWARD_LOCK_KEY = "ACTIVITY_USER_RECEIVE_AWARD_LOCK_";

    /**
     * 活动用锁
     */
    public static String ACTIVITY_SIGN_LOCK_KEY = "ACTIVITY_SIGN_LOCK";

    public static String ACTIVITY_CONFIG = "ACTIVITY_%s_CONFIG";
    /**
     * 人人代理提取佣金锁
     */
    public static String EVERYONE_TRANSFER_LOCK_KEY = "EVERYONE_TRANSFER_LOCK_KEY_";

    /**
     * 佣金报表生成用锁
     */
    public static String COMMISSION_SETTLE_LOCK_KEY = "COMMISSION_SETTLE_LOCK_KEY";

    /**
     * 佣金发放
     */
    public static String COMMISSION_LOCK_KEY = "COMMISSION_LOCK_KEY";

    /**
     * 用户申请加锁解锁用锁
     */
    public static String USER_LOCK_STATUS_KEY = "USER_LOCK_STATUS_KEY";

    /**
     * 用户加锁用锁
     */
    public static String USER_LOCK_STATUS_LOCK_KEY = "USER_LOCK_STATUS_LOCK_KEY";

    /**
     * 用户解锁用锁
     */
    public static String USER_LOCK_STATUS_UNLOCK_KEY = "USER_LOCK_STATUS_UNLOCK_KEY";

    /**
     * 上次转账成功时间
     */
    public static String TRANSFER_SUCCESS_TIME = "user_transfer::TRANSFER_SUCCESS_TIME_%d_%d";

    /**
     * SolarTechEpgs赛事数据TOKEN
     */
    public static String SOLAR_TECH_EPGS_TOKEN = "SOLAR_TECH_EPGS_TOKEN";

    /**
     * SolarTechEpgs赛事数据KEY
     */
    public static String SOLAR_TECH_EPGS_KEY = "SOLAR_TECH_EPGS_KEY";

    /**
     * SolarTechEpgs赛事单场数据KEY
     */
    public static String SOLAR_TECH_EPGS_MATCH_KEY = "SOLAR_TECH_EPGS_MATCH_KEY_%s";

    /**
     * SolarTechEpgs赛事简化数据KEY
     */
    public static String SOLAR_TECH_EPGS_KEY_NEW = "SOLAR_TECH_EPGS_KEY_NEW";

    /**
     * SolarTechEpgs实时赛事单场数据KEY
     */
    public static String SOLAR_TECH_EPGS_LIVE_MATCH_KEY = "SOLAR_TECH_EPGS_LIVE_MATCH_KEY_%s";

    /**
     * SolarTechEpgs实时赛事简化数据KEY
     */
    public static String SOLAR_TECH_EPGS_LIVE_KEY = "SOLAR_TECH_EPGS_LIVE_KEY";

    /**
     * IBO赛事数据KEY
     */
    public static String IBO_GAME_LIST_TODAY_KEY = "IBO_GAME_LIST_TODAY_KEY";
    public static String IBO_LEAGUES_COUNT_TODAY_KEY = "IBO_LEAGUES_COUNT_TODAY_KEY";
    /**
     * 金流层级
     * %d = 金流层级id
     * %d = 客户端编号
     */
    public static String USER_LEVEL_ROUTE = "USER_LEVEL_ROUTE_%d_%d_%d";
    /**
     * 充值用户锁
     */
    public static String RECHARGE_USER_LOCK = "RECHARGE_USER";

    /**
     * JDB游戏列表缓存
     */
    public static String JDB_GAME_LIST_KEY = "JDB_GAME_LIST_KEY_%d";

    /**
     * JDB游戏列表缓存
     */
    public static String RG_GAME_LIST_KEY = "RG_GAME_LIST_KEY_%d";

    /**
     * 电销抽样历史客户池批次数据
     */
    public static String TELL_SALE_EXT_KEY = "TELL_SALE_EXT_KEY_%d";

    /**
     * 电销撤销历史客户池批次数据
     */
    public static String TELL_SALE_RECOVERY_KEY = "TELL_SALE_RECOVERY_KEY_%d";

    /**
     * 呼叫易通话状态数据
     */
    public static String HUJIAOYI_STAT_INFO = "HUJIAOYI_STAT_INFO_%d_%d";

    /**
     * HUJIAOYI Session Value
     */
    public static String HUJIAOYI_SESSION = "HUJIAOYI_SESSION_%s";

    /**
     * portsip access_token
     */
    public static String PORTSIP_ACCESS_TOKEN = "PORTSIP_ACCESS_TOKEN_%s";

    /**
     * portsip session
     */
    public static String PORTSIP_SESSION = "PORTSIP_SESSION_%s";

    /**
     * 报表文件生成用锁
     */
    public static String REPORT_FILE_LOCK_KEY = "REPORT_FILE_SUBMIT_%d";

    /**
     * 用户标签关联脚本跑批时间缓存
     */
    public static String USER_TAG_RELATION_JOB_TIME = "user_tag::USER_TAG_RELATION_JOB_TIME";

    /**
     * 用户标签关联脚本跑批次数
     */
    public static String USER_TAG_RELATION_JOB_NUM = "user_tag::USER_TAG_RELATION_JOB_NUM_%s";

    /**
     * 用户标签关联数据
     */
    public static String USER_TAG_RELATION = "user_tag::USER_TAG_RELATION_%d";

    /**
     * 用户标签字典
     */
    public static String USER_TAG_LIST = "user_tag::USER_TAG_LIST";

    /**
     * 短信通道轮替
     */
    public static String SMS_CHANNEL = "message::SMS_CHANNEL_%s_%s";

    /**
     * 邮件通道轮替
     */
    public static String EMAIL_CHANNEL = "message::EMAIL_CHANNEL_%s";

    /**
     * 活动白名单启用状态
     */
    public static String ACTIVITY_WHITE_ENABLE_SWITCH = "ACTIVITY_WHITE_ENABLE_SWITCH";

    /**
     * 轮盘礼品
     */
    public static String ACTIVITY_TURNTABLE_PT = "ACTIVITY_TURNTABLE_PT";
    public static String ACTIVITY_TURNTABLE_VIP = "ACTIVITY_TURNTABLE_VIP";

    public static String HOME_REPORT_GAME_BET_SUPPLEMENT = "GAME_BET_SUPPLEMENT";
    public static String HOME_USER_NEXT_SYNC_DATE = "HOME_USER_NEXT_SYNC_DATE";
    public static String HOME_REPORT_ALL_REALTIME_REPORT = "HOME_REPORT::ALL_REALTIME_REPORT";
    public static String HOME_REPORT_TOP_RECHARGE_LIST = "HOME_REPORT::TOP_RECHARGE_LIST";
    public static String HOME_REPORT_TOP_WITHDRAW_LIST = "HOME_REPORT::TOP_WITHDRAW_LIST";
    public static String HOME_REPORT_TOP_WIN_LIST = "HOME_REPORT::TOP_WIN_LIST";
    public static String HOME_REPORT_TOP_LOSS_LIST = "HOME_REPORT::TOP_LOSS_LIST";
    public static String HOME_REPORT_MONTHLY_TOTAL = "HOME_REPORT::MONTHLY_TOTAL";
    public static String HOME_REPORT_GAME_BET = "HOME_REPORT::GAME_BET";
    public static String HOME_REPORT_GAME_WIN = "HOME_REPORT::GAME_WIN";
    public static String HOME_REPORT_GAME_TOP_WIN_LIST = "HOME_REPORT::GAME_TOP_WIN_LIST";
    public static String PROXY_REPORT_DATA = "PROXY_REPORT_DATA_%d";
    public static String HOME_REPORT_GAME_PROFIT = "HOME_REPORT::GAME_PROFIT";

    /**
     * 群组消费锁
     */
    public static String GROUP_FLUSH_LOCK_KRY = "GROUP_FLUSH_LOCK_KRY%d";

    /**
     * 代充
     */
    public static String REPLACE_PAY_LOCK_KEY = "REPLACE_PAY_LOCK_KEY";

    /**
     * 用户行为异常监控-看卡不存脚本跑批时间缓存
     */
    public static String RISK_EXCEPTION_WAIT_RECHARGE_JOB_TIME = "RISK::EXCEPTION_WAIT_RECHARGE_JOB_TIME";

    /**
     * 用户行为异常监控-多次充值失败脚本跑批时间缓存
     */
    public static String RISK_EXCEPTION_FAIL_RECHARGE_JOB_TIME = "RISK::EXCEPTION_FAIL_RECHARGE_JOB_TIME";

    /**
     * 用户行为异常监控-充值受限脚本跑批时间缓存
     */
    public static String RISK_EXCEPTION_LIMIT_RECHARGE_JOB_TIME = "RISK::EXCEPTION_LIMIT_RECHARGE_JOB_TIME";

    public static String VOICE_CODE_USAGE_HOUR = "oneHour_%s_%s";

    public static String VOICE_CODE_USAGE_DAY = "oneDay_%s_%s";

    public static String GROUP_TAG_KEY = "group::group_tag_%d";

//    public static String GROUP_TAG_USER_RELATION_KEY = "group::user_relation_group_tag";

    /**
     * 标签群组刷新锁
     */
    public static String GROUP_FLUSH_LOCK_KEY = "GROUP_FLUSH_LOCK";

    /**
     * 标签绑定锁
     */
    public static String USER_TAG_BIND_LOCK_KEY = "USER_TAG_BIND_LOCK_%s";

    /**
     * 用户代理输赢报表详情
     */
    public static String PROXY_USER_DETAIL_REPORT_KEY = "proxy_user::PROXY_USER_DETAIL_KEY_P%s_T%s_U%s_DS%s_DE%s_%s_%s";

    /**
     * 用户代理输赢报表详情
     */
    public static String PROXY_USERS_KEY = "proxy_users::PROXY_USERS_KEY_P%s";

    /**
     * IP 简讯发送次数
     */
    public static String VALIDATE_IP_FAIL_TIMES = "validate_ip_fail_times::VALIDATE_IP_FAIL_TIMES_%s";


    /**
     *  手機號 + ip 發送次數
     */
    public static String LIMIT_SEND_SMS_TIMES = "limit_send_sms_times::LIMIT_SEND_SMS_TIMES_%s_%s";

    /**
     * geetest 检测存入redis中的极验云状态标识
     */
    public static String CHECK_GEETEEST_STATUS = "CHECK_GEETEEST_STATUS";

    /**
     * geetest redis过期时间，单位/秒
     */
    public static String GEETEEST_STATUS_EXTIME = "GEETEEST_STATUS_EXTIME";

    public static String USER_BALANCE_TRANSACTION_LOCK_KEY = "user:balance:transaction:LOCK";

    public static String HG_GAME_TOKEN = "HG_GAME_TOKEN";

    /**
     * 紅包雨獎金 (list)
     * 1. %s: activityId 紅包雨活動ID
     * 2. %s: timeStr yyyyMMddHHmmss
     * 3. %s: redPackageLevelIndex
     */
    public static String RED_PACKET_REWARD = "RED_PACKET_REWARD:%s:%s:%s";

    /**
     * 紅包雨使用者參與記錄 (hash)
     * 1. %s: activityId 紅包雨活動ID
     * 2. %s: timeStr yyyyMMddHHmmss
     */
    public static String RED_PACKET_USER_JOIN = "RED_PACKET_USER_JOIN:%s:%s";

    /**
     * 紅包雨使用者開啟氣泡記錄 (hash)
     * 1. %s: activityId 紅包雨活動ID
     * 2. %s: timeStr yyyyMMddHHmmss
     */
    public static String RED_PACKET_USER_OPEN = "RED_PACKET_USER_OPEN:%s:%s";

    /**
     * 紅包雨使用者搶紅包記錄 (hash)
     * 1. %s: activityId 紅包雨活動ID
     * 2. %s: timeStr yyyyMMddHHmmss
     */
    public static String RED_PACKET_USER_TAKE = "RED_PACKET_USER_TAKE:%s:%s";

    /**
     * 紅包雨使用者鎖 (lock)
     * 1. %s: userId 使用者ID
     */
    public static String RED_PACKET_LOCK_KEY = "RED_PACKET_LOCK_KEY:%s";

    /**
     * 遊戲相關 Key
     */
    public static String GAME_OF_TYPE_KEY = "GAME_OF_TYPE_KEY:%s";
    public static String SPORT_GAME_OF_BET_TYPE_KEY = "SPORT_GAME_OF_BET_TYPE_KEY:%s";
    public static String SPORT_GAME_OF_BET_TEAM_KEY = "SPORT_GAME_OF_BET_TEAM_KEY:%s";

    public static String USER_DIALOG_INFO = "USER_DIALOG_INFO_%s";

    /**
     * 织梦商城相關 Key
     */
    public static String WEAVING_DREAM_MALL_LOCK_KEY = "WEAVING_DREAM_MALL_LOCK_KEY:%s";
    public static String WEAVING_DREAM_MALL_TIME = "WEAVING_DREAM_MALL_TIME:%s";

    public static String EVERYONE_SYSCONFIG_KEY = "EVERYONE_SYSCONFIG";
    public static String EVERYONE_SYSCONFIG_PATTERN_KEY = "EVERYONE_SYSCONFIG_PATTERN";
    public static String EVERYONE_PLATFORM_CONFIG_KEY = "EVERYONE_PLATFORM_CONFIG";
    public static String EVERYONE_SHOW_WORDS_KEY = "EVERYONE_SHOW_WORDS";

    public static String USER_MARQUEE_LIST = "user_marquee::USER_MARQUEE_LIST:%s:%s:%s";

    public static String USER_MARQUEE_LISTS = "user_marquee::USER_MARQUEE_LIST";

    public static String TEL_MAINTAIN_MESSAGE_CONFIG_REDIS_KEY = "TEL_MAINTAIN_MESSAGE_CONFIG_REDIS_KEY";

    /*
    global-backend config, move from ZooKeeper
     */
    public static String BACK_CONFIG = "config::%s";
    public static String DAILY_SIGN = "DAILY_SIGN";
    public static String PLATFORM_SIGN = "PLATFORM_SIGN";
    public static String GAME_REBATE = "GAME_REBATE";
    public static String REBATE_TEMPLATE = "REBATE_TEMPLATE";
    public static String USER_TASK_INDEX_PIC = "USER_TASK_INDEX::PIC";
    public static String BACK_CONFIG_WITHDRAW = "config::WITHDRAW";
    public static String BACK_CONFIG_WITHDRAW_WITH_ARG = "config::WITHDRAW::%s";
    public static String POLICY = "POLICY";
    public static String PROXY = "PROXY";
    public static String MIN = "MIN";
    public static String MAX = "MAX";
    public static String USDT = "USDT";
    public static String PLATFORM = "PLATFORM";
    public static String USDT_PROXY = "USDT_PROXY";
    public static String TRANSFER = "TRANSFER";
    public static String REBATE_VIP_LIMIT = "REBATE_VIP_LIMIT";

    /**
     *  2FA Key
     */
    public static String TWO_FACTOR_AUTH_LIMIT_OTP_VERIFY_TIMES = "TWO_FACTOR_AUTH::LIMIT_OTP_VERIFY_TIMES_%s";
    public static String TWO_FACTOR_AUTH_REQ_TOKEN = "TWO_FACTOR_AUTH::TOKEN_%s";
    public static String TWO_FACTOR_AUTH_REQ_LOGIN = "TWO_FACTOR_AUTH::LONGIN_%s";

    public static String ADMIN_TWO_FACTOR_AUTH_LIMIT_OTP_VERIFY_TIMES = "ADMIN_TWO_FACTOR_AUTH::LIMIT_OTP_VERIFY_TIMES_%s";
    public static String ADMIN_TWO_FACTOR_AUTH_REQ_TOKEN = "ADMIN_TWO_FACTOR_AUTH::TOKEN_%s";
    public static String ADMIN_TWO_FACTOR_AUTH_REQ_LOGIN = "ADMIN_TWO_FACTOR_AUTH::LONGIN_%s";

    /**
     * 用戶活动結算用锁
     */
    public static String ACTIVITY_USER_SETTLEMENT = "ACTIVITY_USER_SETTLEMENT_%s_%s";

    /**
     * forehead 限流
     */
    public final static String RATE_LIMIT_CONFIG = "RATE_LIMIT::config";
    public final static String RATE_LIMIT_KEY_SETTING = "RATE_LIMIT::%s_setting";

    /**
     * 賽事對陣圖-
     */
    public static String GAME_LEAGUE_SOCCER_MATCH_TEAM_KEY = "game::game_league_soccer_match_team_%s";

    /**
     * 全时段悦享安全支付(NEW USDT) LOCK KEY
     * 1. %s: ORDER ID
     */
    public static String NEW_USDT_LOCK_KEY = "NEW_USDT_LOCK_KEY:%s";

    public static String DEF_LIMIT_TIMES = "login_times::DEF_LIMIT_TIMES";

    /**
     * 批次插入 GAMEBET 鎖
     */
    public static String BATCH_INSERT_GAMEBET_LOCK_KEY = "BATCH_INSERT_GAMEBET_LOCK_KEY";

    /**
     * USDT全时段礼赠 (activity_id: 201) LOCK KEY
     * 1. %s: ORDER ID
     */
    public static String USDT_201_LOCK_KEY = "USDT_201_LOCK_KEY:%s";

    /**
     * 实名存送新体验 LOCK KEY
     * 1. %s: ORDER ID
     */
    public static String REAL_NAME_RECHARGE_LOCK_KEY = "REAL_NAME_RECHARGE_LOCK_KEY:%s";

    /**
     * 創建遊戲鎖 ％s userId +  channelId
     */
    public static String CREATE_GAME_USER_LOCk_BY_USER_ID_APPEND_CHANNEL_ID_LOCK_KEY = "CREATE_GAME_USER_LOCk_BY_USER_ID_APPEND_CHANNEL_ID_LOCK_KEY_%s";

    public static String GAME_LIMIT_API_KEY= "GAME_LIMIT_API_KEY_%s";

    /**
     * 寫入打賞記錄併發鎖
     */
    public static String GAME_DONATE_LOCK = "GAME_DONATE_LOCK_%s";

    public static String GAME_LEAGUE_HOT_VIDEO_ORDER = "GAME_LEAGUE_HOT_VIDEO_ORDER_%s";

    public static String GAME_LEAGUE_HOT_LIVE_NEWS_ORDER = "GAME_LEAGUE_HOT_LIVE_NEWS_ORDER_%s";

    public static String PLATFORM_ADDRESS_LOCK = "PLATFORM_ADDRESS_LOCK_%s";


    /**
     * GOPay存送活动(GOPAY) LOCK KEY
     * 1. %s: ORDER ID
     */
    public static String GOPAY_PLATFORM_LOCK_KEY = "GOPAY_PLATFORM_LOCK_KEY:%s";
}
