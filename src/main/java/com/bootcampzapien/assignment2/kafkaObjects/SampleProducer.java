package com.bootcampzapien.assignment2.kafkaObjects;

import com.bootcampzapien.assignment2.dto.RequestDto;
import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class SampleProducer {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final Logger log = LoggerFactory.getLogger(SampleProducer.class.getName());
    private final KafkaSender<Integer, String> sender;

    public SampleProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        SenderOptions<Integer, String> senderOptions = SenderOptions.create(props);

        sender = KafkaSender.create(senderOptions);
    }

    public void sendMessage(String topic, String requestDto) throws InterruptedException {
        sender.send(Mono.just(requestDto).map(i ->
                        SenderRecord.create(new ProducerRecord<>(
                                topic,
                                1,
                                requestDto), i))
                )
                .doOnError(e -> log.error("Send failed", e))
                .doOnNext(r -> log.info("Message # " + r.correlationMetadata() + " send response: " + r.recordMetadata()))
                .subscribe();
    }

    public void close() {
        sender.close();
    }

}
