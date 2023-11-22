package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 訂單明細
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery implements Serializable {
    private Long orderId;
    private Integer userId;
    private String recipient;
    private String phone;
    private String address;
    private BigDecimal totalPrice;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Integer quantity;




}

