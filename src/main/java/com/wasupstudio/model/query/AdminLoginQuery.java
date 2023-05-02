package com.wasupstudio.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AdminLoginQuery extends BaseQuery {

    private static final long serialVersionUID = 2694819125048762614L;

    private String device_id;

    @NotBlank(message = "账号不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String device_os;

    private String device_name;

    private String device_type;


}