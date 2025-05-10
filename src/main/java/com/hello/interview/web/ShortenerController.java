package com.hello.interview.web;

import com.hello.interview.web.service.shortener.ShortenerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/short")
@PreAuthorize("hasRole('USER')")
public class ShortenerController {

    // It is essentially Spring’s version of the Elvis operator (?:) — used for fallback/default values.
    @Value("${shortener.base-url:https://www.shortener.com}")
    private String baseUrl;

    private final ShortenerService shortenerService;

    // Return short URL
    @PostMapping
    public ResponseEntity<String> getEncoded(@RequestBody String longUrl) {
        log.debug("Requested encoding long URL = {}", longUrl);
        String shortUrl = shortenerService.submitLongUrl(longUrl);
        var response = baseUrl + "/short/" + shortUrl;
        return ResponseEntity.ok(response);
    }

    // Return redirect to the long URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getDecoded(@PathVariable String shortUrl) {
        log.debug("Requested the original long URL, short = {}", shortUrl);
        var longUrl = shortenerService.getShortUrl(shortUrl);
        if (longUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // The browser automatically follows the Location header in a 3xx response (302, 301, 307, etc.).
        return ResponseEntity
                .status(302)
                .header("Location", longUrl.get())
                .build();
    }
}
