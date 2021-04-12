package org.agbrothers.englishbot.adapter.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Config {

    @Bean
    @Profile("development")
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
