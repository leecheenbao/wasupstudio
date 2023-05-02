package com.wasupstudio.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.wasupstudio.exception.Result;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.BasePageInfo;
import com.wasupstudio.util.GoogleSignIn;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Value("${google.CLIENT_ID}")
    private  String CLIENT_ID;
    @Value("${google.CLIENT_SECRET}")
    private String CLIENT_SECRET;
    @Value("${google.REDIRECT_URI}")
    private String REDIRECT_URI;
    private final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"
    );
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    @Autowired
    private MemberService memberService;

    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = memberService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        MemberEntity licenseEntity = memberService.findOne(id);
        return ResultGenerator.genSuccessResult(licenseEntity);
    }
    @PostMapping("/google-signup")
    public Result google_signup() throws IOException {
        String url = GoogleSignIn.getAuthorizationUrl();
        return ResultGenerator.genSuccessResult(url);
    }
    @PostMapping("/signup")
    public Result signup(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        memberService.save(memberDTO);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping(value = "/oauth2callback")
    public Result handleOAuth2Callback(@RequestParam(value = "code") String code, HttpSession session) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                CLIENT_ID,
                CLIENT_SECRET,
                SCOPES)
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(REDIRECT_URI)
                .setGrantType("authorization_code")
                .execute();

        Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setTokenServerUrl(new GenericUrl("https://oauth2.googleapis.com/token"))
                .setClientAuthentication(new BasicAuthentication(CLIENT_ID, CLIENT_SECRET))
                .build()
                .setAccessToken(tokenResponse.getAccessToken())
                .setRefreshToken(tokenResponse.getRefreshToken());

        // 取得使用者資訊
        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
        Userinfo userInfo = oauth2.userinfo().get().execute();

        return ResultGenerator.genSuccessResult(userInfo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        memberService.save(memberDTO);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }

        memberDTO.setId(id);
        memberService.update(memberDTO);
        return ResultGenerator.genSuccessResult();
    }
}
