package org.example.UI;

import org.example.MessagePkg.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class MessageGenerator {
    private final Map<String, Function<List<String>, Message>> messageMap = new HashMap<>();

    public MessageGenerator() {
        messageMap.put("aumenta forza cannone", this::createIncreaseCannonPowerMessage);
        messageMap.put("join lobby", this::createJoinLobbyMessage);
    }

    public Optional<Message> generate(String command, List<String> args) {
        Function<List<String>, Message> generator = messageMap.get(command.toLowerCase());
        return generator != null ? Optional.of(generator.apply(args)) : Optional.empty();
    }

    public Message createIncreaseCannonPowerMessage(List<String> args) {
        // implementazione
    }

    public Message createJoinLobbyMessage(List<String> args) {
        // implementazione
    }
}
