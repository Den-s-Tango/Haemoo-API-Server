package com.haemoo.haemooapi.presentation;

import com.haemoo.haemooapi.application.AwsS3Service;
import com.haemoo.haemooapi.dto.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @GetMapping("/presigned-url/get")
    public ResponseEntity<PresignedUrlResponse> getGetPresignedUrl(@RequestParam String fileName) {
        String presignedUrl = awsS3Service.generateGetPresignedUrl(fileName);

        PresignedUrlResponse response = new PresignedUrlResponse(presignedUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/presigned-url/put")
    public ResponseEntity<PresignedUrlResponse> getPutPresignedUrl(@RequestParam String fileName) {
        String presignedUrl = awsS3Service.generatePutPresignedUrl(fileName);

        PresignedUrlResponse response = new PresignedUrlResponse(presignedUrl);

        return ResponseEntity.ok(response);
    }
}
