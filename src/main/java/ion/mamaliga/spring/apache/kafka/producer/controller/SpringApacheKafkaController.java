package ion.mamaliga.spring.apache.kafka.producer.controller;

import ion.mamaliga.spring.apache.kafka.producer.dto.ObjectDto;
import ion.mamaliga.spring.apache.kafka.producer.service.SpringApacheKafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringApacheKafkaController {

    private final SpringApacheKafkaProducerService producerService;

    @Autowired
    public SpringApacheKafkaController(SpringApacheKafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/sendString")
    public ResponseEntity<String> sendString(@RequestParam("message") String message) {
        producerService.send("my-topic1", message);
        return ResponseEntity.ok("String message published to Kafka topic");
    }

    @PostMapping("/sendObject")
    public ResponseEntity<String> sendObject(@RequestBody ObjectDto message) {
        producerService.sendObject("my-topic2", message);
        return ResponseEntity.ok("Object message published to Kafka topic");
    }
}
