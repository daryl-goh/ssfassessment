package sdfassessment.newsarticle.sdfassessment.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sdfassessment.newsarticle.sdfassessment.models.News;
import sdfassessment.newsarticle.sdfassessment.services.NewsService;

@RestController
@RequestMapping(path="/news", produces=MediaType.APPLICATION_JSON_VALUE)
public class NewsRESTController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping(value="{id}")
    public ResponseEntity<String> getArticles(@PathVariable String id) {
        Optional<News> opt = newsSvc.getArticlesById(id);

        if (opt.isEmpty()) {
            JsonObject error = Json.createObjectBuilder()
                .add("error", "Cannot find news article %s".formatted(id))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error.toString());
        }

        News news = opt.get();
        return ResponseEntity.ok(news.toJson().toString());
    }

    
    
}
