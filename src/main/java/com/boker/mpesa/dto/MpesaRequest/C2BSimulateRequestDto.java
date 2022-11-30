package com.boker.mpesa.dto.MpesaRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class C2BSimulateRequestDto{

	@JsonProperty("ShortCode")
	private String shortCode;

	@JsonProperty("Msisdn")
	private String msisdn;

	@JsonProperty("BillRefNumber")
	private String billRefNumber;

	@JsonProperty("Amount")
	private int amount;

	@JsonProperty("CommandID")
	private String commandID;
}