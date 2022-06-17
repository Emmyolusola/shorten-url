package tech.emmyolusola.shortenurl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.emmyolusola.shortenurl.exception.ExpiredUrlException;
import tech.emmyolusola.shortenurl.exception.NoSpecifiedUrlException;
import tech.emmyolusola.shortenurl.model.Url;
import tech.emmyolusola.shortenurl.model.UrlDto;
import tech.emmyolusola.shortenurl.model.UrlResponseDto;
import tech.emmyolusola.shortenurl.service.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping (path = "/shorten-url")
public class UrlShorteningController {

    private final UrlService urlService;

    private HttpServletResponse response;

    @Autowired
    public UrlShorteningController(UrlService urlService, HttpServletResponse response) {
        this.urlService = urlService;
        this.response = response;
    }

    @PostMapping(path = "/generate")
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlDto urlDto) {
        Url urlToPost = urlService.generateShortLink(urlDto);
        if (urlToPost == null) {
            throw new NoSpecifiedUrlException("No url specified by client");
        }
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setOriginalUrl(urlToPost.getOriginalUrl());
        urlResponseDto.setExpirationDate(urlToPost.getExpiredTime());
        urlResponseDto.setShortLink(urlToPost.getShortUrl());

        return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{get-short-link}")
    public ResponseEntity<?> redirectUrlToOriginal(@PathVariable("get-short-link") String shortLink) throws IOException {
        Url urlToOriginal = urlService.getEncodedUrl(shortLink);
        if (StringUtils.isEmpty(shortLink)) {
            throw new NoSpecifiedUrlException("url is not specified by client");
        }
        if (urlToOriginal == null) {
            throw new NoSpecifiedUrlException("url does not exist or has expired");
        }

        if (urlToOriginal.getExpiredTime().isBefore(LocalDateTime.now())) {
            urlService.deleteShortLink(urlToOriginal);
            throw new ExpiredUrlException("url has expired, please generate another one");
        }
        response.sendRedirect(urlToOriginal.getOriginalUrl());

        return null;
    }
}
