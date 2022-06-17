package tech.emmyolusola.shortenurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.emmyolusola.shortenurl.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortUrl(String shortUrl);
}
