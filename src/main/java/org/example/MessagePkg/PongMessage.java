package org.example.MessagePkg;

public class PongMessage extends Message {
    private final String key;

    public PongMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public void handle() {
        // Anche questa può non fare nulla
    }
}
