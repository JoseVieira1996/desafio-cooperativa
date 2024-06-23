package com.cooperativa.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotificacaoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NotificationServiceTest {
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private NotificacaoService notificationService;


    @Test
    void testSendNotification() {
        when(kafkaTemplate.send(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new CompletableFuture<>());
        notificationService.sendNotification("Topic", "Not all who wander are lost");
        verify(kafkaTemplate).send(eq("Topic"), eq("Not all who wander are lost"));
    }
}
