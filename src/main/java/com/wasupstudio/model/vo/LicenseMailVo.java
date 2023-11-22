package com.wasupstudio.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LicenseMailVo {
    private Long orderId;
    private BigDecimal amount;
    private Integer count;
    private String name;
    private String email;
    private String license;

}
