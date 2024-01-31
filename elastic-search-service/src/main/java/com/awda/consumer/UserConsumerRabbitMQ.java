package com.awda.consumer;

import com.awda.model.ElasticUser;
import com.awda.model.MessageTemplate;
import com.awda.repository.UserElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerRabbitMQ {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumerRabbitMQ.class);
    @Autowired
    UserElasticRepository userElasticRepository;

    @RabbitListener(queues = {"q.user-registration"})
    public void getMessage(MessageTemplate messageTemplate) {
        LOGGER.info(String.format("receive message from rabbitmq => %s", messageTemplate.toString()));
        String messageMethod = messageTemplate.getMethod();
        ElasticUser elasticUser = new ElasticUser(
                messageTemplate.getUser().getId(),
                messageTemplate.getUser().getUserName(),
                messageTemplate.getUser().getAccountNumber(),
                messageTemplate.getUser().getEmailAddress(),
                messageTemplate.getUser().getIdentityNumber(),
                messageTemplate.getUser().getPassword()
        );
        if (messageMethod.equals("POST") || messageMethod.equals("PUT")) {
            userElasticRepository.save(elasticUser);
        } else {
            userElasticRepository.deleteById(messageTemplate.getUser().getId());
        }
    }
}
