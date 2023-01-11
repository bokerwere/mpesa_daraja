package com.boker.mpesa.dto.MpesaRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StkPushResponse{

	@JsonProperty("MerchantRequestID")
	private String merchantRequestID;

	@JsonProperty("ResponseCode")
	private String responseCode;

	@JsonProperty("CustomerMessage")
	private String customerMessage;

	@JsonProperty("CheckoutRequestID")
	private String checkoutRequestID;

	@JsonProperty("ResponseDescription")
	private String responseDescription;
}