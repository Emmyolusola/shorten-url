package tech.emmyolusola.shortenurl.model;

import java.time.LocalDateTime;

public class UrlDto {

    private String originalUrl;
    private String expirationTime;

    public UrlDto() {
    }

    public UrlDto(String originalUrl, String expirationTime) {
        this.originalUrl = originalUrl;
        this.expirationTime = expirationTime;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "originalUrl='" + originalUrl + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                '}';
    }
}
