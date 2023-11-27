package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchDTO {
    private String orderId;
    private String recipient;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer status;
    private String email;
    private String phone;
}
