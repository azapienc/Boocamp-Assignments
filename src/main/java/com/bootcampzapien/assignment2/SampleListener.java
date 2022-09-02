package com.bootcampzapien.assignment2;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@Component
public class SampleListener {
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC = "app_updates";
    private static final Logger log = LoggerFactory.getLogger(SampleListener.class.getName());
    //    private final KafkaSender<Integer, String> sender;
    private final KafkaReceiver<Integer, String> receiver;

    public static final String TOPIC2 = "em_DQL";

    public StringDeserializer deserializer = new StringDeserializer();

    @Autowired
    SampleProducer producer;

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
        inboundFlux.subscribe(r -> {
            System.out.println("Message: " + r.value());
            r.receiverOffset().acknowledge();
        });
    }


//    public void sendMessage(String topic, RequestDto requestDto) throws InterruptedException {
//        sender.send(Mono.just(requestDto).map(i ->
//                        SenderRecord.create(new ProducerRecord<>(
//                                topic,
//                                requestDto.getEmp_id(),
//                                requestDto.toString()), i))
//                )
//                .doOnError(e -> log.error("Send failed", e))
//                .doOnNext(r -> log.info("Message # " + r.correlationMetadata() + " send response: " + r.recordMetadata()))
//                .subscribe();
//    }
//
//    public void close() {
//        receiver.close();
//    }

}
