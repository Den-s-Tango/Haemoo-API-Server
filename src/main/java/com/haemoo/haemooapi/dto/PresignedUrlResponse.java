package com.haemoo.haemooapi.dto;

import lombok.Getter;

@Getter
public class PresignedUrlResponse {

    private final String url;

    public PresignedUrlResponse(String url) {
        this.url = url;
    }

}
