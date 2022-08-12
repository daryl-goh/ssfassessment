package sdfassessment.newsarticle.sdfassessment.repositories;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sdfassessment.newsarticle.sdfassessment.models.News;

@Repository
public class NewsRepository {

    @Autowired 
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public String get(String id) {
        ValueOperations<String,String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(id);
    }

    public void saveArticles(List<News> articles) {
        
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        for (News n: articles) {
            listOps.leftPush(n.getId(), n.toJson().toString());
        }
    }

    public News getArticles(String id) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String jsonString = listOps.leftPop(id);
        System.out.println(jsonString);
        JsonObject jsonObject = createJsonObject(jsonString);
        News news = new News();
        return news;
    }

    public JsonObject createJsonObject(String jsonString) {
        Reader reader = new StringReader(jsonString);
        return Json
                .createReader(reader)
                .readObject();
    }
    
}
