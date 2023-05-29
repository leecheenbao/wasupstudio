package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CashFlowReturnDataDTO {

  private String status;
  private String merchantID;
  private String tradeInfo = "";
  private String tradeSha = "";
  private String version;
  private Integer encryptType;
  private String period = "";
}
