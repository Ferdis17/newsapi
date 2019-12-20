package com.news.apicodeathon.srevices;

import com.news.apicodeathon.domain.Article;
import com.news.apicodeathon.domain.Source;
import com.news.apicodeathon.domain.SourceResponse;
import com.news.apicodeathon.domain.TopHeadlinesResponse;
import com.news.apicodeathon.error.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NewsService {

    @Value(value = "${url}")
    private String url;

    @Value(value = "${apikey}")
    private String key;// = "948a0a04f57542d3a422056d71be0ed0";

    @Autowired
    private RestTemplate restTemplate;

    public List<Article> getArticlesByType(String type, String q, String language, String country, String sources, String category){
        String uri = url + "/" + type + "?apiKey=" + key;
        if(q != null && !q.isEmpty()){
            uri += "&q=" + q;
        }
        if(language != null && !language.isEmpty()){
            uri += "&language=" + language;
        }
        if(country != null && !country.isEmpty()){
            uri += "&country=" + country;
        }
        if(sources != null && !sources.isEmpty()){
            uri += "&sources=" + sources;
        }
        if(category != null && !category.isEmpty()){
            uri += "&category=" + category;
        }


        try {
            TopHeadlinesResponse result = this.restTemplate.getForObject(uri, TopHeadlinesResponse.class);
        if(result != null && "ok".equals(result.getStatus()) && result.getTotalResults() != null && result.getTotalResults() > 0) {
            return result.getArticles();
        } else {
            return Collections.emptyList();
        }
        } catch (Exception e) {
            throw new CustomError(e.getMessage());
        }
    }


    public List<Source> getSources(String language, String country) {
        String uri = url + "/sources?apiKey=" + key;
        if(language != null && !language.isEmpty()){
            uri += "&language=" + language;
        }
        if(country != null && !country.isEmpty()){
            uri += "&country=" + country;
        }


        try {
            SourceResponse result = this.restTemplate.getForObject(uri, SourceResponse.class);
            if(result != null && "ok".equals(result.getStatus())) {
                return result.getSources();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new CustomError(e.getMessage());
        }

    }
}
