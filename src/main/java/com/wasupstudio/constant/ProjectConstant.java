package com.wasupstudio.constant;


import org.springframework.util.ObjectUtils;

/**
 * 项目常量
 */
public final class ProjectConstant {

    public static final String ENCODING = "utf-8";
    public static final String MSG_TYPE_REGISTER = "mobile_register"; // 手机注册
    public static final String MSG_TYPE_BIND = "mobile_bind"; // 手机绑定
    public static final String MSG_TYPE_UNBIND = "mobile_unbind"; // 手机解绑
    public static final String MSG_TYPE_CARDDEL = "card_del"; // 删除银行卡
    public static final String MSG_TYPE_CARD_BIND = "card_bind"; // 绑定银行卡
    public static final String MSG_TYPE_SETPASS = "set_pass";  // 修改密码
    public static final String MSG_TYPE_FINDPASS = "find_pass"; // 找回密码
    public static final String MSG_TYPE_CASHSECURITY = "cash_security"; // 提现安全
    public static final String MSG_TYPE_LOGINSECURITY = "login_security"; // 登录安全
    public static final String MSG_TYPE_CASH = "cash"; // 提现
    public static final String MSG_TYPE_LOGIN = "login"; // 登录
    public static final String MSG_TYPE_LOGINCHECK = "login_check"; // 登录检查
    public static final String MSG_TYPE_UPDATE = "mobile_update"; // 更新手機號碼
    public static final String MSG_TYPE_CHECK_USER_PROCESS = "check_user_process"; // 优化登入注册流程
    public static final String MSG_TYPE_ADDRESS_BIND = "address_bind"; // 地址绑定
    public static final String MSG_TYPE_ADDRESS_UNBIND = "address_unbind"; // 地址解绑
    public static final String MSG_TYPE_G_COIN_UNBIND="g_coin_unbind"; // G幣解绑
    public static final String MSG_TYPE_PLATFORM_COIN_BIND = "platform_coin_bind"; //平台幣綁定
    public static final String MSG_TYPE_PLATFORM_COIN_UNBIND = "platform_coin_unbind"; //平台幣解綁


    public static final String EMAIL_TYPE_BIND = "email_bind"; // 邮箱绑定
    public static final String EMAIL_TYPE_UNBIND = "email_unbind"; // 邮箱解绑
    public static final String EMAIL_TYPE_FINDPASS = "find_pass"; // 找回密码
    public static final String EMAIL_TYPE_SETPASS = "set_pass";

    public static final String FIND_PWD_MOBILE = "mobile";
    public static final String FIND_PWD_EMAIL = "email";
    public static final String FIND_PWD_VOICE = "voice";
    public static final String FIND_PWD_VALIDATION = "validation";
    public static final int REPORT_DATA_DAYS = 30;

    // 默认头像
    public static final String DEFAULT_HEAD_URL = "/img/5ad560be5122ad4166576975";

    // 默认国家代码
    public static final int DEFAULT_COUNTRY_CODE = 86;

    // 随机昵称长度
    public static final int RADOM_NICK_NAME_LENGTH = 10;

    // 最上级代理用户ID
    public static final int SUPPROXY_USER_ID = 1;
    // 最上级代理用户账号
    public static final String SUPPROXY_USER_NAME = "admin";
    // 默认子代理数量
    public static final int DEFAULT_SUBPROXY_COUNT = 10;
    // 默认代理模式
    public static final int DEFAULT_COMMISSION_ID = 1;
    // 层级
    public static final int SUPPROXY_PROXY_LEVEL = 1;
    // 用户初始群组
    public static final int DEFAULT_USER_GROUP = 1;

    // 频繁取消充值次数限制
    public static final int CANCEL_RECHARGE_MAX_TIMES = 5;

    // 电销任务领取最大数量限制
    public static final int TELLSALE_RECEIVE_MAX = 30;

    // 报表下载记录最大限制
    public static final int DOWNLOAD_RECORD_MAX = 5000;


    // 数据库IN查询最大限制
    public static final int DATABASE_IN_RECORD_MAX = 5000;


    /**
     * 各种状态定义
     */
    public final class PaymentChannelStatus {
        public final static int SUCCESS = 0;// 正常状态
        public final static int FAILED = 1;// 未选中
    }

    /**
     * 游戏注单状态
     */
    public final class GameBetStatus {
        public final static int noSettlement = 0;// 未结算
        public final static int settlement = 1;// 已结算
        public final static int fail = 2;// 投注失敗
    }

    /**
     * 资金变更类型
     */
    public final class ChargeTypeStatus {
        public final static String INC = "I";// 增加
        public final static String DEC = "O";// 减少
    }

    /**
     * 资金变更类型
     */
    public final class AmountTypeStatus {
        public final static int INC = 1;// 增加
        public final static int DEC = 0;// 减少
    }

    /**
     * 卡类型
     */
    public final class BankCardTypeStatus {
        public final static int CHARGE = 1;// 充值
        public final static int WITHDRAW = 2;// 提现
    }

    /**
     * 游戏注单时间方式选择
     */
    public final class GameBetTime {
        public final static int betTime = 0;// 投注时间
        public final static int payOutTime = 1;// 结算时间
    }

    public final class ActivityRewardType {
        public final static int singleItem = 1;// 单品
        public final static int task = 2;// 实物
        public final static int bag = 3;// 包
    }

    public final class ActivityBagType {
        public final static int part = 1;// 抽取部分
        public final static int all = 2;// 全部获得
        public final static int vip = 3;// 按vip发放
    }

    public final class ActivityReward {
        public final static int BONUS = 1; //彩金
        public final static int GIFT = 2; //實體禮物
    }

    public final class ActivityRewardStatus {
        public final static int WAITING = 1; //待領
        public final static int RECEIVED = 2; //已領
    }

    public final class ActivityUserRewardType {
        public final static int BONUS = 1;//彩金
        public final static int IN_KIND = 2;//實物
        public final static int FUXING_GAOZHAO = 3;//福星高照
        public final static int NISSIN_DOUJIN = 4;//日進斗金
        public final static int MYSTERY_REWARD = 5;//神秘獎勵
    }

    public final class ActivityGiftConfigType {
        public final static int IN_KIND = 1;//實物
        public final static int CUSTOMIZED = 2;//訂製
        public final static int BONUS = 3;//彩金
    }

    public final class ActivityMoonFestivalNationalDayStatus {
        public final static int NOT_SATISFY = 0;//不符合
        public final static int RECEIVED = 1;//已領
        public final static int WAITING = 2;//待領
    }

    public final class ActivityDailyTaskStatisticsStatus {
        public final static int NOT_SETTLE = 0;//未開始結算
        public final static int UNDONE = 1;//未完成
        public final static int DONE = 2;//已完成
    }
    public final class ActivityRecordStatus {
        public final static int NOT_REDEEM = 0;//未領取
        public final static int REDEEMED = 1;//已領
        public final static int EXPIRED = 2;//已過期
        public final static int INCOMPATIBLE = 3;//不符合
    }

    /**
     * 各种状态定义
     */
    public final class Status {
        public final static int NORMAL = 0;// 正常状态
        public final static int UNSELECTED = 0;// 未选中
        public final static int SELECTED = 1;// 已选中
        public final static int PENDING = 0;// 待处理
        public final static int SUCCESS = 1;// 处理成功
        public final static int DISABLE = 1;// 禁用
        public final static int FAILED = 2; // 处理失败
        public final static int DELETE = 2; // 删除
        public final static int PROCESSING = 3; // 处理中
        public final static int SYSTEMEXCEPTION = 5; // 系统异常
        public final static int MAINTAIN = 6; // 正在维护
        public final static int EXISTENT = 7; // 不存在
        public final static int DELETED = 9; // 不存在
        public final static int ENABLE = 1;//启用
        public final static int NO_ENABLE = 0;//未启用


    }

    /**
     * 异常提现类型
     */
    public final class WithdrawRiskType {
        public final static int NORMAL = 0;// 正常状态
        public final static int FIRST_AMOUNT_RISK = 1;// 首提金额过大
        public final static int SINGLE_AMOUNT_RISK = 2;// 单笔金额过大
        public final static int DAILY_AMOUNT_RISK = 3;// 当日金额过大
        public final static int DAILY_TIMES_RISK = 4;// 频繁提现
        public final static int DAILY_PROFIT_RISK = 5;// 频利润异常
        public final static int DAILY_IP_RISK = 6;// IP多账号
        public final static int DAILY_DEVICE_RISK = 7;// 设备多账号
    }

    /**
     * 异常提现类型
     */
    public final class UserNoticeType {
        public final static int RECHARGE = 1;// 充值
        public final static int WITHDRAW = 2;// 提现
        public final static int TRANSFER = 3;// 转账
        public final static int ADD_AMOUNT = 4;// 增加金额
        public final static int SUB_AMOUNT = 5;// 减少金额
        public final static int TRANSFER_IN = 6;// 转入
        public final static int USERBACK = 7;// 老用户赠送彩金
    }

    // 群组提现类型
    public final class GroupWithdrawType {
        public final static int COMMON = 0;// 通用
        public final static int MIN = 1;// 小额
        public final static int MAX = 2;// 大额
    }

    // 提现拆单类型
    public final class WithdrawSplitType {
        public final static int COMMON = 0;// 通用
        public final static int MAX = 1;// 大额
        public final static int PROXY_MAX = 2;// 代理大额
        public final static int MIN = 3;// 小额
    }

    /**
     * 活动相关状态
     */
    public final class ActivityStatus {
        public final static int WIN_A_PRIZE = 1;
        public final static int NO_PRIZE = 0;
    }

    /**
     * 系统通道类型
     */
    public final class SystemChannelType {
        public final static int MSG = 1;// 短信
        public final static int EMAIL = 3;// 邮箱
        //        public final static int EMAIL = 2;// 邮箱
//        public final static int BANK = 3;// 银行卡实名
        public final static int CUSTOMER = 5; // 客服接口
        public final static int VOICE_MSG = 6; // 語音短信
        public final static int UPSTREAM_MSG = 7; // 上行短信
        public final static int CAPTCHA = 8; // 圖形驗證廠商(DingXiang, ... etc.)
        public final static int SHOPPING_MALL = 9; // 商城点数購物商城
        public final static int CLOUD_STORAGE = 10; // 雲儲存(AWS-S3, Ali-OSS, GCP-GCS)
        public final static int ACTIVITY_AUTO_VALIDATION = 11; // 活動管理(自動審核)
        public final static int TELEPHONE_MAINTAIN = 12; // 電維短信
        public final static int RATE_LIMIT_CONFIG = 13; //
    }

    public final class SystemCustomerName {
        public final static String LIVE_CHAT = "Livechat";
        public final static String SMART_CUSTOMER = "SmartCustomerSystem";
        public final static String A_CHAT = "Achat";
    }

    public final class SystemApiStatus {
        public final static int NORMAL = 0;// 正常
        public final static int DISABLE = 1;// 停用
        public final static int DELETED = 2;// 删除
    }

    public final class MessageSmsTemplateStatus {
        public final static int DISABLE = 0;// 停用
        public final static int ENABLE = 1;// 啟用
    }

    public final class MessageSmsChannelTemplateStatus {
        public final static int DISABLE = 0;// 停用
        public final static int ENABLE = 1;// 啟用
    }

    public final class SystemAdminStatus {
        public final static int NORMAL = 1;// 正常
        public final static int DISABLE = 2;// 停用
        public final static int DELETED = 3;// 删除
    }

    public final class CaptchaAuthStatus {
        public final static int DISABLE = 0;// 停用
        public final static int ENABLE = 1;// 啟用
    }

    public final class CaptchaAuthKey {
        public final static String RULE = "rule";
        public final static String RULE_STATUS = "status";
        public final static String RULE_TYPE = "type";
        public final static String RULE_TYPE_APPLY = "apply";
        public final static String RULE_TYPE_VALIDATE = "validate";
        public final static String APPLY_LIMIT = "applyLimit";
        public final static String TIME_LIMIT = "timeLimit";
        public final static String SUSPENDED_TIME = "suspendedTime";
        public final static String HISTORY_TIME = "historyTime";
        public final static String HISTORY_KEY = "captcha_history_%s_%s_%s";
        public final static String SUSPEND_KEY = "captcha_suspend_%s_%s_%s";
        public final static int CAPTCHA_TYPE = 8;
        public final static String GEE_TEST_SETTING = "gee_test_setting";
        public static final String GEE_TEST_ID = "geetestId";
        public static final String GEE_TEST_KEY = "geetestKey";
        public static final String GEE_TEST_REGISTER_URL = "registerUrl";
        public static final String GEE_TEST_VALIDATE_URL = "validateUrl";
        public static final String GEE_TEST_STATUS_URL = "statusUrl";
        public static final String GEE_TEST_FAILED = "fail";
        public static final String GEE_TEST_SUCCESS = "success";
        public static final String GEE_TEST_CHALLENGE_KEY = "challenge";
        public static final String GEE_TEST_SECCODE_KEY = "seccode";
        public static final String GEE_TEST_CHALLENGE = "geetest_challenge";
        public static final String GEE_TEST_VALIDATE = "geetest_validate";
        public static final String GEE_TEST_SECCODE = "geetest_seccode";
        public static final String GEE_TEST_STATUS_KEY = "seccode";
        public final static String VERIFICATION = "verification";
        public final static String IMG_TOKEN = "imgToken";
        public final static String RID = "rid";
        public static final String GEE_TEST4_LOT_NUMBER = "lot_number";
        public static final String GEE_TEST4_CAPTCHA_OUTPUT = "captcha_output";
        public static final String GEE_TEST4_PASS_TOKEN = "pass_token";
        public static final String GEE_TEST4_GEN_TIME = "gen_time";
        public static final String GEE_TEST4_SIGN_TOKEN = "sign_token";
        public static final String GEE_TEST4_SIGN_RESULT = "result";
        public static final String GEE_TEST4_SIGN_REASON = "reason";
    }

    /**
     * 用户帐变类型
     *
     * @author Leo
     * <p>
     * 2015年8月4日 上午11:58:41
     */
    public final class BalanceType {
        //交易类型：1存款，2提现，3转账，4红利，5返水，6人工加币，7人工减币，8上级转入，9佣金 10增加流水 11减少流水 12充值奖励 13VIP升级奖励 14红包 15系统奖励
        //16派彩 17老用户活动红利 18节日礼金 19生日礼金 20加币-不計公司收入 21充值补分 22充值减分
        public final static int RECHARGE = 1;
        public final static int WITHDRAW = 2;
        public final static int TRANSFER = 3;
        public final static int BONUS = 4;
        public final static int REBATE = 5;
        public final static int ADD = 6;
        public final static int DIS = 7;
        public final static int CREDIT = 8;
        public final static int COMMISSION = 9;
        public final static int ADDWATER = 10;
        public final static int SUBWATER = 11;
        public final static int REWARD = 12;
        public final static int VIPGIFT = 13;
        public final static int REDPACKET = 14;
        public final static int ADMINGIFT = 15;
        public final static int PAYOUT = 16;
        public final static int USERBACKBONUS = 17;
        public final static int FESTIVALlPACKET = 18;
        public final static int BIRTHDAYGIFT = 19;
        public final static int ADD_NO_PROFIT = 20;
        public static final int ADD_RECHARGE = 21;
        public static final int DIS_RECHARGE = 22;

        public final static int API_TRANSFER = 1; // 接口转账出款
        public final static int MANUAL_TRANSFER = 0; // 手动转账出款
    }

    public final class ProxyBalanceType {
        //交易类型：1返佣金，2佣金给用户上分，3充值额度给用户上分，4充值，5调整红利，6公司额度，7提现
        public final static int COMMISSION = 1;
        public final static int COMM_CREDIT = 2;
        public final static int RECHARGE_CREDIT = 3;
        public final static int RECHARGE = 4;
        public final static int REBATE = 5;
        public final static int ADD = 6;
        public final static int WITHDRAW = 7;

    }

    /**
     * 平台幣別 種類 gl_user_platform_wallet protocol
     */
    public final class CoinType {
        public static final int GOBAO = 1; // 购宝
        public static final int FPAY = 2; // FPAY
    }

    public static String getCoinName(String type) {
        if (!ObjectUtils.isEmpty(type)) {
            if (Integer.valueOf(type).intValue() == CoinType.GOBAO) {
                return "购宝";
            }
            if (Integer.valueOf(type).intValue() == CoinType.FPAY) {
                return "FPAY交易所";
            }
        }
        return "未知";
    }

    public static String getPlatformCoin(String type) {
        if (!ObjectUtils.isEmpty(type)) {
            if (Integer.valueOf(type).intValue() == CoinType.GOBAO) {
                return "G币";
            }
            if (Integer.valueOf(type).intValue() == CoinType.FPAY) {
                return "FP币";
            }
        }
        return "未知";
    }


    public final class PaymentType {
        /**
         * @Author: Sim
         * @Description: 支付方式
         * @params:
         * @Date: 上午10:46 2018/5/14
         */
        public final static int CHUKUAN = 0; // 出款

        /**
         * @Description: 支持的9种支付方式
         * @Param:
         * @return:
         * @Author: Sim
         * @Date: 2019-07-16 19:59
         */
        // 银联支付(網銀支付)
        public final static int WANGYIN = 1;
        // 支付宝扫码
        public final static int ZHIFUBAO = 2;
        // 微信支付
        public final static int WEIXIN = 3;
        // QQ支付
        public final static int QQ = 4;
        // 银联支付
        public final static int UNION = 5;
        // 京东支付
        public final static int JD = 6;
        // 银行卡转账
        public final static int CARDZZ = 7;
        // 支付宝转账
        public final static int ZHIFUBAOZZ = 8;
        // 微信转账
        public final static int WEIXINZZ = 9;
        // 云闪付
        public final static int YUNSHANFU = 10;
        // 银行卡转账快速
        public final static int CARDZZQUICK = 11;
        // 虚拟币充值 USDT(ERC-20)
        public final static int BLOCKCHAIN_ERC = 12;
        // 虚拟币充值 USDT(TRC-20)
        public final static int BLOCKCHAIN_TRC = 14;

        public final static int MXPAY_TC3IN1 = 13;
        // G币
        public final static int GOBAO = 18;

        public final static int PLATFORMFPAY = 27; // FPAY

        public final static int PAYMENT_ID_GOPAY = 30;

        // End

        public final static int QQ_SCAN_PAY = 13; // QQ扫码支付
        public final static int JD_SCAN_PAY = 15; // 京东扫码支付
        public final static int ZHIFUBAO_ZHUANZ = 16; // 支付宝转账
        public final static int WEIXIN_ZHUANZ = 18; // 微信转账

        public final static int WANGYIN2 = 19; // 网银支付二
        public final static int WANGYIN3 = 20; // 网银支付三
        public final static int WEIXIN2 = 21; // 微信支付二

        public final static int WEIXIN3 = 23; // 微信支付三
        public final static int XINYONGKA = 24; // 信用卡支付
        public final static int FPAY_PLATFORM = 27; // FPAY

        /**
         * @Author: Sim
         * @Description: 支付渠道
         * @params:
         * @Date: 上午10:46 2018/5/14
         */
        public final static int EHUIKA = 38; // 易汇卡支付
        public final static int CHANGHUIPAY = 55; // 畅汇支付
        public final static int HUYUNPAY = 56; // 虎云支付
        public final static int FENGYUN = 57; // 风云支付
        public final static int JIUFU = 58; // 玖付支付
        public final static int GUOZI = 59; // 锅子支付
        public final static int EASYPAY = 60;// easypay
        public final static int YONGSHENG = 62;// 永胜
        public final static int QINGHAI = 63;// 青海
        public final static int FENGYUN2 = 64; // 风云支付2
        public final static int APPLE = 65; // 苹果支付(上海團隊)
        public final static int MXPAY = 66; // MXPAY支付
        public final static int YONGHENG = 67; // 永恒支付
        public final static int RISHENG = 68; // 日胜支付
        public final static int WATERPAY3 = 87; // WATERPAY代付3
        public final static int EATPAY = 71; // EATPAY
        public final static int HIPAY1 = 72; // HIPAY1
        public final static int HIPAY2 = 73; // HIPAY2
        public final static int ANJIE = 74;// 安捷支付
        public final static int HEPAY = 75;// HEPAY
        public final static int SAVEPAY = 76;// SAVEPAY
        public final static int JBPKPAY = 77;// JBPPAY K
        public final static int JBPJPAY = 78;// JBPPAY J
        public final static int TAITANPAY = 79;// 泰坦支付
        public final static int NEWPAY2 = 80;// 万博新支付
        public final static int MXPAY2 = 81; // MXPAY2支付
        public final static int FIVESTAR = 82; // 五星代付1
        public final static int TIANHONG = 83; // 五星代付1
        public final static int ATCPAY = 84;
        public final static int FENGYUN3 = 85; // 风云支付3
        public final static int WLPAY = 88; //未來支付
        public final static int MSPAY1 = 90; //码上支付1
        public final static int MSPAY2 = 91; //码上支付2
        public final static int MSPAY3 = 92; //码上支付3


        public final static int DAHENG_PAY_QQ = 749; //大亨支付(QQ支付)
        public final static int YINHERPAY_ERC = 750;// 银河支付_ERC
        public final static int YINHERPAY_TRC = 752;// 银河支付_TRC
        public final static int YUANBAO = 753; // 源宝支付
        public final static int LONGYU6 = 754; // 龙游支付(支付宝话费慢充)
        public final static int LONGYU5 = 755; // 龙游支付(支付宝话费快充ios端)
        public final static int LONGYU4 = 756; // 龙游支付(支付宝原生)
        public final static int LONGYU3 = 757; // 龙游支付(支付宝转支付宝)
        public final static int LONGYU2 = 758; // 龙游支付(微信话费快充ios端)
        public final static int LONGYU1 = 759; // 龙游支付(微信转微信)
        public final static int DAHENG_PAY_WEIXIN_QR = 760; //大亨支付
        public final static int AI_YUE_FU = 761; // 爱乐付
        public final static int HUAN_YU_PAY = 763; // 环宇支付
        public final static int BIG_HIPPO_PAY = 765; // 大河马支付
        public final static int YONG_XIN_PAY3567 = 766; // 永信支付
        public final static int YYPAY = 767; // YY支付
        public final static int RMPAY = 768; // RM支付
        public final static int STARRYSKYPAY = 769; // 星空支付
        public final static int XUANBOPAY = 776; // 泫泊支付
        public final static int SUNFLOWER_PAY = 770;//向日葵支付
        public final static int WHALE_PAY2 = 771; // 鲸鱼支付
        public final static int WEAVING_DREAM_MALL_PAY = 772; // 织梦商城
        public final static int BAJIEPAY = 773; // 八戒支付
        public final static int QQPAY3 = 774; // QQ支付(支付宝固码H5)
        public final static int QQPAY2 = 775; // QQ支付(支付宝H5)
        public final static int QQPAY1 = 779; // QQ支付(支付宝扫码+H5)
        public final static int OFXPAY8 = 777; // 鸿运通银行卡转账
        public final static int FUKUTAROPAY_51064 = 778; // 福太郎51064
        public final static int TIANYIYINGPAY = 780; // 天浥贏纯代付
        public final static int YONGSINPAY = 781; // 永鑫支付
        public final static int TAIDAPAY = 783; // 泰达支付
        public final static int FEIYUPAY = 784; // 飞鱼支付
        public final static int JC_PAY = 788; // JC纯代付
        public final static int HEYIPAY = 786; // 和奕支付
        public final static int WEIFUPAY = 785; // 微付純代付
        public final static int COPOPAY = 787; // COPO纯代付
        public final static int CRYPTO_LEDGER_ERC = 789; // Crypto_Ledger_ERC
        public final static int CRYPTO_LEDGER_TRC = 790; // Crypto_Ledger_TRC
        public final static int JUEJUEZI5059 = 791; // 絕絕子5059
        public final static int FPAY = 792; // Fpay纯代付
        public final static int JUEJUEZI5060 = 794; // 絕絕子5060
        public final static int YUANDONGPAY = 793; //遠東純代付
        public final static int DS_PAY = 797; //DS支付
        public final static int LEYIFU = 795; //乐易富
        public final static int JUPAY = 796; // 聚富jupay
        public final static int YONG_XIN_PAY = 798; // 永信支付
        public final static int DOCCHIPAY = 799; //多吉支付
        public final static int SHIANGYUN_PAY = 800;//祥云0125
        public final static int FUKUTARO_PAY_ALI = 801; // 支付寶紅包口令
        public final static int SINPAY = 802; //星之付
        public final static int JU_YUAN_PAY = 805; // 聚源支付 0135
        public final static int FNPAY=803; // 蜂鳥支付(代收)
        public final static int FNPAY2=804; // 蜂鳥支付(代付)
        public final static int DAHENG_PAY_ALI = 806; //大亨支付 ALI 支付寶紅包口令
        public final static int DAHENG_PAY = 807; //大亨支付
        public final static int HERO_PAY_40239 = 813; // 豪杰支付40239 純代付
        public final static int WATER_PAY86_MAX = 810;//WATER PAY86 (大額)
        public final static int WATER_PAY86_MIN = 809;// WATER PAY86(小額)
        public final static int GOBAO_PAY = 808;// 购宝钱包
        public final static int TOPMAV_PAY = 814;//祥云013
        public final static int MX_PAY_MEMM000340PI = 812; // MXPay MEMM000340PI 代付
        public final static int TSEAN_PAY_TRC = 815; // 策安 TRC
        public final static int FEIFAN_PAY = 816; // 非凡支付
        public final static int WHALE_PAY = 811;//鯨魚支付(純代付)
        public final static int KFC1029616_PAY = 817;//稳赢支付1029616
        public final static int KFC1023124_PAY = 818;//稳赢支付1023124
        public final static int KFC1015842_PAY = 820;//稳赢支付1015842
        public final static int AIR_PLANE_PAY = 821; // 飞机纯代付 0114
        public final static int FUKUTAROPAY_50903 = 822; // 福太郎50903
        public final static int U_PAY_3_ALIPAY_50905 = 824; // UPay 3.0 支付宝口令50905
        public final static int U_PAY_3_GATEWAY = 823; // UPay 3.0 50904
        public final static int JK_PAY = 825;
        public final static int CROWN_PAY = 826; // 皇冠支付
        public final static int UB_PAY_TRC = 827;
        public final static int LIMA_YUNSHANFU = 828;
        public final static int LIMA_REDPACKET = 829;
        public final static int LIMA_ZHIFUBAO = 830;
        public final static int OTC_PAY_ERC = 836;
        public final static int OTC_PAY_TRC = 835;
        public final static int TOP_PAY = 831; // 尖端支付
        public final static int EMPEROR_PAY = 832;
        public final static int MX_PAY_ERC = 833; // MXPay ERC
        public final static int MX_PAY_TRC = 834; // MXPay TRC
        public final static int RC_PAY_ERC = 839;
        public final static int RC_PAY_TRC = 838;
        public final static int TIANKONG_PAY_DAIFU = 837;// 天空付(純代付)
        public final static int SUYUN_PAY_ERC = 842; // 速云 ERC
        public final static int SUYUN_PAY_TRC = 843; // 速云 TRC
        public final static int TSEAN_PAY_ERC = 844; // 策安 ERC
        public final static int OFXPAY7 = 840; // 鸿运通纯代付
        public final static int OFXPAY6 = 841; // 鸿运通微信红包
        public final static int HAMBIT_ERC = 845;
        public final static int HAMBIT_TRC = 846;
        public final static int ASIA_PAY = 850; // 亚洲龙
        public final static int GFPAY = 855; // 皇聖
        public final static int GUOZICOIN_TRC_WITHDRAW = 854; // 锅子(特殊出款) TRC
        public final static int OFXPAY5 = 864; // 鸿运通支付宝原生
        public final static int OFXPAY4 = 863; // 鸿运通支付宝扫码
        public final static int OFXPAY3 = 862; // 鸿运通微信话费
        public final static int OFXPAY2 = 861; // 鸿运通微信原生
        public final static int OFXPAY1 = 860; // 鸿运通微信小店原生
        public final static int POWER_PAY = 856; // 威力付
        public final static int TENGYUN_PAY_WECHAT = 851; // 騰雲支付-微信
        public final static int TENGYUN_PAY_ALI = 852; // 騰雲支付-支付寶
        public final static int TENGYUN_PAY_BANK = 853; // 騰雲支付-銀行卡
        public final static int LEPAY_ALI = 858; // 樂支付 支付寶
        public final static int CFPAY15 = 859; // 超凡支付pay.alipay.qrcode
        public final static int XINFUTONGPAY_ERC = 867; // 信付通 ERC
        public final static int XINFUTONGPAY_TRC = 868; // 信付通 TRC
        public final static int M18PAY = 866; // M18支付
        public final static int CFPAY14 = 870; // 超凡支付pay.weixin.original
        public final static int CFPAY13 = 871; // 超凡支付pay.qq.qrcode
        public final static int CFPAY12 = 872; // 超凡支付pay.weixin.more
        public final static int CFPAY11 = 873; // 超凡支付pay.alipay.original
        public final static int CFPAY10 = 874; // 超凡支付pay.alipay.qrcode
        public final static int CFPAY9 = 875; // 超凡支付pay.weixin.small
        public final static int CFPAY8 = 876; // 超凡支付pay.alipay.card
        public final static int CFPAY7 = 877; // 超凡支付pay.qq.wap
        public final static int CFPAY6 = 878; // 超凡支付pay.yunshanpay
        public final static int U_PAY_3_WECHAT = 880; // 新 永恒支付 3.0 微信扫码
        public final static int U_PAY_3_ALIPAY = 881; // 新 永恒支付 3.0 支付宝口令
        public final static int BADAOTONGPAY = 879; // 鸿运支付(八達通)
        public final static int TIGERPAY_TRC = 883; // 虎支付 (TRC)
        public final static int TIGERPAY_ERC = 884; // 虎支付 (ERC)
        public final static int SAFEPAY_ERC = 891; // 安心寶支付 (ERC)
        public final static int SAFEPAY_TRC = 892; // 安心寶支付 (TRC)
        public final static int DGSPAY = 893; //大久陞支付
        public final static int ELEPHANT_PAY = 887;//新大象支付
        public final static int LX_PAY = 894;//立鑫支付
        public final static int KFC_PAY = 885;//稳赢支付
        public final static int TIANKONG_PAY = 889;// 天空付
        public final static int AIDAIFU_PAY = 886; // AI(純代付)
        public final static int SKYPAY = 888; //skypay支付
        public final static int ALIPAY = 890;// 阿里支付
        public final static int PANJIAYUAN_ERC = 897;//潘家园支付 USDT(TRC)
        public final static int PANJIAYUAN_TRC = 898;//潘家园支付 USDT(ERC)
        public final static int KHPAY = 899;  //KH純代付
        public final static int GINTONPAY = 901;  //金通支付
        public final static int WATERPAY4 = 900; // WATERPAY支付4
        public final static int YINFUCOIN_ERC = 903;//赢付支付 USDT(ERC)
        public final static int YINFUCOIN_TRC = 904;//赢付支付 USDT(TRC)
        public final static int HEROPAY_WECHAT_CALL_CHARGE = 905; //豪杰支付(微信話費)
        public final static int HAPPYGO = 906; //HappyGo 支付
        public final static int DINGSIN_PAY_19 = 908;// 鼎信支付19
        public final static int HEROPAY = 909; //豪杰支付
        public final static int DADPAY = 911; //爹不疼支付
        public final static int DINGSIN_PAY_18 = 910;// 鼎信支付18
        public final static int FUYI_PAY = 912;//富億支付
        public final static int PANDAPAY = 913; //熊貓支付
        public final static int CFPAY5 = 896; // 超凡支付5
        public final static int CFPAY4 = 914; // 超凡支付4
        public final static int CFPAY3 = 915; // 超凡支付3
        public final static int CFPAY2 = 916; // 超凡支付2
        public final static int CFPAY1 = 917; // 超凡支付1
        public final static int DINGSIN_PAY_17 = 919;// 鼎信支付17
        public final static int DINGSIN_PAY_16 = 920;// 鼎信支付16
        public final static int SUPERPAY = 921; //SuperPay支付
        public final static int DDPAY2 = 922;//DD支付
        public final static int DINGSIN_PAY_15 = 923;// 鼎信支付15
        public final static int DINGSIN_PAY_12 = 924;// 鼎信支付12
        public final static int DINGSIN_PAY_13 = 925;// 鼎信支付13
        public final static int DINGSIN_PAY_14 = 926;// 鼎信支付14
        public final static int AN_PAY = 928; // 安富支付
        public final static int GOWELLCOIN_TRC = 931;//順心付 USDT(TRC)
        public final static int GOWELLCOIN_ERC = 932;//順心付 USDT(ERC)
        public final static int WEALTH_PAY = 933; // 財神支付
        public final static int JISU_PAY1 = 934; // 極速支付1
        public final static int JISU_PAY2 = 935; // 極速支付2
        public final static int JISU_PAY3 = 936; // 極速支付3
        public final static int U_PAY_3 = 938; // 新 永恒支付 3.0
        public final static int DINGSIN_PAY_1 = 940;// 鼎信支付1
        public final static int DINGSIN_PAY_2 = 941;// 鼎信支付2
        public final static int DINGSIN_PAY_3 = 942;// 鼎信支付3
        public final static int DINGSIN_PAY_4 = 943;// 鼎信支付4
        public final static int DINGSIN_PAY_7 = 946;// 鼎信支付7
        public final static int DINGSIN_PAY_8 = 947;// 鼎信支付8
        public final static int DINGSIN_PAY_9 = 948;// 鼎信支付9
        public final static int DINGSIN_PAY_10 = 937;// 鼎信支付10
        public final static int DINGSIN_PAY_11 = 939;// 鼎信支付11
        public final static int MANGO_PAY = 950;//芒果支付
        public final static int QC_PAY = 951; // 錢程支付
        public final static int DYNASTY_PAY = 952;//皇朝支付
        public final static int LIMA_PAY = 953;//立馬付
        public final static int CHANGCHENG_PAY = 954;// 長誠支付
        public final static int SANDBOX_PAY = 955;// 沙盒支付
        public final static int MATIC_PAY = 956; //Matic Pay
        public final static int WATERCOIN_TRC = 961;//Watercoin USDT (TRC)
        public final static int XIAONIU_PAY = 962; // 小牛支付
        public final static int JIANGNAN_PAY3 = 964; // 潘家园 (江南集市支付3.0)
        public final static int JIANGNAN_PAY2 = 966; // 风云永恒 (江南集市支付2.0)
        public final static int YONGHENG_PAY3 = 967;//永恒支付 3.0
        public final static int GUOZICOIN_TRC20 = 969;//鍋子支付 USDT
        public final static int GUOZICOIN_ERC20 = 970;//鍋子支付 USDT
        public final static int RIGHTNOWPAY = 971;//碼上支付
        public final static int BURNED_MONKEY = 974;//火猴支付
        public final static int WINWIN888PAY = 975; // WinWin888
        public final static int WATERCOIN_ERC = 976;//Watercoin USDT (ERC)
        public final static int FUKUTAROPAY = 977; // 福太郎
        public final static int LEPAY = 981;//樂支付
        public final static int JIANGNANPAY = 982; //江南集市支付
        public final static int BFPAY = 989; // 暴付
        public final static int CFPAY = 991; // 超凡支付
        public final static int XIANGYUNPAY = 993; //祥云支付
        public final static int PLATFORM_FPAY = 1076; //FPAY交易所
        public final static int CHANNEL_ID_GOPAY = 1165;


        // 提现手续费补偿金额
        /*玖付单笔2元*/
        public final static long JIUFU_WITHDRAW_FEE = 0;
        public final static long QINGHAI_WITHDRAW_FEE = 0;
        public final static long APPLE_WITHDRAW_FEE = 0;
        public final static long MXPAY_WITHDRAW_FEE = 0;
        public final static long YONGHENG_WITHDRAW_FEE = 0;
        public final static long RISHENG_WITHDRAW_FEE = 0;
    }

    public final class WithdrawProtocolMode {
        public final static String ERC_20 = "ERC20";
        public final static String TRC_20 = "TRC20";
    }

    // 提现方式，0=银行卡|1=usdt|2=平台幣
    public final class WithdrawMode {
        public final static int BANKCARD = 0;
        public final static int USDT = 1;
        public final static int PLATFORM = 2;
    }

    // 成本类型
    public final class CostType {
        public final static int BONUS = 101; // 红利
        public final static int REBATE = 102; // 返水
    }

    // 代理上分类型
    public final class CreditStatusType {
        public final static int PENDING = 0; // 申请中
        public final static int APPROVED = 1; // 已开通
        public final static int CLOSED = 2; // 已关闭
    }

    // 代理审核状态：0待审核，1一审通过，2一审拒绝，3二审通过，4二审拒绝
    public final class ProxyApprovedStatus {
        public final static int PENDING = 0;
        public final static int FIRSTSUCCESS = 1;
        public final static int FIRSTREJECTED = 2;
        public final static int SECONDSUCCESS = 3;
        public final static int SECONDREJECTED = 4;

        public final static int APPROVEDA = 1;
    }

    // 用户类型
    public final class UserType {
        public final static int USER = 0; // 用户
        public final static int PROXY = 1; // 代理
    }

    // 代理类型
    public final class ProxyType {
        public final static int LEVEL2 = 2; // 2级代理
        public final static int LEVEL3 = 3; // 3级代理
    }

    // 用户类型
    public final class DataBase {
        public final static String MASTER = "master"; // 主库
        public final static String SLAVE = "slave"; // 从库
        public final static String TIDB = "tidb"; // tidb
    }

    // 是否修改过密码
    public final class PasswordChangeStatus {
        public final static int UNCHANGED = 1;
        public final static int CHANGED = 0;
    }

    //用户状态
    public final class UserStatus {
        public final static int NORMAL = 0;
        public final static int LOCK = 1;
//        public final static int COMPLETELOCK = 1;
//        public final static int INDIRECTLOCK = 2;
//        public final static int LIMITLOCK = 4;
    }

    //用户锁定类型
    public final class UserLockType {
        public final static int LOGIN = 1;
        public final static int RECHARGE = 2;
        public final static int WITHDRAW = 3;
        public final static int TRANSFER = 4;
        public final static int BONUS = 5;
        public final static int BANKCARD = 6;
    }


    // 代理域名使用状态
    public final class UserDomainUseStatus {
        public final static int UNUSED = 0;
        public final static int USED = 1;
    }

    // 用户关系
    public final class UserRerationCfg {
        public final static int DEFAULT = 0;
        public final static int PROXY = 1;
    }

    // 老用户活动状态
    public final class UserbackCfg {
        public final static int NOTSTART = 0;
        public final static int INHAND = 1;
        public final static int ENDED = 2;
        public final static int DELETED = 9;
    }

    // 通用活动状态
    public final class CommonStatus {
        public final static int NOTSTART = 0;
        public final static int INHAND = 1;
        public final static int ENDED = 2;
        public final static int SUSPEND = 8;
        public final static int DELETED = 9;
    }

    // 余额宝活动状态
    public final class YuebaoCfg {
        public final static int OPENED = 0;
        public final static int CLOSED = 1;
    }

    // 余额宝用户状态
    public final class YuebaoUserCfg {
        public final static int OPENED = 0;
        public final static int CLOSED = 1;
    }

    // 余额宝活动状态
    public final class YuebaoRateCfg {
        public final static int NOTSTART = 0;
        public final static int OPENED = 1;
        public final static int CLOSED = 2;
        public final static int ENDED = 3;
        public final static int DELETED = 9;
    }

    // 电话回拨申请处理结果编码
    public final class CallRecordBusinessType {
        public final static int TELLSALE = 1;
    }

    // 电话回拨申请处理结果
    public final class CallbackResultCfg {
        public final static String NOTCONNECT = "尚未到回拨时间";
        public final static String WAITCONNECT = "等待回拨";
        public final static String CONNECTFAIL = "回拨失败";
        public final static String FINISHED = "已结束";
    }

    // 电话回拨申请处理结果编码
    public final class CallbackCodeCfg {
        public final static String NOTCONNECT = "not_connect";
        public final static String WAITCONNECT = "wait_connect";
        public final static String CONNECTFAIL = "connect_fail";
        public final static String FINISHED = "finished";
    }

    // 电话回拨失败信息模板
    public static final String CALLBACK_FAIL_RESULT = "尊敬的用户，客服因为【%s】的原因未能成功和您取得联系。请您重新申请。";

    // 全局配置key名称
    public final class GlobalConfigKey {
        public final static String GOOGLE_SECURITY = "google_security";
        public final static String PROXY_PHONE = "proxy_phone";
        public final static String PROXY_EMAIL = "proxy_email";
        public final static String PROXY_CHAT = "proxy_chat";
    }

    // 余额宝修改利率限制天数
    public static final int YUEBAO_CHANGE_LIMIT_DAYS = 7;

    // 余额宝重新开启限制天数
    public static final int YUEBAO_REOPEN_LIMIT_DAYS = 1;


    public final class SmsType {
        public final static String SMS_CODE = "smsCode";
        public final static String VOICE_SMS_CODE = "voiceSmsCode";

    }

    public final class ClientType {
        public final static int pc = 0;
        public final static int h5 = 1;
        public final static int android = 2;
        public final static int ios = 3;
    }

    public final static String OS_TYPE_PARAMETER_NAME = "os_type";
    public final static String USER_INFO_PARAMETER_NAME = "user_info";
    public final static String REFERER_PARAMETER_NAME = "referer";
    public final static int OPEN = 0;

    public final class online {
        public final static int isOnline = 1;
        public final static int noOnline = 2;
    }

    public final class TradeTransferType {
        //1转入 2转出 3派彩 4投注
        public final static int TRANSFER_IN = 1;
        public final static int TRANSFER_OUT = 2;
        public final static int INCOME = 3;
        public final static int EXPENSE = 4;
    }

    public final class AttachmentType {
        public final static int USER_MANUAL_BIND_CARD = 1;
    }

    public final class Profit {
        public final static int NOJUDGE = 0;// 不用判斷

        public final static int POSITIVE_PROFIT = 1;// 公司正盈利

        public final static int NEGATIVE_PROFIT = 2;// 公司負盈利
    }

    public final class EveryOnePlatformInclude {
        public final static int NO = 0;

        public final static int YES = 1;
    }
}
