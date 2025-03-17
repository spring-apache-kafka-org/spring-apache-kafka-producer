package ion.mamaliga.spring.apache.kafka.producer.service;

import ion.mamaliga.spring.apache.kafka.producer.component.SpringApacheKafkaTemplate;
import ion.mamaliga.spring.apache.kafka.producer.dto.ObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringApacheKafkaProducerService {

    private final SpringApacheKafkaTemplate springApacheKafkaTemplate;


    public void send(String topic, String message) {
        springApacheKafkaTemplate.send(topic, message);
    }

    public void sendObject(String topic, ObjectDto message) {
        springApacheKafkaTemplate.sendObject(topic, message);
    }
}
