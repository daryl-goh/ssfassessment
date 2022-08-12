package sdfassessment.newsarticle.sdfassessment.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

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
    
}
