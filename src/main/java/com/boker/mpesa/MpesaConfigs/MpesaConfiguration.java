package com.boker.mpesa.MpesaConfigs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Data
@Configuration
public class MpesaConfiguration {
    @Value("${mpesa.username}")
    private  String mpesaUsername;
    @Value("${mpesa.password}")
    private  String mpesaPassword;
    @Value("${mpesa.validationURL}")
    private String validationURL;
    @Value("${mpesa.confirmationURL}")
    private String confirmationURL;
    @Value("${mpesa.consumerKey}")
    private String consumerKey;
    @Value("${mpesa.consumerSecret}")
    private String consumerSecret;
    @Value("${mpesa.url}")
    private String mpesaBaseUrl;
    @Value("${mpesa.stkPushUrl}")
    private String stkPushUrl;
    @Value("${mpesa.stkPassKey}")
    private String stkPassKey;
    @Value("${mpesa.stkPushShortcode}")
    private String stkPushShortcode;
    @Value("${mpesa.stkPushCallBackUrl}")
    private String stkPushRequestCallbackUrl;




    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    ObjectMapper objectMapper(){
    return new ObjectMapper() ;
    }
}
