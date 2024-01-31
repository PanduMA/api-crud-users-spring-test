package com.awda.kafka;

import com.awda.model.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.Serializable;

@Service
public class UserProducer {
    private static final String topic = "topic-user";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);
    private KafkaTemplate<String, Serializable> kafkaTemplate;

    @Autowired
    public UserProducer(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageTemplate messageTemplate) {
        LOGGER.info(String.format("send message to kafka => %s", messageTemplate));

        var sending = kafkaTemplate.send(topic, messageTemplate);
        sending.whenComplete((sendResult, ex) -> {
            if (ex != null)
                sending.completeExceptionally(ex);
            else
                sending.complete(sendResult);
        });
    }
}
