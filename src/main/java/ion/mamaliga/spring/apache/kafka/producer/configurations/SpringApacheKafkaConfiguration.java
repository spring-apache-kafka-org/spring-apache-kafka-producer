package ion.mamaliga.spring.apache.kafka.producer.configurations;

import ion.mamaliga.spring.apache.kafka.producer.component.ObjectDtoSerializer;
import ion.mamaliga.spring.apache.kafka.producer.dto.ObjectDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringApacheKafkaConfiguration {

    private final String bootstrapServers;
    private final KafkaProperties kafkaProperties;

    public SpringApacheKafkaConfiguration(final @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
                                          final KafkaProperties kafkaProperties) {
        this.bootstrapServers = bootstrapServers;
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final Map<String, Object> configs = getConfigs();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(final ProducerFactory<String, String> producerFactory,
                                                       final ObjectProvider<RecordMessageConverter> messageConverter) {
        final KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
        kafkaTemplate.setDefaultTopic(this.kafkaProperties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<String, ObjectDto> producerFactoryForObjectDto(final ObjectDtoSerializer<ObjectDto> objectDtoSerializer) {
        return new DefaultKafkaProducerFactory<>(getConfigs(), new StringSerializer(), objectDtoSerializer);
    }

    @Bean
    public KafkaTemplate<String, ObjectDto> kafkaTemplateForObjectDto(final ProducerFactory<String, ObjectDto> producerFactoryForObjectDto,
                                                                      final ObjectProvider<RecordMessageConverter> messageConverter) {
        final KafkaTemplate<String, ObjectDto> kafkaTemplate = new KafkaTemplate<>(producerFactoryForObjectDto);
        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
        kafkaTemplate.setDefaultTopic(this.kafkaProperties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }

    private Map<String, Object> getConfigs() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return configs;
    }
}
