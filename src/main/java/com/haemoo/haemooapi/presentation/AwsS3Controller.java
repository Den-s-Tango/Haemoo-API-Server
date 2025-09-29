package com.haemoo.haemooapi.presentation;

import com.haemoo.haemooapi.application.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @GetMapping("/presigned-url/get")
    public ResponseEntity<String> getGetPresignedUrl(@RequestParam String fileName) {
        String presignedUrl = awsS3Service.generateGetPresignedUrl(fileName);
        return ResponseEntity.ok(presignedUrl);
    }

    @GetMapping("/presigned-url/put")
    public ResponseEntity<String> getPutPresignedUrl(@RequestParam String fileName) {
        String presignedUrl = awsS3Service.generatePutPresignedUrl(fileName);
        return ResponseEntity.ok(presignedUrl);
    }
}
