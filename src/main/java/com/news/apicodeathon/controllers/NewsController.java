package com.news.apicodeathon.controllers;

import com.news.apicodeathon.srevices.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping(value = "docs/top-headlines")
    public ResponseEntity<?> getTopNews(@RequestParam(value = "language", required = false) String language,
                                        @RequestParam(value = "country", required = false) String country,
                                        @RequestParam(value = "sources", required = false) String sources,
                                        @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(newsService.getArticlesByType("top-headlines", null, language, country, sources, category));
    }

    @GetMapping(value = "docs/everything")
    public ResponseEntity<?> getEverything(@RequestParam(value = "q", required = false) String q) {
        return ResponseEntity.ok(newsService.getArticlesByType("everything", q, null, null, null, null));
    }

    @GetMapping(value = "docs/sources", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSources(@RequestParam(value = "language", required = false) String language,
                                        @RequestParam(value = "country", required = false) String country) {
        return ResponseEntity.ok(newsService.getSources(language,country));
    }

}