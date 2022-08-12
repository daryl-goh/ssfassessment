package sdfassessment.newsarticle.sdfassessment.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sdfassessment.newsarticle.sdfassessment.models.News;
import sdfassessment.newsarticle.sdfassessment.repositories.NewsRepository;

@Service
public class NewsService {
    
    private static final String URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

    @Value("${API_KEY}")
    private String api_key;

    public List<News> getArticles(){
        String payload;

        String url = UriComponentsBuilder.fromUriString(URL)
            .queryParam("api_key", api_key)
            .toUriString();

            RequestEntity<Void> req = RequestEntity.get(url).build();

            RestTemplate template = new RestTemplate();

            ResponseEntity<String> resp = template.exchange(req, String.class);

            payload = resp.getBody();
            System.out.println("payload: " + payload);

            Reader stringReader = new StringReader(payload);
            JsonReader jsonReader = Json.createReader(stringReader);
            JsonObject jsonObject = jsonReader.readObject();
            JsonArray data = jsonObject.getJsonArray("Data");
            List<News> list = new LinkedList<>();
            for (int i = 0; i < data.size(); i++) {
                JsonObject jo = data.getJsonObject(i);
                list.add(News.create(jo));
            }
            return list;
    }

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;
    private NewsRepository newsRepo;
    
    // public void saveArticles(List<News> articles) {
    //     String id = articles.getId();
    //     List<News> articles = new LinkedList<>();
    //     ListOperations<String, String> listOps = redisTemplate.opsForList();
    //     listOps.leftPushAll(id, 
	// 			contents.stream()
	// 				.map(v -> v.toJson().toString()).toList()
	// 	);

    // }

    
    public Optional<News> getArticlesById(String id) {
        String result = newsRepo.get(id);
        if (null == result)
            return Optional.empty();

        return Optional.of(News.create(result));
    }
}
