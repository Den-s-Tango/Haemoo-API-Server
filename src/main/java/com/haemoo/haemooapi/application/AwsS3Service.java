package com.haemoo.haemooapi.application;

import com.haemoo.haemooapi.dto.AwsS3Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String initiateMultipartUpload(String key) {
        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(
                CreateMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );

        return response.uploadId();
    }

    public List<String> generatePresignedUrls(String key, String uploadId, int partCount) {
        List<String> presignedUrls = new ArrayList<>();

        for (int partNumber = 1; partNumber <= partCount; partNumber++) {

            final int finalPartNumber = partNumber;
            PresignedUploadPartRequest presignedRequest = s3Presigner.presignUploadPart(
                    req -> req.signatureDuration(Duration.ofMinutes(15))
                            .uploadPartRequest(
                                    UploadPartRequest.builder()
                                            .bucket(bucketName)
                                            .key(key)
                                            .uploadId(uploadId)
                                            .partNumber(finalPartNumber)
                                            .build()
                            )
            );

            presignedUrls.add(presignedRequest.url().toString());
        }

        return presignedUrls;
    }

    public void completeMultipartUpload(String key, String uploadId, List<AwsS3Dto.PartEtag> parts) {

        List<CompletedPart> completedParts = parts.stream()
                .map(part -> CompletedPart.builder()
                        .partNumber(part.getPartNumber())
                        .eTag(part.getEtag())
                        .build())
                .toList();

        s3Client.completeMultipartUpload(
                CompleteMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .uploadId(uploadId)
                        .multipartUpload(CompletedMultipartUpload.builder()
                                .parts(completedParts)
                                .build())
                        .build()
        );
    }

    public void abortMultipartUpload(String key, String uploadId) {
        s3Client.abortMultipartUpload(
                AbortMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .uploadId(uploadId)
                        .build()
        );
    }

    public String generateSinglePresignedUrl(String objectKey) {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PutObjectPresignRequest putObjectPresignedRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignedRequest);

        return presignedPutObjectRequest.url().toString();
    }
}
