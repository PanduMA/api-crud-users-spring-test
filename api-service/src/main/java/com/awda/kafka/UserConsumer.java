package com.awda.kafka;

import com.awda.model.MessageTemplate;
import com.awda.model.UserMongoDB;
import com.awda.repository.UserMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
    @Autowired
    UserMongoRepository userMongoRepository;

    @KafkaListener(topics = "topic-user", groupId = "group-consumer-user", containerFactory = "kafkaListenerContainerFactory")
    public void getMessage(MessageTemplate messageTemplate) {
        LOGGER.info(String.format("receive message from kafka => %s", messageTemplate.toString()));
        String messageMethod = messageTemplate.getMethod();
        UserMongoDB userMongoDB = new UserMongoDB(
                messageTemplate.getUser().getId(),
                messageTemplate.getUser().getUserName(),
                messageTemplate.getUser().getAccountNumber(),
                messageTemplate.getUser().getEmailAddress(),
                messageTemplate.getUser().getIdentityNumber(),
                messageTemplate.getUser().getPassword()
        );
        if (messageMethod.equals("POST") || messageMethod.equals("PUT")) {
            userMongoRepository.save(userMongoDB);
        } else {
            userMongoRepository.deleteById(messageTemplate.getUser().getId());
        }
    }
}
