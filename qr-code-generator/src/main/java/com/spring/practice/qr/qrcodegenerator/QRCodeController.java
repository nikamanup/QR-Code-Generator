package com.spring.practice.qr.qrcodegenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    // Example Endpoint: http://localhost:8080/generate?data=Hello%20World&size=300
    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(
        @RequestParam(name = "data") String data,
        @RequestParam(name = "size", defaultValue = "200") int size) 
    {
        try {
            // Call the service to generate the image bytes
            byte[] qrCodeBytes = qrCodeService.generateQRCodeImage(data, size, size);

            // Return the image data with the correct content type (image/png)
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);

        } catch (Exception e) {
            // Log the error and return an appropriate status code
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Example Endpoint: http://localhost:8080/contact?first=John&last=Doe&mobile=9998887770&company=ABC%20Inc
    @GetMapping(value = "/contact", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateContactQRCode(
        @RequestParam(name = "first") String firstName,
        @RequestParam(name = "last") String lastName,
        @RequestParam(name = "mobile") String mobile,
        @RequestParam(name = "company") String company,
        @RequestParam(name = "size", defaultValue = "200") int size) 
    {
        try {
            // 1. Build the vCard String
            String vCardData = qrCodeService.createVCardString(firstName, lastName, mobile, company);

            // 2. Generate the QR code bytes
            byte[] qrCodeBytes = qrCodeService.generateQRCodeImage(vCardData, size, size);

            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Handles map, YouTube, general website, and now feedback forms
    @GetMapping(value = "/generate/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateUrlQRCode(
        @RequestParam(name = "url") String url, // Renamed 'data' to 'url' for clarity
        @RequestParam(name = "size", defaultValue = "300") int size) 
    {
        try {
            // Your QR Code Service method uses the URL directly
            byte[] qrCodeBytes = qrCodeService.generateQRCodeImage(url, size, size);

            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/generate/review", produces = MediaType.IMAGE_PNG_VALUE)
public ResponseEntity<byte[]> generateReviewQRCode(
    @RequestParam(name = "placeId") String placeId,
    @RequestParam(name = "size", defaultValue = "300") int size) 
{
    try {
        // 1. Construct the direct review URL
        String baseUrl = "https://search.google.com/local/writereview?placeid=";
        String reviewUrl = baseUrl + placeId;

        // 2. Generate the QR code bytes
        // Assuming your service method is called 'generateQRCodeImage'
        byte[] qrCodeBytes = qrCodeService.generateQRCodeImage(reviewUrl, size, size);

        // 3. Return the PNG image
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(qrCodeBytes);

    } catch (Exception e) {
        // Log the error for debugging
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
