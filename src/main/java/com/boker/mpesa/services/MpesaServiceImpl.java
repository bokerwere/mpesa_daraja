package com.boker.mpesa.services;

import com.boker.mpesa.MpesaConfigs.MpesaConfiguration;
import com.boker.mpesa.dto.MpesaRequest.C2BSimulateRequestDto;
import com.boker.mpesa.dto.MpesaRequest.UrlRegisterRequest;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.boker.mpesa.dto.MpesaResponse.UrlRegisterResponse;
import com.boker.mpesa.Utils.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@Slf4j
@RequiredArgsConstructor
public class MpesaServiceImpl implements MpesaService {

    private final MpesaConfiguration mpesaConfiguration;

    private final RestTemplate restTemplate;

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
            e.getMessage();
        }

        return new AccessTokenResponse();
    }

    @Override
    public AccessTokenResponse generateToken() {
        String encodedCedentials = Helper.toBase64(String.format("%s:%S", mpesaConfiguration.getConsumerKey(), mpesaConfiguration.getConsumerSecret()));
        String encoded = "R3NaNjV1VGhHNVBOQldUSmdKTXJkUzRBVFRYb0ZleTg6QzBMeVdJT2hBa3ZHdkh2Tw==";


        String value="Basic R3NaNjV1VGhHNVBOQldUSmdKTXJkUzRBVFRYb0ZleTg6QzBMeVdJT2hBa3ZHdkh2Tw==";
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.setBasicAuth(encodedCedentials);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization",value);
        System.out.println("===============" + encodedCedentials);
        System.out.println(encoded);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
        UriComponents bulder = UriComponentsBuilder.fromUriString(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(url,HttpMethod.GET,httpEntity,JsonNode.class);
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getBody());
        return null;
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
        JsonNode responseBody = response.getBody();

        return mapper.treeToValue(responseBody, C2BSimulateResponseDto.class);
    }

    @Override
    public UrlRegisterResponse urlRegistration(UrlRegisterRequest urlRegisterRequest) throws JsonProcessingException {

        AccessTokenResponse tokenResponse = getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(tokenResponse.getAccessToken());
        UrlRegisterRequest urlRegisterRequest1 = new UrlRegisterRequest();
        urlRegisterRequest1.setConfirmationURL(mpesaConfiguration.getConfirmationURL());
        urlRegisterRequest1.setValidationURL(mpesaConfiguration.getValidationURL());
        urlRegisterRequest1.setShortCode(urlRegisterRequest.getShortCode());
        urlRegisterRequest1.setResponseType(urlRegisterRequest.getShortCode());
        HttpEntity<UrlRegisterRequest> httpEntity = new HttpEntity<>(urlRegisterRequest1, httpHeaders);
        String url = "https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        ResponseEntity<JsonNode> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.POST, httpEntity, JsonNode.class);
        JsonNode responseBody = response.getBody();
        log.info(" body {}", responseBody);
        return mapper.treeToValue(responseBody, UrlRegisterResponse.class);
    }
}

