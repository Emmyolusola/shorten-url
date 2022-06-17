package tech.emmyolusola.shortenurl.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Url {

    @Id
    @SequenceGenerator(
            name = "url_links",
            sequenceName = "url_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(nullable = false)
    private Long id;
    private String shortUrl;
    @Lob
    private String originalUrl;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime;

    public Url() {
    }

    public Url(String shortUrl, String originalUrl, LocalDateTime createdTime, LocalDateTime expiredTime) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", shortUrl='" + shortUrl + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", createdTime=" + createdTime +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
