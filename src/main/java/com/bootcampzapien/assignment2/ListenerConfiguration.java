package com.bootcampzapien.assignment2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfiguration {

    @Bean(initMethod = "receiveMessage")
    SampleListener listener() {
        return new SampleListener();
    }

}
