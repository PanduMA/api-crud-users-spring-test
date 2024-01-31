package com.awda.kafka;

import com.awda.model.MessageTemplate;
import com.awda.model.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
    private static final String KEY = "USERS";
    @Autowired
    RedisTemplate redisTemplate;

    @KafkaListener(topics = "topic-user", groupId = "group-consumer-user-redis", containerFactory = "kafkaListenerContainerFactory")
    public void getMessage(MessageTemplate messageTemplate) {
        LOGGER.info(String.format("receive message from redis consumer => %s", messageTemplate.toString()));
        String messageMethod = messageTemplate.getMethod();
        if (messageMethod.equals("POST") || messageMethod.equals("PUT")) {
            redisTemplate.opsForHash().put(KEY, messageTemplate.getUser().getId(), messageTemplate.getUser());
        } else {
            redisTemplate.opsForHash().delete(KEY, messageTemplate.getUser().getId());
        }
    }
}
