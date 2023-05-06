package com.wasupstudio.model.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wa_orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  private String userId;

  private String recipient;

  private String phone;

  private String address;

  private Double totalPrice;

  private String status;

  private Date createTime;

  private Date updateTime;
}
