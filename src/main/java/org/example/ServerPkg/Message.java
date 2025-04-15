package org.example.ServerPkg;
import java.io.Serializable;
import java.util.Map;

public record Message(String title, Map<String, Object> arguments) implements Serializable {}

