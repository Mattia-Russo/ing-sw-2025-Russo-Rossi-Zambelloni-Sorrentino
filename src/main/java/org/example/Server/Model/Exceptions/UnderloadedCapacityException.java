package org.example.Server.Model.Exceptions;

public class UnderloadedCapacityException extends RuntimeException {
  public UnderloadedCapacityException(String message) {
    super(message);
  }
}
