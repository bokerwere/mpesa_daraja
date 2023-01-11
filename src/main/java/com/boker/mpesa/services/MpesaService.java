package com.boker.mpesa.services;

import com.boker.mpesa.dto.MpesaRequest.B2CPaymentRequest;
import com.boker.mpesa.dto.MpesaRequest.C2BSimulateRequestDto;
import com.boker.mpesa.dto.MpesaRequest.InternalStkPushRequest;
import com.boker.mpesa.dto.MpesaRequest.UrlRegisterRequest;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface MpesaService {
    AccessTokenResponse getToken() throws JsonProcessingException;

    AccessTokenResponse generateToken();

    C2BSimulateResponseDto c2bSimulate(C2BSimulateRequestDto c2BSimulateRequestDto) throws JsonProcessingException;

    JsonNode urlRegistration(UrlRegisterRequest urlRegisterRequest) throws JsonProcessingException;

    JsonNode B2CPayment(B2CPaymentRequest b2CPaymentRequest);

    JsonNode StkPushTransaction(InternalStkPushRequest internalStkPushRequest);


}
