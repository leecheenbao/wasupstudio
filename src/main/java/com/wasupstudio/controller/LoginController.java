package com.wasupstudio.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
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
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.exception.BussinessException;
import com.wasupstudio.model.Result;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.LoginDTO;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.AesUtils;
import com.wasupstudio.util.HttpServletRequestUtils;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.MailUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "權限相關 API")
@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {
	@Value("${google.CLIENT_ID}")
	private  String CLIENT_ID;
	@Value("${google.CLIENT_SECRET}")
	private String CLIENT_SECRET;
	@Value("${google.REDIRECT_URI}")
	private String REDIRECT_URI;
	@Value("${base.url}")
	private String BASE_URL;

	private final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/userinfo.profile"
	);
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "Google註冊", notes = "如果提供了code，則會使用Google API進行註冊，否則會重定向到Google的OAuth授權頁面")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "授權碼", required = false, dataType = "String", paramType = "query")
	})
	@GetMapping("/google-signup")
	public Result google_signup(@RequestParam(value = "code" ,required = false) String code,
								@RequestBody(required = false) MemberDTO memberDTO,
								HttpSession session) throws Exception {
		session.setAttribute("member", memberDTO);
		if (code != null) {
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
					.setRedirectUri(BASE_URL + REDIRECT_URI)
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

		return getGoogleOAuth(BASE_URL + REDIRECT_URI);
	}

	@ApiOperation(value = "Google登錄", notes = "如果提供了code，則會使用Google API進行登錄，否則會重定向到Google的OAuth授權頁面")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "授權碼", required = false, dataType = "String", paramType = "query")
	})
	@GetMapping("/google-login")
	public Result googleLogin(@RequestParam(value = "code" ,required = false) String code, HttpServletRequest request) throws Exception {

		if (code != null) {
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
					.setRedirectUri(BASE_URL + REDIRECT_URI)
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

			String jwtToken = memberService.login(userInfo.getEmail());
			if (jwtToken == null) {
				return ResultGenerator.genSuccessResult(
						ResultCode.USER_LOGIN_FAILED.getMessage() + ":" +
								ResultCode.USER_NAME_NOT_EXIST.getMessage());
			}
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setToken(jwtToken);
			loginDTO.setMemMail(userInfo.getEmail());
			loginDTO.setToken(request.getRemoteAddr());
			return ResultGenerator.genSuccessResult(loginDTO);
		}

		return getGoogleOAuth(BASE_URL + REDIRECT_URI);
	}

	private Result getGoogleOAuth(String REDIRECT_URI) throws GeneralSecurityException, IOException {
		List<String> scopes = Arrays.asList("openid", "email", "profile");
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(),
				CLIENT_ID,
				CLIENT_SECRET,
				scopes)
				.build();
		AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
		return ResultGenerator.genSuccessResult(authorizationUrl.build());
	}

	@GetMapping(value = "/oauth2callback")
	public Result handleOAuth2Callback(@RequestParam(value = "code") String code) throws IOException {
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
				.setRedirectUri(BASE_URL + REDIRECT_URI)
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
	/**
	 * 用戶登入
	 *
	 * @param adminLoginQuery 用戶登入條件
	 * @return 登入結果
	 */
	@ApiOperation(value = "用戶登入", notes = "使用用戶信箱和密碼進行登入")
	@ApiResponses({
			@ApiResponse(code = 200, message = "登入成功", response = LoginDTO.class),
			@ApiResponse(code = 400, message = "請求參數不正確", response = Result.class),
			@ApiResponse(code = 401, message = "認證失敗", response = Result.class),
			@ApiResponse(code = 500, message = "伺服器內部錯誤", response = Result.class)
	})
	@PostMapping(value = "/login", produces = "application/json;charset=utf-8")
	public Result login(@RequestBody @Valid AdminLoginQuery adminLoginQuery) {
		String loginIp = getIP();

		AdminLoginLogQuery adminLoginLogQuery = new AdminLoginLogQuery();
		adminLoginLogQuery.setUserAgent(getHeader("User-Agent"));
		adminLoginLogQuery.setIp(loginIp);
		adminLoginLogQuery.setUrl(getUrl());
		adminLoginQuery.setDevice_id(getHeader("device_id"));
		adminLoginQuery.setDevice_name(getHeader("device_name"));
		adminLoginQuery.setDevice_type(getHeader("device_type"));
		adminLoginQuery.setDevice_os(getHeader("device_os"));

		String jwtToken = memberService.login(adminLoginQuery, adminLoginLogQuery);
		if (jwtToken.isEmpty()){
			log.info("[會員登入失敗 登入資訊 adminLoginQuery:{}]", adminLoginQuery);
			return ResultGenerator.genFailResult(ResultCode.USER_LOGIN_FAILED.getMessage());
		}
		Authentication authentication = JwtUtils.getAuthentication(jwtToken);
		MemberEntity memberEntity = memberService.getAdminByEmail(adminLoginQuery.getEmail());
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setToken(jwtToken);
		loginDTO.setMemMail(adminLoginQuery.getEmail());
		loginDTO.setRole(authentication.getAuthorities());
		loginDTO.setId(memberEntity.getId());
		return ResultGenerator.genSuccessResult(loginDTO);

	}

	/**
	 * 用戶註冊
	 *
	 * @return 登入結果
	 */
	@ApiOperation(value = "會員註冊", notes = "使用會員資料進行註冊，回傳成功或失敗訊息")
	@PostMapping("/signup")
	public Result signup(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) throws Exception {

		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldErrors().stream()
					.map(error -> error.getField() + " " + error.getDefaultMessage())
					.collect(Collectors.joining(", "));
			return ResultGenerator.genFailResult(errorMsg);
		}


		MemberEntity memberEntity = memberService.getAdminByEmail(memberDTO.getEmail());
		if (memberEntity == null) {
			String mail = memberDTO.getEmail();
			String verificationCode = AesUtils.encrypt(mail);
			MailUtil.sendMail(ProjectConstant.MailType.SIGNUP, verificationCode, mail);
			memberService.save(memberDTO);
			return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
		} else {
			return ResultGenerator.genFailResult(ResultCode.USER_NAME_EXIST.getMessage());
		}

	}


	/**
	 * 發送驗證信
	 *
	 * @return 發送結果
	 */
	@ApiOperation(value = "發送驗證信", notes = "註冊之後發送驗證信，可用於再次發送")
	@PostMapping("/mail")
	public Result sendMail(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) throws Exception {

		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldErrors().stream()
					.map(error -> error.getField() + " " + error.getDefaultMessage())
					.collect(Collectors.joining(", "));
			return ResultGenerator.genFailResult(errorMsg);
		}

		/* 註冊成功發送驗證信 */
		MemberEntity memberEntity = memberService.getAdminByEmail(memberDTO.getEmail());
		if (memberEntity != null) {
			String mail = memberEntity.getEmail();
			String verificationCode = AesUtils.encrypt(mail);

			MailUtil.sendMail(ProjectConstant.MailType.SIGNUP, verificationCode, mail);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 確認驗證信
	 *
	 * @return 發送結果
	 */
	@ApiOperation(value = "確認驗證信", notes = "確認驗證信，更改啟用狀態")
	@GetMapping("/verify/{verificationCode}")
	public Result verifyAccount(@PathVariable String verificationCode) {
		// 在這裡根據驗證碼進行驗證邏輯的實現
		// 從數據庫中查詢相應的帳號，檢查驗證碼是否有效
		MemberDTO memberDTO = memberService.findByVerificationCode(verificationCode);
		// 如果驗證通過，將帳號的啟用狀態設置為已啟用
		if (memberDTO.getStatus().equals(ProjectConstant.SystemAdminStatus.NOT_ENABLED)){
			memberDTO.setStatus(ProjectConstant.SystemAdminStatus.DISABLE);
			memberService.update(memberDTO);
			return ResultGenerator.genSuccessResult(ResultCode.VALIDATAE_CODE_SUCCESS.getMessage());
		} else if (memberDTO.getStatus().equals(ProjectConstant.SystemAdminStatus.NORMAL)){
			return ResultGenerator.genFailResult(ResultCode.VALIDATAE_CODE_ERROR.getMessage());
		} else if (memberDTO.getStatus().equals(ProjectConstant.SystemAdminStatus.DISABLE)) {
			return ResultGenerator.genFailResult(ResultCode.USER_LOCK_ERROR.getMessage());
		}
		// 返回相應的成功或失敗消息
		return ResultGenerator.genFailResult(ResultCode.USER_NAME_NOT_EXIST.getMessage());
	}

	/**
	 * 獲取當前IP
	 *
	 * @return
	 */
	public String getIP() {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return HttpServletRequestUtils.getRequestIp(httpServletRequest);
	}

	/**
	 * 獲取header信息字段
	 *
	 * @param filed
	 * @return
	 */
	public String getHeader(String filed) {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return httpServletRequest.getHeader(filed);
	}

	/**
	 * 取得當前URL
	 *
	 * @return
	 */
	public String getUrl() {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String url = httpServletRequest.getHeader("Referer");
		if (null == url || "".equals(url)) {
			StringBuffer urlTmp = httpServletRequest.getRequestURL();
			url = urlTmp.delete(urlTmp.length() - httpServletRequest.getRequestURI().length(), urlTmp.length()).append("/").toString();
		}

		return url;
	}
}

