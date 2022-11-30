package com.boker.mpesa.Controller;

import com.boker.mpesa.dto.MpesaRequest.C2BSimulateRequestDto;
import com.boker.mpesa.dto.MpesaRequest.UrlRegisterRequest;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.boker.mpesa.dto.MpesaResponse.UrlRegisterResponse;
import com.boker.mpesa.services.MpesaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mobile-money")
@RequiredArgsConstructor
public class MpesaController {
    private  final MpesaService mpesaService;
    @SneakyThrows
    @GetMapping(value = "/token")
    public ResponseEntity<AccessTokenResponse>getToken(){
        return ResponseEntity.ok(mpesaService.getToken());
    }
    @PostMapping(value="/c2b-simulate")
    public ResponseEntity<C2BSimulateResponseDto> c2BSimulate(@RequestBody C2BSimulateRequestDto c2BSimulateRequestDto) throws JsonProcessingException {
        return  ResponseEntity.ok(mpesaService.c2bSimulate(c2BSimulateRequestDto));
    }
    @SneakyThrows
    @PostMapping(value = "/url-register")
    public ResponseEntity<UrlRegisterResponse> urlRegister(@RequestBody UrlRegisterRequest urlRegisterRequest){
        return  ResponseEntity.ok(mpesaService.urlRegistration(urlRegisterRequest));

    }
}
