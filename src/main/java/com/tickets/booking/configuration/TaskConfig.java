package com.tickets.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {
    @Bean
    SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
