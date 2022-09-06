package com.bootcampzapien.assignment2.kafkaObjects;

import com.bootcampzapien.assignment2.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SampleListener {
    @Autowired
    SampleProducer producer;
    @Autowired
    ObjectMapper objectMapper;

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "app_updates";
    private final KafkaReceiver<Integer, String> receiver;
    private StringDeserializer deserializer = new StringDeserializer();

    private static final String EM_DQL = "em_DQL";
    private static final String EMP_UPDATES = "emp_updates";

    public SampleListener() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ReceiverOptions<Integer, String> receiverOptions =
                ReceiverOptions.<Integer, String>create(consumerProps)
                        .subscription(Collections.singleton(TOPIC));

        receiver = KafkaReceiver.create(receiverOptions);
    }

    public void receiveMessage() {
        Flux<ReceiverRecord<Integer, String>> inboundFlux = receiver.receive();
        inboundFlux
                .subscribe(this::evaluateAndPublishMessage);
    }

    private void evaluateAndPublishMessage(ReceiverRecord<Integer, String> r) {
        log.info("Received message: " + r.toString());
        if (isMessagevalid(r)) {
            log.info("Publishing to emp_updates");
            publishMessage(EMP_UPDATES, r);
        } else {
            log.warn("Publishing to em_dql");
            publishMessage(EM_DQL, r);
        }
        r.receiverOffset().acknowledge();
    }

    private boolean isMessagevalid(ReceiverRecord<Integer, String> r) {
        RequestDto formattedMessage = new RequestDto();
        try {
            formattedMessage = objectMapper.readValue(r.value(), RequestDto.class);
            System.out.println(formattedMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void publishMessage(String topic, ReceiverRecord<Integer, String> message) {
        try {
            producer.sendMessage(topic, message.value());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
