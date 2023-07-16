package com.wasupstudio.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 序號資料表
 * @TableName license
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_license")
public class LicenseEntity implements Serializable {

    /**
     * 序號ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 序號
     */
    @Column(name = "license_key")
    private String licenseKey;
    /**
     * 啓用狀態
     */
    @Column(name = "activated")
    private Integer activated;
    /**
     * 啓用日期
     */
    @Column(name = "activation_date")
    private Date activationDate;
    /**
     * 到期日期
     */
    @Column(name = "expiration_date")
    private Date expirationDate;
    /**
     * 客戶姓名
     */
    @Column(name = "customer_name")
    private String customerName;
    /**
     * 客戶信箱
     */
    @Column(name = "customer_email")
    private String customerEmail;

    /**
     * 生成方式
     */
    @Column(name = "generage")
    private String generate;



}

