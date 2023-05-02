package com.wasupstudio.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpServletRequestUtils {

    public static String getUrl(@NonNull final HttpServletRequest request) {
        String url = request.getHeader("Referer");
        log.info("header 內 Referer: {}", url);
        if (null == url || "".equals(url)) {
            StringBuffer urlTmp = request.getRequestURL();
            log.info("header 內 Referer為空改用 getRequestURL: {}", urlTmp);
            url = urlTmp.delete(urlTmp.length() - request.getRequestURI().length(), urlTmp.length()).append("/").toString();
        }

        log.info("最終url: {}", url);
        return url;
    }

    public static String getRequestIp(@NonNull final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            String localIpv6 = "0:0:0:0:0:0:0:1";
            if ("127.0.0.1".equals(ip) || ip.equals(localIpv6)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // "***.***.***.***".length()
        if (ip != null && ip.length() > 15) {
            // = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getReferer(@NonNull final HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        if (Objects.isNull(referer) || referer.length() == 0 || "unknown".equalsIgnoreCase(referer)) {
            referer = request.getHeader("referer");
        }

        if (Objects.isNull(referer) || referer.length() == 0 || "unknown".equalsIgnoreCase(referer)) {
            referer = "";
        }

        return referer;
    }

    /**
     * transforms a Map into a String
     *
     * @param params
     * @return
     */
    public static String getParamsString(Map<String, String> params) {
        try {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        } catch (UnsupportedEncodingException e) {
            log.error("[HttpServletRequestUtils][getParamsString] 錯誤訊息", e);
            return null;
        }
    }

}
