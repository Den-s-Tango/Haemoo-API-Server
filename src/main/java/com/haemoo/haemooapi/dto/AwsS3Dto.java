package com.haemoo.haemooapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AwsS3Dto {

    @Data
    @NoArgsConstructor
    public static class InitiateUploadRequest {
        private String fileName;
    }

    @Data
    @AllArgsConstructor
    public static class InitiateUploadResponse {
        private String uploadId;
    }

    @Data
    @NoArgsConstructor
    public static class CompleteUploadRequest {
        private String fileName;
        private String uploadId;
        private List<PartEtag> parts;
    }

    @Data
    @NoArgsConstructor
    public static class PartEtag {
        private int partNumber;
        private String etag;
    }

    @Data
    @NoArgsConstructor
    public static class MultipartPresignedUrlsRequest {
        private String fileName;
        private String uploadId;
        private int partCount;
    }

    @Data
    @AllArgsConstructor
    public static class MultipartPresignedUrlsResponse {
        private List<String> presignedUrls;
    }

    @Data
    @NoArgsConstructor
    public static class SinglePresignedUrlRequest {
        private String fileName;
    }

    @Data
    @AllArgsConstructor
    public static class SinglePresignedUrlResponse {
        private String url;
    }

    @Data
    @NoArgsConstructor
    public static class AbortUploadRequest {
        private String fileName;
        private String uploadId;
    }
}
