package com.boker.mpesa.services;

import com.boker.mpesa.dto.MpesaRequest.C2BSimulateRequestDto;
import com.boker.mpesa.dto.MpesaRequest.UrlRegisterRequest;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.boker.mpesa.dto.MpesaResponse.UrlRegisterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MpesaService {
    AccessTokenResponse getToken() throws JsonProcessingException;
    AccessTokenResponse generateToken();
    C2BSimulateResponseDto c2bSimulate(C2BSimulateRequestDto c2BSimulateRequestDto) throws JsonProcessingException;
    UrlRegisterResponse urlRegistration(UrlRegisterRequest urlRegisterRequest) throws JsonProcessingException;
}
