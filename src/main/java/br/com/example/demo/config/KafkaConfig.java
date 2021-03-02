package br.com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${application.topic.player.name}")
    private String playerTopic;

    @Bean
    public NewTopic playerTopic() {
        return TopicBuilder.name(playerTopic).build();
    }

}
