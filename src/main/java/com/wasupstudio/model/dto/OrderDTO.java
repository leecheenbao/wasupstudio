package com.wasupstudio.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  private Long orderId;

  private String userId;

  private String recipient;

  private String phone;

  private String address;

  private Double totalPrice;

  private String status;
}
