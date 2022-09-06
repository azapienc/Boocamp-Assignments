package com.bootcampzapien.assignment_1.publisher;

import com.bootcampzapien.assignment_1.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ObjectMapper objectMapper;
    private final KafkaSender<Integer, String> sender;
    private static final Logger log = LoggerFactory.getLogger(SampleProducer.class.getName());
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC = "app_updates";

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

    public void sendMessage(String topic, RequestDto requestDto) {
        sender.send(Mono.just(requestDto)
                        .map(i -> SenderRecord.create(new ProducerRecord<>(
                                topic,
                                requestDto.getEmp_id(),
                                handleSerialization(requestDto)), i)))
                .doOnError(e -> {
                    log.error("Send failed", e);
                    throw new RuntimeException(e);
                })
                .doOnNext(r -> log.info("Message # " + r.correlationMetadata() + " send response: " + r.recordMetadata()))
                .subscribe();
    }

    /**
     * Serializes input object to JSON object
     * @param requestDto
     * @return
     */
    private String handleSerialization(RequestDto requestDto) {
        try {
            return objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        sender.close();
    }

}
