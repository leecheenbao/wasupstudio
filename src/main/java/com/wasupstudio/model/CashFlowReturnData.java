package com.wasupstudio.model;

public class CashFlowReturnData {
	private String Status;
	private String MerchantID;
	private String TradeInfo = "";
	private String TradeSha = "";
	private String Version;
	private Integer EncryptType;
	private String Period = "";

	public String getPeriod() {
		return Period;
	}

	public void setPeriod(String period) {
		Period = period;
	}

	public Integer getEncryptType() {
		return EncryptType;
	}

	public void setEncryptType(Integer encryptType) {
		EncryptType = encryptType;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMerchantID() {
		return MerchantID;
	}

	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}

	public String getTradeInfo() {
		return TradeInfo;
	}

	public void setTradeInfo(String tradeInfo) {
		TradeInfo = tradeInfo;
	}

	public String getTradeSha() {
		return TradeSha;
	}

	public void setTradeSha(String tradeSha) {
		TradeSha = tradeSha;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

}
