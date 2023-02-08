package com.boker.mpesa.dto.MpesaResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionStatusResponse {

    @JsonProperty("ConversationID")
    private String conversationID;

    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;

    @JsonProperty("ResponseDescription")
    private String responseDescription;
}