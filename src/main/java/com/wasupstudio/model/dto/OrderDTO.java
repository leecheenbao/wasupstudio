package com.wasupstudio.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  List<OrderItemDTO> products;

  private String recipient;

  private String phone;

  private String address;

  private String email;
  @Data
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class OrderItemDTO {

    private String productId;

    private Integer quantity;

  }

}
