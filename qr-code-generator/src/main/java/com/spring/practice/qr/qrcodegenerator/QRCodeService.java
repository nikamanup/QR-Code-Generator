package com.spring.practice.qr.qrcodegenerator;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QRCodeService {
    public byte[] generateQRCodeImage(String data, int width, int height) throws Exception {
        
        // 1. Define encoding parameters
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 2. Generate the BitMatrix
        BitMatrix matrix = new MultiFormatWriter().encode(
            data, 
            BarcodeFormat.QR_CODE, 
            width, 
            height, 
            hints
        );

        // 3. Write the BitMatrix to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        // 4. Return the byte array
        return outputStream.toByteArray();
    }

    // Inside your QRCodeService.java
    public String createVCardString(String firstName, String lastName, String mobile, String company) {
    // Note the use of "\n" to represent new lines in the encoded string.
    return "BEGIN:VCARD\n" +
           "VERSION:3.0\n" +
           "N:" + lastName + ";" + firstName + ";;;\n" +
           "FN:" + firstName + " " + lastName + "\n" +
           "ORG:" + company + "\n" +
           "TEL;TYPE=CELL:" + mobile + "\n" +
           "END:VCARD";
    }

    
}
