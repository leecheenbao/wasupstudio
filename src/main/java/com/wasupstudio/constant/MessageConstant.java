package com.wasupstudio.constant;


/**
 * 消息
 */
public final class MessageConstant {

    /**
     * 余额宝消息
     */
    public final class Yuebao {
        // 后台关闭余额宝消息
        public final static String CLOSE = "尊敬的用户，感谢您使用【余额宝】。因为运营政策的改变，【余额宝】功能已下线。给您带来不便，尽情谅解！" +
                "如有任何疑问，例如功能上线时间，请咨询客服。";
        // 余额宝发放利息消息
        public final static String INTEREST = "尊敬的用户，感谢您使用【余额宝】。您的昨日收益为 %.4f元（%d倍流水），" +
                "已于 %s 发放到您的中心钱包，请查收。收益明细请前往【余额宝】页面查看。";
        // 余额宝利息调整消息
        public final static String CHANGE_RATE = "尊敬的用户，感谢您使用【余额宝】。因为运营政策的改变，您当前选择的利率 %.2f%%（%d倍流水）" +
                "已被调整。为了保障您的利益，您的余额宝已于 %s 暂时关闭。建议您前往【余额宝】页面重新开通，并选择调整后的利率。给您带来不便，" +
                "尽情谅解！如有任何疑问，请咨询客服。";
        // 用户开启余额宝消息
        public final static String USER_OPEN = "尊敬的用户，您已于 %s 开通了【余额宝】功能。您选择的利率为 %.2f%%（%d倍流水）。祝您使用愉快！";
        // 用户关闭余额宝消息
        public final static String USER_CLOSE = "尊敬的用户，您已于 %s 关闭了【余额宝】功能。您可以随时再次开启此功能。";
        // 用户变更余额宝利率消息
        public final static String USER_CHANGE = "尊敬的用户，您已于 %s 成功调整了【余额宝】的利率。您之前选择的利率为 %.2f%%（%d倍流水），" +
                "新的利率为 %.2f%%（%d倍流水），将于下一个结算周期生效。祝您使用愉快！";
        // 非活跃用户关闭消息
        public final static String INACTIVE_USER_CLOSE = "尊敬的用户，感谢您使用【余额宝】。由于您30天内没有登录过网站或客户端，" +
                "为了您的账户安全，您的余额宝已于 %s 暂时关闭。建议您前往【余额宝】页面重新开通。给您带来不便，" +
                "尽情谅解！如有任何疑问，请咨询客服。";
    }

}
