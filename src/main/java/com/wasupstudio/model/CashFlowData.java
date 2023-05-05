package com.wasupstudio.model;

public class CashFlowData {

	private String tradeInfo;
	private String tradeSha;
	private String postData_;
	private String merchantID_;

	public String getTradeInfo() {
		return tradeInfo;
	}

	public void setTradeInfo(String tradeInfo) {
		this.tradeInfo = tradeInfo;
	}

	public String getTradeSha() {
		return tradeSha;
	}

	public String getPostData_() {
		return postData_;
	}

	public void setPostData_(String postData_) {
		this.postData_ = postData_;
	}

	public String getMerchantID_() {
		return merchantID_;
	}

	public void setMerchantID_(String merchantID_) {
		this.merchantID_ = merchantID_;
	}

	public void setTradeSha(String tradeSha) {
		this.tradeSha = tradeSha;
	}
}
