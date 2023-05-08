package com.wasupstudio.model;

import lombok.Data;

@Data
public class CashFlowReturnData {

  private String Status;
  private String MerchantID;
  private String TradeInfo = "";
  private String TradeSha = "";
  private String Version;
  private Integer EncryptType;
  private String Period = "";
}
