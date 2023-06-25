package tgbot.bot.statemachine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class Callback {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @JsonProperty("op")
    private final String operation;

    @JsonProperty("id")
    private final Long resourceId;

    public static Callback of(String operation) {
        return new Callback(operation, null);
    }

    public static Callback of(String operation, long resourceId) {
        return new Callback(operation, resourceId);
    }

    @SneakyThrows
    public static Callback deserialize(String source) {
        return OBJECT_MAPPER.readValue(source, Callback.class);
    }

    @SneakyThrows
    public String serialize() {
        var output = OBJECT_MAPPER.writeValueAsString(this);
        if (output.length() > 64) {
            throw new RuntimeException("Serialized Callback exceeds length of 64: " + output);
        }
        return output;
    }
}
