package com.wasupstudio.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 登入紀錄表
 * @TableName wa_login_records
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wa_login_records")
public class LoginRecordsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login_time", nullable = false)
    private Date loginTime;

    @Column(name = "login_type", nullable = false)
    private Integer loginType;

}

