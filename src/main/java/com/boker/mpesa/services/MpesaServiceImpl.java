package com.boker.mpesa.services;

import com.boker.mpesa.MpesaConfigs.MpesaConfiguration;
import com.boker.mpesa.Utils.Constants;
import com.boker.mpesa.Utils.HelperUtility;
import com.boker.mpesa.dto.MpesaRequest.*;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.boker.mpesa.dto.MpesaResponse.StkPushSyncResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class MpesaServiceImpl implements MpesaService {

    private final MpesaConfiguration mpesaConfiguration;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper mapper;


    @Override
    public AccessTokenResponse getToken() throws JsonProcessingException {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBasicAuth(mpesaConfiguration.getMpesaUsername(), mpesaConfiguration.getMpesaPassword());
            String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
            HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
            int statusCode = response.getStatusCodeValue();

            if (statusCode == 200 || statusCode == 201) {
                JsonNode jsonBody = response.getBody();
                log.info("token {}", jsonBody);
                return mapper.treeToValue(jsonBody, AccessTokenResponse.class);

            }
        } catch (Exception e) {
            log.error("[");
            e.getMessage();
        }

        return new AccessTokenResponse();
    }

    @Override
    public void generateToken() {
        String encodedCedentials = HelperUtility.toBase64(String.format("%s:%s", mpesaConfiguration.getConsumerKey(), mpesaConfiguration.getConsumerSecret()));
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.setBasicAuth(encodedCedentials);
        httpHeaders.setBasicAuth("Bearer"+"R3NaNjV1VGhHNVBOQldUSmdKTXJkUzRBVFRYb0ZleTg6QzBMeVdJT2hBa3ZHdkh2Tw==");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("grant_type","client_credentials");
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
        UriComponents bulder = UriComponentsBuilder.fromUriString(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
        System.out.println(response.getStatusCodeValue());
        System.out.println("==========" + response.getBody());

    }

    @Override
    public C2BSimulateResponseDto c2bSimulate(C2BSimulateRequestDto c2BSimulateRequestDto) throws JsonProcessingException {
        AccessTokenResponse tokenResponse = getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println("bearer:===" + tokenResponse.getAccessToken());
        //httpHeaders.setContentType(MediaType.parseMediaType("Application/Json"));
        httpHeaders.setBearerAuth(tokenResponse.getAccessToken());
        C2BSimulateRequestDto request = new C2BSimulateRequestDto();
        request.setAmount(c2BSimulateRequestDto.getAmount());
        request.setShortCode(c2BSimulateRequestDto.getShortCode());
        request.setMsisdn(c2BSimulateRequestDto.getMsisdn());
        request.setBillRefNumber(c2BSimulateRequestDto.getBillRefNumber());
        request.setCommandID(c2BSimulateRequestDto.getCommandID());
        HttpEntity<C2BSimulateRequestDto> httpEntity = new HttpEntity<>(request, httpHeaders);
        String url = "https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate";
        UriComponents builder = UriComponentsBuilder.fromUriString(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(builder.toString(), HttpMethod.POST, httpEntity, JsonNode.class);
        log.info("statusCode {}", response.getStatusCodeValue());
        log.info("status {}", response.getStatusCode());
        int statusCode = response.getStatusCodeValue();
        try {
            if (statusCode == 200 || statusCode == 201) {
                JsonNode responseBody = response.getBody();
                return mapper.treeToValue(responseBody, C2BSimulateResponseDto.class);
            }
        } catch (RestClientResponseException exception) {
            String errorMessage = exception.getMessage();
            exception.getRawStatusCode();
            log.error("errorMessage{}", errorMessage);
            JsonNode jsonNode = mapper.readTree(exception.getMessage());
            log.error("errorBody{}", jsonNode);
        }

        return null;

    }

    @Override
    public JsonNode urlRegistration(UrlRegisterRequest urlRegisterRequest) throws JsonProcessingException {

        AccessTokenResponse tokenResponse = getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(tokenResponse.getAccessToken());
        UrlRegisterRequest urlRegisterRequest1 = new UrlRegisterRequest();
        //requests
        urlRegisterRequest1.setConfirmationURL(mpesaConfiguration.getConfirmationURL());
        urlRegisterRequest1.setValidationURL(mpesaConfiguration.getValidationURL());
        urlRegisterRequest1.setShortCode(urlRegisterRequest.getShortCode());
        urlRegisterRequest1.setResponseType(urlRegisterRequest.getShortCode());
        HttpEntity<UrlRegisterRequest> httpEntity = new HttpEntity<>(urlRegisterRequest1, httpHeaders);
        String url = String.format("%s/mpesa/c2b/v1/registerurl", mpesaConfiguration.getMpesaBaseUrl());
        //"https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.POST, httpEntity, JsonNode.class);
        JsonNode responseBody = response.getBody();
        log.info(" body {}", responseBody);
        // return mapper.treeToValue(responseBody, UrlRegisterResponse.class);
        return responseBody;
    }

    @SneakyThrows
    @Override
    public JsonNode B2CPayment(B2CPaymentRequest b2CPaymentRequest) {
        AccessTokenResponse token = getToken();
        System.out.println("=============" + token.getAccessToken());
        //B2CRequests

        B2CPaymentRequest request = new B2CPaymentRequest();
        request.setAmount(b2CPaymentRequest.getAmount());
        request.setOccassion(b2CPaymentRequest.getOccassion());
        request.setRemarks(b2CPaymentRequest.getRemarks());
        request.setInitiatorName(b2CPaymentRequest.getInitiatorName());
        request.setPartyA(b2CPaymentRequest.getPartyA());
        request.setPartyB(b2CPaymentRequest.getPartyB());
        request.setSecurityCredential(b2CPaymentRequest.getSecurityCredential());
        request.setQueueTimeOutURL(b2CPaymentRequest.getQueueTimeOutURL());
        request.setCommandID(b2CPaymentRequest.getCommandID());
        request.setResultURL(b2CPaymentRequest.getResultURL());
        log.info("body {}", mapper.writeValueAsString(b2CPaymentRequest));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.getAccessToken());
        //httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<B2CPaymentRequest> entity = new HttpEntity<>(request, httpHeaders);
        log.info("body {}", entity);
        String url = "https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest";
        UriComponents urlBuilder = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
        int statusCode = response.getStatusCodeValue();
        log.info("statusCode:{}", statusCode);
        JsonNode jsonNode = response.getBody();
        return jsonNode;
    }

    @SneakyThrows
    @Override
    public Optional<StkPushSyncResponse> StkPushTransaction(InternalStkPushRequest internalStkPushRequest) {
        try {
            AccessTokenResponse token = getToken();
            ExternalStkPushRequest externalStkPushRequest = new ExternalStkPushRequest();
            String transactionTimestamp = HelperUtility.generateTimestamp();
            String stkPushPassword = HelperUtility.getStkPushPassword(mpesaConfiguration.getStkPushShortcode(), mpesaConfiguration.getStkPassKey(), transactionTimestamp);

            externalStkPushRequest.setBusinessShortCode(mpesaConfiguration.getStkPushShortcode());
            externalStkPushRequest.setPassword(stkPushPassword);
            externalStkPushRequest.setTimestamp(transactionTimestamp);
            externalStkPushRequest.setAmount(internalStkPushRequest.getAmount());
            externalStkPushRequest.setCallBackURL(mpesaConfiguration.getStkPushRequestCallbackUrl());
            externalStkPushRequest.setTransactionType(Constants.CUSTOMER_PAYBILL_ONLINE);
            externalStkPushRequest.setPartyB(mpesaConfiguration.getStkPushShortcode());
            externalStkPushRequest.setPartyA(internalStkPushRequest.getPhoneNumber());
            externalStkPushRequest.setPhoneNumber(internalStkPushRequest.getPhoneNumber());
            externalStkPushRequest.setAccountReference(HelperUtility.getTransactionUniqueNumber());
            externalStkPushRequest.setTransactionDesc(String.format("%s Transaction", internalStkPushRequest.getPhoneNumber()));
            log.info("payload{}", mapper.writeValueAsString(externalStkPushRequest));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token.getAccessToken());
            HttpEntity<ExternalStkPushRequest> entity = new HttpEntity<>(externalStkPushRequest, headers);

            String stkPushRequestUrl = mpesaConfiguration.getStkPushUrl();
            try {
                ResponseEntity<JsonNode> response = restTemplate.exchange(stkPushRequestUrl, HttpMethod.POST, entity, JsonNode.class);
                int statusCodeValue = response.getStatusCodeValue();
                HttpStatus httpStatus = response.getStatusCode();
                log.info("httpStatus:{}", httpStatus);
                log.info("statusValueCode:{}", statusCodeValue);
                if (statusCodeValue == 200 || statusCodeValue == 201 || statusCodeValue == 202) {
                    JsonNode jsonNode = response.getBody();
                    log.info("responseBody{}", mapper.writeValueAsString(jsonNode));
                    StkPushSyncResponse stkPushSyncResponse = mapper.treeToValue(jsonNode, StkPushSyncResponse.class);
                    log.info("stkPushSycResponse{}", mapper.writeValueAsString(stkPushSyncResponse));
                    String merchantId = jsonNode.get("MerchantRequestID").asText();
                    log.info("merchantId{}", merchantId);
                    return Optional.of(stkPushSyncResponse);
                }

            } catch (RestClientResponseException e) {
                e.getMessage();
                log.error("error{}", e.getMessage());
                log.error("errorBody{}", mapper.writeValueAsString(e.getResponseBodyAsString()));
                e.getRawStatusCode();
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

