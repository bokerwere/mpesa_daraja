package com.boker.mpesa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MpesaApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	//WebMV
    @Test
    public  void dateTest() {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d-MMM-yyyy'T'HH:mm:ss"));
        System.out.println(localDateTime.toUpperCase());
    }
}
