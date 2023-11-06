package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  private Long productId;

  private String name;

  private BigDecimal price;

  private Integer quantity;
}
