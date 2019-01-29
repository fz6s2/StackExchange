package ru.inp.stackexchange.format;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * UNIX time deserializer for JSON parser.
 */
public class JSONUnixTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            Instant instant = Instant.ofEpochSecond(p.getLongValue());
            ZoneId zoneId = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zoneId);
        } catch (Exception e) {
            return null;
        }
    }
}
