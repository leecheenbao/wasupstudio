package com.wasupstudio.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements Serializable {
    public enum Role {
        ROLE_USER, ROLE_ADMIN, ROLE_DISABLE
    }
    private Integer id;
    private String email;
    private String pwd;
    private String name;
    private String phone;
    private String birthday;
    private String organization;
    private Integer grade;
    private String registionTime;
    private Role role;
    private String lastIp;
    private Date lastLogin;

    private Integer status;

    private String verificationCode;

    private Integer gender;

    private String category;


}
