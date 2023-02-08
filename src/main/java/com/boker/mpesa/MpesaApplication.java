package com.boker.mpesa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "mpesa payment"))
public class MpesaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MpesaApplication.class, args);
    }

}
