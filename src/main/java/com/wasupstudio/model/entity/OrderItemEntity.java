package com.wasupstudio.model.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_order_items")
public class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long orderItemId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "quantity")
  private Integer quantity;

}
