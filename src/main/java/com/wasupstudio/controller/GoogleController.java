package com.wasupstudio.controller;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Controller
public class GoogleController {

    @Value("${google.CLIENT_SECRET}")
    private  String CLIENT_ID;
    @Value("${google.CLIENT_ID}")
    private String CLIENT_SECRET;
    @Value("${google.REDIRECT_URI}")
    private String REDIRECT_URI;
    private final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"
    );
    @GetMapping("/google/login")
    public String googleLogin(HttpServletRequest request) throws Exception {
        String clientId = "YOUR_CLIENT_ID";
        String clientSecret = "YOUR_CLIENT_SECRET";
        List<String> scopes = Arrays.asList("openid", "email", "profile");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientId,
                clientSecret,
                scopes)
                .build();
        String redirectUri = "http://localhost:8080/google/callback";
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri);
        return "redirect:" + authorizationUrl.build();
    }

    @GetMapping("/google/callback")
    public String googleCallback(@RequestParam("code") String code, HttpServletRequest request) throws Exception {
        String clientId = "YOUR_CLIENT_ID";
        String clientSecret = "YOUR_CLIENT_SECRET";
        List<String> scopes = Arrays.asList("openid", "email", "profile");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientId,
                clientSecret,
                scopes)
                .build();
        String redirectUri = "http://localhost:8080/google/callback";
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        Credential credential = flow.createAndStoreCredential(response, null);
        // 使用credential進行後續操作，例如獲取用戶信息等等
        return "redirect:/home";
    }

    @RequestMapping("/wasupstudio/api/member/code")
    public void googleCallback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        String code = request.getParameter("code");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                SCOPES)
                .build();


        if (code != null) {
            try {
                GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri("http://localhost:8080/wasupstudio/api/member/code").execute();
                String accessToken = tokenResponse.getAccessToken();
                String refreshToken = tokenResponse.getRefreshToken();
                Long expiresInSeconds = tokenResponse.getExpiresInSeconds();

                HttpRequestInitializer requestInitializer = new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) throws IOException {
                        request.setReadTimeout(300 * 60000); //设置读取超时时间
                        request.setConnectTimeout(300 * 60000); //设置连接超时时间
                    }
                };

                // 根据 accessToken 获取用户信息
                GoogleCredential credential = new GoogleCredential.Builder()
                        .setTransport(httpTransport)
                        .setJsonFactory(jsonFactory)
//                        .setClientSecrets(clientSecrets)
                        .build()
                        .setAccessToken(accessToken)
                        .setRefreshToken(refreshToken);
//                Plus plus = new Plus.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
//                Person profile = plus.people().get("me").execute();

                // 处理用户信息，例如保存到数据库等
                // ...

                response.sendRedirect("http://localhost:8080/");
            } catch (TokenResponseException e) {
                if (e.getDetails() != null) {
                    System.err.println("Error: " + e.getDetails().getError());
                    if (e.getDetails().getErrorDescription() != null) {
                        System.err.println(e.getDetails().getErrorDescription());
                    }
                    if (e.getDetails().getErrorUri() != null) {
                        System.err.println(e.getDetails().getErrorUri());
                    }
                } else {
                    System.err.println(e.getMessage());
                }
            }
        } else {
            // 用户拒绝授权，处理逻辑
            // ...
        }
    }

}

