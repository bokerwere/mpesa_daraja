package com.boker.mpesa.Utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class HelperUtility {
    public static String toBase64(String value) {
        byte[] data = value.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.getEncoder().encodeToString(data);
    }

    public static String getStkPushPassword(String shortCode, String passKey, String timestamp) {
        String concatinatedString = String.format("%s%s%s", shortCode, passKey, timestamp);
        return toBase64(concatinatedString);
    }

    public static String getTransactionUniqueNumber() {
        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
        String transactionNumber = stringGenerator.generate(12).toUpperCase();
        log.info("Transaction Number:{}", transactionNumber);
        return transactionNumber;

    }

    //generate timestamp
    public static String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(new Date());
    }
}
