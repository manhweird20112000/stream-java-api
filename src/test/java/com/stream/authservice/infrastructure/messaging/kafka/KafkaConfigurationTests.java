package com.stream.authservice.infrastructure.messaging.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class KafkaConfigurationTests {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void kafkaTemplateIsConfigured() {
        assertThat(kafkaTemplate).isNotNull();
    }
}
