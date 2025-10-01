package com.haemoo.haemooapi.presentation;

import com.haemoo.haemooapi.application.AwsS3Service;
import com.haemoo.haemooapi.dto.AwsS3Dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/videos")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("upload/multipart/initiate")
    public ResponseEntity<InitiateUploadResponse> initiateMultipartUpload(@RequestBody InitiateUploadRequest request) {
        String uploadId = awsS3Service.initiateMultipartUpload(request.getFileName());
        return ResponseEntity.ok(new InitiateUploadResponse(uploadId));
    }

    @PostMapping("upload/multipart/presigned-urls")
    public ResponseEntity<MultipartPresignedUrlsResponse> getMultipartPresignedUrls(@RequestBody MultipartPresignedUrlsRequest request) {

        String fileName = request.getFileName();
        String uploadId = request.getUploadId();
        int partCount = request.getPartCount();

        List<String> presignedUrls = awsS3Service.generatePresignedUrls(fileName, uploadId, partCount);

        return ResponseEntity.ok(new MultipartPresignedUrlsResponse(presignedUrls));
    }

    @PostMapping("upload/multipart/complete")
    public ResponseEntity<Void> completeMultipartUpload(@RequestBody CompleteUploadRequest request) {

        String fileName = request.getFileName();
        String uploadId = request.getUploadId();
        List<PartEtag> parts = request.getParts();

        awsS3Service.completeMultipartUpload(fileName, uploadId, parts);

        return ResponseEntity.ok().build();
    }

    @PostMapping("upload/multipart/abort")
    public ResponseEntity<Void> abortMultipartUpload(@RequestBody AbortUploadRequest request) {

        String fileName = request.getFileName();
        String uploadId = request.getUploadId();

        awsS3Service.abortMultipartUpload(uploadId, fileName);

        return ResponseEntity.ok().build();
    }


    @PostMapping("upload/single/presigned-url")
    public ResponseEntity<SinglePresignedUrlResponse> getSinglePresignedUrl(@RequestBody SinglePresignedUrlRequest request) {

        String fileName = request.getFileName();

        String presignedUrl = awsS3Service.generateSinglePresignedUrl(fileName);

        return ResponseEntity.ok(new SinglePresignedUrlResponse(presignedUrl));
    }
}
