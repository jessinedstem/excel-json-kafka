package com.edstem.kakfa.exceljsonkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ExcelJsonKafkaConfig {
    @Bean
    public NewTopic newTopic(){
         return TopicBuilder.name("excel-json-converter")
                 .build();
    }
}
