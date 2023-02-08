package com.boker.mpesa.Entities;

import lombok.Data;

@Data
public class Merchant {
    private Long merchartId;
    private String merchantName;
    private String initiateName;
    private String consumerKey;
    private String consumerSecret;
    //LIPA NA M-PESA shortcode
    private String stkPushShortcode;
    //LIPA NA M-PESA  passkey
    private String stkPassKey;


}
