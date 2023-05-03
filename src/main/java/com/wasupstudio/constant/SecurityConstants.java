package com.wasupstudio.constant;

/**
 * SecurityConstants
 *
 * @author Paul
 **/
public final class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static constant class");
    }

    /**
     * 用於登錄的 url
     */
    public static final String AUTH_LOGIN_URL = "/auth/login";

    /**
     * JWT簽名密鑰，這裡使用 HS512 算法的簽名密鑰
     * <p>
     * 注意：最好使用環境變量或 .properties 文件的方式將密鑰傳入程序
     * 密鑰生成地址：http://www.allkeysgenerator.com/
     */
    public static final String JWT_SECRET_KEY = "p2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdS";


    /**
     * 一般是在請求頭裡加入 Authorization，並加上 Bearer 標注
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Authorization 請求頭
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * token 類型
     */
    public static final String TOKEN_TYPE = "JWT";

    public static final String TOKEN_ROLE_CLAIM = "role";
    public static final String TOKEN_ISSUER = "security";
    public static final String TOKEN_AUDIENCE = "security-all";

    /**
     * 當 Remember 是 false 時，token 有效時間 2 小時
     */
    public static final long EXPIRATION_TIME = 60 * 60 * 2L;

    /**
     * 當 Remember 是 true 時，token 有效時間 7 天
     */
    public static final long EXPIRATION_REMEMBER_TIME = 60 * 60 * 24 * 7L;


}

