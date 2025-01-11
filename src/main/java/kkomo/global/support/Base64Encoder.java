package kkomo.global.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Base64Encoder {

    private final ObjectMapper objectMapper;

    public String encode(final Object object) {
        try {
            final String converted = objectMapper.writeValueAsString(object);
            return Base64.getEncoder().encodeToString(converted.getBytes());
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException("Failed to encode:", exception);
        }
    }
}
