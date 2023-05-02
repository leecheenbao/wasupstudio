package com.wasupstudio.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 序號資料表
 * @TableName license
 */


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenseVo implements Serializable {

    /**
     * 序號ID
     */
    private Integer id;
    /**
     * 序號
     */
    private String licenseKey;
    /**
     * 啓用狀態
     */
    private Integer activated;
    /**
     * 啓用日期
     */
    private Date activationDate;
    /**
     * 到期日期
     */
    private Date expirationDate;
    /**
     * 客戶姓名
     */
    private String customerName;
    /**
     * 客戶信箱
     */
    private String customerEmail;

}

