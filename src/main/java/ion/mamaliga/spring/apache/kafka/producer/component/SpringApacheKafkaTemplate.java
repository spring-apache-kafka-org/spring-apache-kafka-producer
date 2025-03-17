// Copyright (c) 2023 Ion Mamaliga Ltd.

package ion.mamaliga.spring.apache.kafka.producer.component;

import ion.mamaliga.spring.apache.kafka.producer.dto.ObjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringApacheKafkaTemplate {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, ObjectDto> kafkaTemplateForObjectDto;

    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("String message {} published to Kafka topic {}", message, topic);
    }

    public void sendObject(String topic, ObjectDto message) {
        final ProducerRecord<String, ObjectDto> producerRecord = new ProducerRecord<>(topic, message);
        kafkaTemplateForObjectDto.send(producerRecord);
        log.info("Object message {} published to Kafka topic {}", message, topic);
    }
}
