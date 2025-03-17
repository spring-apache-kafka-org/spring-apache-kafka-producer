package ion.mamaliga.spring.apache.kafka.producer.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The Common Object serializer.
 */
@Slf4j
@Component
public class ObjectDtoSerializer<T> implements Serializer<T> {

    private final ObjectMapper jsonObjectMapper;

    public ObjectDtoSerializer(@Qualifier("jsonObjectMapper") ObjectMapper jsonObjectMapper) {
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        log.debug("Overridden blank configure() method was invoked");
    }

    @Override
    public byte[] serialize(String topic, T data) {
        byte[] retVal = null;
        try {
            retVal = jsonObjectMapper.writeValueAsString(data).getBytes();
        } catch (Exception exception) {
            log.error("Error in deserializing bytes, ERROR=[]", exception);
        }
        return retVal;
    }

    @Override
    public void close() {
        log.debug("Overridden blank close() method was invoked");
    }
}
