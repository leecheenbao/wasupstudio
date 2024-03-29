package com.wasupstudio.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_member")
public class MemberEntity implements Serializable {
    public enum Role {
        ROLE_USER, ROLE_ADMIN, ROLE_DISABLE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pwd", nullable = false)
    private String pwd;
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "birthday", nullable = false)
    private String birthday;
    @Column(name = "organization", nullable = false)
    private String organization;
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "registration_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registionTime;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "lastIp", nullable = false)
    private String lastIp;

    @Column(name = "lastLogin")
    private Date lastLogin;

    @Column(name = "verificationCode")
    private String verificationCode;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "category")
    private String category;


}
