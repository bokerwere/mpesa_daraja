package com.boker.mpesa.dto.MpesaResponse.Asyncronous;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReferenceItem {

    @JsonProperty("Key")
    private String key;
}