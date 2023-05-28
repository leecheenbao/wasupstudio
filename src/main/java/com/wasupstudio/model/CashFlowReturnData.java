package com.wasupstudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CashFlowReturnData {

  private String status;
  private String merchantID;
  private String tradeInfo = "";
  private String tradeSha = "";
  private String version;
  private Integer encryptType;
  private String period = "";
}
