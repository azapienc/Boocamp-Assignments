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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SampleListener {
    @Autowired
    private SampleProducer producer;
    @Autowired
    private ObjectMapper objectMapper;

    private final KafkaReceiver<Integer, String> receiver;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String EMP_UPDATES = "emp_updates";
    private static final String EM_DQL = "em_DQL";
    private static final String TOPIC = "app_updates";

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

    /**
     * Listens the updates topic
     */
    public void receiveMessage() {
        receiver.receive().subscribe(this::evaluateAndPublishMessage);
    }

    /**
     * Evaluates if the message is correct and publish it to the corresponding topic
     * @param receivedMessage
     */
    private void evaluateAndPublishMessage(ReceiverRecord<Integer, String> receivedMessage) {
        log.info("Received message: " + receivedMessage.toString());
        receivedMessage.receiverOffset().acknowledge();

        if (isMessagevalid(receivedMessage)) {
            log.info("Publishing to emp_updates");
            publishMessage(EMP_UPDATES, receivedMessage);
        } else {
            log.warn("Publishing to em_dql");
            publishMessage(EM_DQL, receivedMessage);
        }
    }

    /**
     * Deserializes and validates the message
     * @param receivedMessaged
     * @return
     */
    private boolean isMessagevalid(ReceiverRecord<Integer, String> receivedMessaged) {
        RequestDto formattedMessage = new RequestDto();
        try {
            formattedMessage = objectMapper.readValue(receivedMessaged.value(), RequestDto.class);
            if (formattedMessage.getEmp_id() <= 0 ||
                    formattedMessage.getEmp_name() == null || formattedMessage.getEmp_name().equals("") ||
                    formattedMessage.getEmp_city() == null || formattedMessage.getEmp_city().equals("") ||
                    formattedMessage.getEmp_phone() == null || formattedMessage.getEmp_phone().equals("") ||
                    formattedMessage.getJava_exp() == null ||
                    formattedMessage.getSpring_exp() == null)
                return false;
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }

    /**
     * Publish the message to Kafka once it is validated
     * @param topic
     * @param message
     */
    private void publishMessage(String topic, ReceiverRecord<Integer, String> message) {
        producer.sendMessage(topic, message.value());
    }
}
