package tech.emmyolusola.shortenurl.service;

import tech.emmyolusola.shortenurl.model.Url;
import tech.emmyolusola.shortenurl.model.UrlDto;

public interface UrlService {

    public Url generateShortLink(UrlDto urlDto);

    public Url persistShortLink(Url url);

    public Url getEncodedUrl(String url);

    public void deleteShortLink(Url url);
}
