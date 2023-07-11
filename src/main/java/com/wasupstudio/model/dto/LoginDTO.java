package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	@Email(message = "帳號必須是Email 格式")
	@NotBlank(message = "帳號不可為空")
	private String memMail;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w]{6,16}$", message = "密碼必須為長度6~16位碼大小寫英文加數字")
	@NotBlank(message = "密碼不可為空")
	private String memPwd;

	private String token;

	private Collection<? extends GrantedAuthority> role;
}