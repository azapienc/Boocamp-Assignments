package com.bootcampzapien.assignment2.configuration;

import com.bootcampzapien.assignment2.kafkaObjects.SampleListener;
import com.bootcampzapien.assignment2.kafkaObjects.SampleProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfiguration {

    @Bean(initMethod = "receiveMessage")
    SampleListener listener() {
        return new SampleListener();
    }



//    @Bean
//    SampleProducer producer(){
//        return new SampleProducer();
//    }

}
