package com.boker.mpesa.dto.MpesaRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@JsonIgnoreProperties(ignoreUnknown = true)
public class B2CPaymentRequest {

    @JsonProperty("QueueTimeOutURL")
    private String queueTimeOutURL;

    @JsonProperty("Remarks")
    private String remarks;

    @JsonProperty("Occassion")
    private String occassion;

    @JsonProperty("Amount")
    private String amount;

    @JsonProperty("InitiatorName")

    private String initiatorName;

    @JsonProperty("SecurityCredential")
    private String securityCredential;

    @JsonProperty("CommandID")
    private String commandID;

    @JsonProperty("PartyA")
    private String partyA;

    @JsonProperty("PartyB")
    private String partyB;

    @JsonProperty("ResultURL")
    private String resultURL;
}