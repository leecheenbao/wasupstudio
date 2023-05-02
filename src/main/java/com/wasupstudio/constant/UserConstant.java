package com.wasupstudio.constant;

/**
 * 用户常量
 *
 * @author leolee
 */
public interface UserConstant {
    // 用户token
    String TOKEN = "token";
    // 用户ID
    String UID = "uid";
    // 用户ID
    String USER_ID = "userId";
    // 用户ip
    String IP = "ip";

    /**
     * @Description: 用户资金操作类型
     * @Param:
     * @return:
     * @Author: Sim
     * @Date: 2019-07-16 14:19
     */
    enum UserOperateType {
        PROXY_CHANGE(UserOperateType.PROXY_CHANGE_OPTTYPE, "变更上级代理"),
        CONTACT_CHANGE(UserOperateType.CONTACT_CHANGE_OPTTYPE, "变更联系方式"),
        BANK_RESET(UserOperateType.BANK_RESET_OPTTYPE, "重置银行信息"),
        FULL_LOCK(UserOperateType.FULL_LOCK_OPTTYPE, "完全锁定"),
        HALF_LOCK(UserOperateType.HALF_LOCK_OPTTYPE, "间接锁定"),
        LEVEL_LOCK(UserOperateType.LEVEL_LOCK_OPTTYPE, "本级锁定"),
        LIMIT_LOCK(UserOperateType.LIMIT_LOCK_OPTTYPE, "限额锁定"),
        LIMIT_UN_LOCK(UserOperateType.LIMIT_UN_LOCK_OPTTYPE, "解除限额锁定"),
        UN_LOCK(UserOperateType.UN_LOCK_OPTTYPE, "解除锁定"),
        COMMISSION_CHANGE(UserOperateType.COMMISSION_CHANGE_OPTTYPE, "修改代理返佣模式"),
        CARD_BIND(UserOperateType.CARD_BIND_OPTTYPE, "绑定银行卡"),
        UN_LOCK_CARD_BIND(UserOperateType.USER_BANKCARD_UNLOCK_OPTTYPE, "银行卡解锁"),
        CARD_DELETE(UserOperateType.CARD_DELETE_OPTTYPE, "删除银行卡"),
        CARD_CHANGE(UserOperateType.CARD_CHANGE_OPTTYPE, "变更选中银行卡"),
        REMARK_CHANGE(UserOperateType.REMARK_CHANGE_OPTTYPE, "修改备注"),
        CREDIT_CHANGE(UserOperateType.CREDIT_CHANGE_OPTTYPE, "修改上分设置"),

        ACTIVITY(UserOperateType.ACTIVITY_OPTTYPE, "活动参与申请"),

        RESET_PASSWORD(UserOperateType.RESET_PASSWORD_OPTTYPE, "找回密码"),
        UPDATE_PASSWORD(UserOperateType.UPDATE_PASSWORD_OPTTYPE, "修改密码"),
        UPDATE_SECURITY_CODE(UserOperateType.UPDATE_SECURITY_CODE_OPTTYPE, "修改手势密码"),
        UPDATE_RECHARGE(UserOperateType.UPDATE_RECHARGE_OPTTYPE, "修改充值限制"),
        UPDATE_TRANSFER(UserOperateType.UPDATE_TRANSFER_OPTTYPE, "修改转账限制"),
        LOCK_ACCOUNT(UserOperateType.LOCK_ACCOUNT_OPTTYPE, "锁定账号"),
        OPEN_WITHDRAW(UserOperateType.OPEN_WITHDRAW_OPTTYPE, "开启提现验证"),
        CLOSE_WITHDRAW(UserOperateType.CLOSE_WITHDRAW_OPTTYPE, "关闭提现验证"),
        OPEN_LOGIN_PROTECT(UserOperateType.OPEN_LOGIN_PROTECT_OPTTYPE, "开启登录保护"),
        CLOSE_LOGIN_PROTECT(UserOperateType.CLOSE_LOGIN_PROTECT_OPTTYPE, "关闭登录保护"),
        BIND_MOBILE(UserOperateType.BIND_MOBILE_OPTTYPE, "绑定手机"),
        UNBIND_MOBILE(UserOperateType.UNBIND_MOBILE_OPTTYPE, "解绑手机"),
        UPDATE_MOBILE(UserOperateType.UPDATE_MOBILE_OPTTYPE, "更新手机号码"),
        BIND_EMAIL(UserOperateType.BIND_EMAIL_OPTTYPE, "绑定邮箱"),
        UNBIND_EMAIL(UserOperateType.UNBIND_EMAIL_OPTTYPE, "解绑邮箱"),
        UPDATE_REALNAME(UserOperateType.UPDATE_REALNAME_OPTTYPE, "修改姓名"),
        UPDATE_USER(UserOperateType.UPDATE_USER_OPTTYPE, "修改用户资料"),
        UPDATE_USER_NICKNAME(UserOperateType.UPDATE_USER_NICKNAME_OPTTYPE, "修改用户昵称"),
        UPDATE_USER_BIRTHDAY(UserOperateType.UPDATE_USER_BIRTHDAY_OPTTYPE, "修改用户生日"),
        UPDATE_USER_SEX(UserOperateType.UPDATE_USER_SEX_OPTTYPE, "修改用户性别"),
        CLEAR_USER_VALIDWATER(UserOperateType.CLEAR_USER_VALIDWATER_OPTTYPE, "用户有效流水清零"),
        CLEAR_USER_RECHARGEWATER(UserOperateType.CLEAR_USER_RECHARGEWATER_OPTTYPE, "会员流水清零"),
        CLEAR_USER_LIMITWATER(UserOperateType.CLEAR_USER_LIMITWATER_OPTTYPE, "平台限制流水清零"),
        UPDATE_USER_HEADURL(UserOperateType.UPDATE_USER_HEADURL_OPTTYPE, "修改用户头像"),
        UPDATE_USER_VIP_OPTTYPE(UserOperateType.USER_VIP_UPDATE_OPTTYPE, "修改用户VIP等级"),

        USER_LOGIN_LOCK(UserOperateType.USER_LOGIN_LOCK_OPTTYPE, "登录锁定"),
        USER_RECHARGE_LOCK(UserOperateType.USER_RECHARGE_LOCK_OPTTYPE, "充值锁定"),
        USER_WITHDRAW_LOCK(UserOperateType.USER_WITHDRAW_LOCK_OPTTYPE, "提现锁定"),
        USER_TRANSFER_LOCK(UserOperateType.USER_TRANSFER_LOCK_OPTTYPE, "转账锁定"),
        USER_LOGIN_UNLOCK(UserOperateType.USER_LOGIN_UNLOCK_OPTTYPE, "解除登录锁定"),
        USER_RECHARGE_UNLOCK(UserOperateType.USER_RECHARGE_UNLOCK_OPTTYPE, "解除充值锁定"),
        USER_WITHDRAW_UNLOCK(UserOperateType.USER_WITHDRAW_UNLOCK_OPTTYPE, "解除提现锁定"),
        USER_TRANSFER_UNLOCK(UserOperateType.USER_TRANSFER_UNLOCK_OPTTYPE, "解除转账锁定"),

        USER_POINTS_UPDATE(UserOperateType.USER_POINTS_UPDATE_OPTTYPE, "用户积分调整"),
        USER_GROUP_UPDATE(UserOperateType.USER_GROUP_UPDATE_OPTTYPE, "用户群组变更"),

        USER_WITHDRAW_MAX_SPLIT_UPDATE(UserOperateType.USER_WITHDRAW_MAX_SPLIT, "大额提现拆单白名单变更"),
        USER_WITHDRAW_MIN_SPLIT_UPDATE(UserOperateType.USER_WITHDRAW_MIN_SPLIT, "小额提现拆单白名单变更"),

        USER_BIG_RECHARGE_UPDATE(UserOperateType.USER_BIG_RECHARGE, "大额充值权限"),
        USER_INVITE_RECHARGE_UPDATE(UserOperateType.USER_INVITE_RECHARGE, "特邀充值权限"),
        USER_SELECT_RECHARGE_UPDATE(UserOperateType.USER_SELECT_RECHARGE, "优选充值权限"),
        UPDATE_USER_VIP_AUTO_OPTTYPE(UserOperateType.USER_VIP_UPDATE_AUTO_OPTTYPE, "修改用户VIP等级(系统)"),
        MANUAL_CARD_BIND(UserOperateType.MANUAL_CARD_BIND_OPTTYPE, "人工绑定银行卡"),
        USER_BONUS_LOCK(UserOperateType.USER_BONUS_LOCK_OPTTYPE, "红利锁定"),
        USER_BONUS_UNLOCK(UserOperateType.USER_BONUS_UNLOCK_OPTTYPE, "解除红利锁定"),

        USER_BANKCARD_LOCK(UserOperateType.USER_BANKCARD_LOCK_OPTTYPE, "銀行卡锁定"),
        USER_BANKCARD_UNLOCK(UserOperateType.USER_BANKCARD_UNLOCK_OPTTYPE, "解除銀行卡锁定"),


        USER_FINANCIAL_TAG(UserOperateType.USER_FINANCIAL_TAG_OPTTYPE, "编辑财务标签"),

        USER_TRANSFER_EXCEPTION_SUCCESS(UserOperateType.USER_TRANSFER_EXCEPTION_SUCCESS_OPTTYPE, "异常转帐提交确认成功"),

        USER_TRANSFER_EXCEPTION_FAIL(UserOperateType.USER_TRANSFER_EXCEPTION_FAIL_OPTTYPE, "异常转帐提交确认失败"),

        USER_ADDRESS_BIND(UserOperateType.USER_ADDRESS_BIND_OPTTYPE, "绑定提现地址"),
        USER_ADDRESS_UNBIND(UserOperateType.USER_ADDRESS_UNBIND_OPTTYPE, "移除提现地址"),
        USER_PLATFORM_WALLET_BIND(UserOperateType.USER_PLATFORM_WALLET_BIND_OPTTYPE, "绑定钱包地址"),
        USER_PLATFORM_WALLET_UNBIND(UserOperateType.USER_PLATFORM_WALLET_UNBIND_OPTTYPE, "移除钱包地址");

        private int optType;
        private String desc;

        UserOperateType(int optType, String desc) {
            this.optType = optType;
            this.desc = desc;
        }

        public int getOptType() {
            return optType;
        }

        public String getDesc() {
            return desc;
        }

        public static final int PROXY_CHANGE_OPTTYPE = 1;
        public static final int CONTACT_CHANGE_OPTTYPE = 2;
        public static final int BANK_RESET_OPTTYPE = 3;
        public static final int FULL_LOCK_OPTTYPE = 4;
        public static final int HALF_LOCK_OPTTYPE = 5;
        public static final int LEVEL_LOCK_OPTTYPE = 5;
        public static final int UN_LOCK_OPTTYPE = 6;
        public static final int COMMISSION_CHANGE_OPTTYPE = 7;
        public static final int CARD_DELETE_OPTTYPE = 8;
        public static final int REMARK_CHANGE_OPTTYPE = 9;
        public static final int CREDIT_CHANGE_OPTTYPE = 10;
        public static final int LIMIT_LOCK_OPTTYPE = 11;
        public static final int LIMIT_UN_LOCK_OPTTYPE = 12;
        public static final int USER_VIP_UPDATE_OPTTYPE = 13;
        public static final int USER_LOGIN_LOCK_OPTTYPE = 14;
        public static final int USER_RECHARGE_LOCK_OPTTYPE = 15;
        public static final int USER_WITHDRAW_LOCK_OPTTYPE = 16;
        public static final int USER_TRANSFER_LOCK_OPTTYPE = 17;
        public static final int USER_LOGIN_UNLOCK_OPTTYPE = 18;
        public static final int USER_RECHARGE_UNLOCK_OPTTYPE = 19;
        public static final int USER_WITHDRAW_UNLOCK_OPTTYPE = 20;
        public static final int USER_TRANSFER_UNLOCK_OPTTYPE = 21;
        public static final int USER_BANKCARD_LOCK_OPTTYPE = 22;
        public static final int USER_BANKCARD_UNLOCK_OPTTYPE = 23;

        public static final int RESET_PASSWORD_OPTTYPE = 101;
        public static final int UPDATE_PASSWORD_OPTTYPE = 102;
        public static final int UPDATE_RECHARGE_OPTTYPE = 103;
        public static final int UPDATE_TRANSFER_OPTTYPE = 104;
        public static final int LOCK_ACCOUNT_OPTTYPE = 105;
        public static final int OPEN_WITHDRAW_OPTTYPE = 106;
        public static final int CLOSE_WITHDRAW_OPTTYPE = 107;
        public static final int BIND_MOBILE_OPTTYPE = 108;
        public static final int UNBIND_MOBILE_OPTTYPE = 109;
        public static final int BIND_EMAIL_OPTTYPE = 110;
        public static final int UNBIND_EMAIL_OPTTYPE = 111;
        public static final int UPDATE_REALNAME_OPTTYPE = 112;
        public static final int CARD_BIND_OPTTYPE = 113;
        public static final int CARD_CHANGE_OPTTYPE = 114;
        public static final int UPDATE_USER_OPTTYPE = 115;
        public static final int UPDATE_USER_NICKNAME_OPTTYPE = 116;
        public static final int UPDATE_USER_BIRTHDAY_OPTTYPE = 117;
        public static final int UPDATE_USER_SEX_OPTTYPE = 118;
        public static final int UPDATE_USER_HEADURL_OPTTYPE = 119;
        public static final int OPEN_LOGIN_PROTECT_OPTTYPE = 120;
        public static final int CLOSE_LOGIN_PROTECT_OPTTYPE = 121;
        public static final int UPDATE_SECURITY_CODE_OPTTYPE = 122;
        public static final int CLEAR_USER_VALIDWATER_OPTTYPE = 123;
        public static final int ACTIVITY_OPTTYPE = 124;
        public static final int CLEAR_USER_RECHARGEWATER_OPTTYPE = 125;
        public static final int UPDATE_MOBILE_OPTTYPE = 126;
        public static final int USER_POINTS_UPDATE_OPTTYPE = 127;
        public static final int CLEAR_USER_LIMITWATER_OPTTYPE = 129;
        public static final int USER_GROUP_UPDATE_OPTTYPE = 130;
        public static final int USER_WITHDRAW_MAX_SPLIT = 131;
        public static final int USER_WITHDRAW_MIN_SPLIT = 132;
        public static final int USER_BIG_RECHARGE = 133;
        public static final int USER_INVITE_RECHARGE = 134;
        public static final int USER_VIP_UPDATE_AUTO_OPTTYPE = 135;
        public static final int USER_SELECT_RECHARGE = 136;
        public static final int USER_BONUS_LOCK_OPTTYPE = 137;
        public static final int USER_BONUS_UNLOCK_OPTTYPE = 138;
        public static final int MANUAL_CARD_BIND_OPTTYPE = 139;
        public static final int USER_FINANCIAL_TAG_OPTTYPE = 140;
        public static final int USER_TRANSFER_EXCEPTION_SUCCESS_OPTTYPE = 141;
        public static final int USER_TRANSFER_EXCEPTION_FAIL_OPTTYPE = 142;
        public static final int USER_ADDRESS_BIND_OPTTYPE = 143;
        public static final int USER_ADDRESS_UNBIND_OPTTYPE = 144;
        public static final int USER_PLATFORM_WALLET_BIND_OPTTYPE = 145;
        public static final int USER_PLATFORM_WALLET_UNBIND_OPTTYPE = 146;

    }
}
