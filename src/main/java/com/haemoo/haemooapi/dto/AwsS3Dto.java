package com.haemoo.haemooapi.dto;

import lombok.Getter;

@Getter
public class PresignedUrlDto {

    private final String url;

    public PresignedUrlDto(String url) {
        this.url = url;
    }

}
