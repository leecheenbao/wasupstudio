package com.wasupstudio.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wasupstudio.exception.Result;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.LoginDTO;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.BasePageInfo;
import com.wasupstudio.util.HttpServletRequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "LoginController")
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private MemberService memberService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 會員登入
	 * 
	 * @param adminLoginQuery
	 * @return JSONObject
	 */
	@ApiOperation(value = "账号登录", notes = "账号登录接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string"),
			@ApiImplicitParam(name = "imgCode", value = "图片验证码", dataType = "string"),
			@ApiImplicitParam(name = "uuid", value = "图片验证码唯一ID", required = true, dataType = "string")
	})
	@PostMapping(value = "/login", produces = "application/json;charset=utf-8")
	public Result login(@Valid AdminLoginQuery adminLoginQuery) {
		String loginIp = getIP();

		AdminLoginLogQuery adminLoginLogQuery = new AdminLoginLogQuery();
		adminLoginLogQuery.setUserAgent(getHeader("User-Agent"));
		adminLoginLogQuery.setIp(loginIp);
		adminLoginLogQuery.setUrl(getUrl());
		adminLoginQuery.setDevice_id(getHeader("device_id"));
		adminLoginQuery.setDevice_name(getHeader("device_name"));
		adminLoginQuery.setDevice_type(getHeader("device_type"));
		adminLoginQuery.setDevice_os(getHeader("device_os"));

		JsonObject jsonObject = memberService.login(adminLoginQuery, adminLoginLogQuery);

		return ResultGenerator.genSuccessResult(jsonObject);

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
	 * 获取header头信息_**字段
	 *
	 * @param filed
	 * @return
	 */
	public String getHeader(String filed) {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return httpServletRequest.getHeader(filed);
	}

	/**
	 * 取当前URL
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
