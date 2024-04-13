package com.wasupstudio.interceptor;

import com.google.gson.Gson;
import com.wasupstudio.annotation.SkipAuth;
import com.wasupstudio.constant.BaseRedisKeyConstant;
import com.wasupstudio.constant.UserRoleConstants;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.BusinessException;
import com.wasupstudio.exception.CommonError;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@Slf4j
@Component
public class LicenseInterceptor extends HandlerInterceptorAdapter {

    @Value("${spring.profiles.active}")
    private String env;

    private LicenseService licenseService;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * pre handle
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        MemberEntity member = new MemberEntity();
        if (auth != null){
            String token = auth.split(" ")[1];
            member = JwtUtils.getMember(JwtUtils.getAuthentication(token));
        } else {
            throw new BusinessException(CommonError.PERMISSION_DENIED);
        }

        Integer memberId = member.getId();
        boolean isAccess = true;
        ResultCode resultCode = null;
        String requestURI = request.getRequestURI();
        // 檢查 license 是否有效於登入時儲存於 redis 呼叫 API 時直接取出 boolean
        String license = redisUtil.getKey(String.format(BaseRedisKeyConstant.LOGIN_CHECKED, memberId.toString()));
        boolean checkLicense = Boolean.parseBoolean(license);

        //此API是否跳過認證
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SkipAuth skipAuth = handlerMethod.getMethodAnnotation(SkipAuth.class);
        if (skipAuth != null || member.getRole().toString().equals(UserRoleConstants.ROLE_ADMIN) || checkLicense) {
            // 過濾
        } else if (null == license || license.equals("false")) {
            resultCode = ResultCode.NOT_ACCESS;
            isAccess = false;
        }

        log.info("LicenseInterceptor preHandle method, env:{}, license:{}, requestURI:{}, tokenAccess:{}, resultCode:{}", env, license, requestURI, isAccess, resultCode);
        if (!isAccess) {
            Result result = new Result();
            result.setCode(resultCode);
            responseResult(response, result);
        }
        return isAccess;
    }

    private boolean validateLicense(HttpServletRequest request) {
        String requestSign = request.getHeader("license");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        LinkedHashMap<String, String> keys = new LinkedHashMap<>();

        keys.put("license", request.getHeader("license"));


        StringBuffer sb = new StringBuffer();
        for (String key : keys.keySet()) {
            sb.append(key).append("=").append(keys.get(key)).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'

        String secret = "global";//密钥，自己修改

        String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5
        return StringUtils.equals(sign, requestSign);//比较
    }

    /**
     * response result
     */
    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }


}
