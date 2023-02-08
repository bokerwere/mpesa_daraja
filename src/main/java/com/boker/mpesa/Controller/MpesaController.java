package com.boker.mpesa.Controller;

import com.boker.mpesa.docs.Example;
import com.boker.mpesa.dto.MpesaRequest.B2CPaymentRequest;
import com.boker.mpesa.dto.MpesaRequest.C2BSimulateRequestDto;
import com.boker.mpesa.dto.MpesaRequest.InternalStkPushRequest;
import com.boker.mpesa.dto.MpesaRequest.UrlRegisterRequest;
import com.boker.mpesa.dto.MpesaResponse.AccessTokenResponse;
import com.boker.mpesa.dto.MpesaResponse.C2BSimulateResponseDto;
import com.boker.mpesa.services.MpesaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mobile-money")
@RequiredArgsConstructor
public class MpesaController {
    private final MpesaService mpesaService;

    @SneakyThrows
    @GetMapping(value = "/token")
    public ResponseEntity<?> getToken() {
        return ResponseEntity.ok(mpesaService.getToken());
    }

    @PostMapping(value = "/c2b-simulate")
    public ResponseEntity<C2BSimulateResponseDto> c2BSimulate(@RequestBody C2BSimulateRequestDto c2BSimulateRequestDto) throws JsonProcessingException {
        return ResponseEntity.ok(mpesaService.c2bSimulate(c2BSimulateRequestDto));
    }

    @SneakyThrows
    @PostMapping(value = "/url-register")
    public ResponseEntity<JsonNode> urlRegister(@RequestBody UrlRegisterRequest urlRegisterRequest) {
        return ResponseEntity.ok(mpesaService.urlRegistration(urlRegisterRequest));

    }

    @PostMapping(value = "/b2c-request")
    public ResponseEntity<JsonNode> B2CPayment(@RequestBody B2CPaymentRequest b2CPaymentRequest) {
        return ResponseEntity.ok(mpesaService.B2CPayment(b2CPaymentRequest));

    }

    @Operation(summary = "stk push", description = "M-Pesa Express||LIPA NA M-PESA",
            responses ={@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Example.STK_PUSH))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json"))})
    @PostMapping(value = "/stk-request")
    public ResponseEntity<?> stkPushPayment(@RequestBody InternalStkPushRequest stkPushRequest) {
        return ResponseEntity.ok(mpesaService.StkPushTransaction(stkPushRequest));

    }
}
