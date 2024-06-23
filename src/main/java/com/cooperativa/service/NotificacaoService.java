package com.cooperativa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
