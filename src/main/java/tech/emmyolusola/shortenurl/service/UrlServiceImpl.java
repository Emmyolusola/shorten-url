package tech.emmyolusola.shortenurl.service;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.emmyolusola.shortenurl.model.Url;
import tech.emmyolusola.shortenurl.model.UrlDto;
import tech.emmyolusola.shortenurl.repository.UrlRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UrlServiceImpl implements UrlService {

    private UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url generateShortLink(UrlDto urlDto) {

        // isNotEmpty method in class StringUtils is imported from apache.commons.lang3 package
        // the dependency had to be added in the application properties to access it
        if (StringUtils.isNotEmpty(urlDto.getOriginalUrl())){
            String encodedUrl = encodeUrl(urlDto.getOriginalUrl());
            Url urlToPersist = new Url();
            urlToPersist.setCreatedTime(LocalDateTime.now());
            urlToPersist.setOriginalUrl(urlDto.getOriginalUrl());
            urlToPersist.setShortUrl(encodedUrl);
            urlToPersist.setExpiredTime(getExpirationTime
                    (urlDto.getExpirationTime(), urlToPersist.getCreatedTime()));
            Url urlToReturn = persistShortLink(urlToPersist);

            if (urlToReturn != null){
                return urlToReturn;
            }
            return null;
        }
        return null;
    }

    private LocalDateTime getExpirationTime(String expirationTime, LocalDateTime createdTime) {

        if (StringUtils.isBlank(expirationTime)){
            return createdTime.plusSeconds(120);
        }
        return LocalDateTime.parse(expirationTime);
    }

    private String encodeUrl(String originalUrl) {

        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        // using Hashing from Google guava class for the encoding of the URl to short link
        // concatenating the url with the current time is to ensure that a unique url is created at all times
        // the murmur3_32() method is used because we're encoding to short links
        encodedUrl = Hashing.murmur3_32()
                .hashString(originalUrl.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return encodedUrl;
    }

    @Override
    public Url persistShortLink(Url url) {
        return urlRepository.save(url);
    }

    @Override
    public Url getEncodedUrl(String url) {
        return urlRepository.findByShortUrl(url);
    }

    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
