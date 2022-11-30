package com.boker.mpesa.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Helper {
     public static String toBase64(String value){
        byte[] data=value.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(data);
    }
}
