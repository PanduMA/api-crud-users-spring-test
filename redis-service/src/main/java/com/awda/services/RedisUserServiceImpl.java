package com.awda.services;

import com.awda.model.MessageTemplate;
import com.awda.model.UserData;
import com.awda.model.UserMongoDB;
import com.awda.repository.UserMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisUserServiceImpl implements RedisUserService {
    private static final String KEY = "USERS";
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<UserData> getAllRedisUser() {
        List<UserData> userList = redisTemplate.opsForHash().values(KEY);
        if (userList.isEmpty()) {
            return new ArrayList<>();
        }
        return userList;
    }

    @Override
    public UserData getRedisUser(Long id) {
        UserData userData = (UserData) redisTemplate.opsForHash().get(KEY, id);
        return userData;
    }
}
