package com.wasupstudio.model;

import lombok.Data;

@Data
public class CashFlowReturnData {

  private String Status;
  private String Message;
  private Result Result;

  @Data
  public static class Result{
    private String MerchantID;
    private Integer Amt;
    private String TradeNo;
    private String MerchantOrderNo;
    private String PaymentType;
    private String RespondType;
    private String PayTime;
    private String IP;
    private String EscrowBank;
  }
}
