package org.example.Model.Exceptions;

public class UnderloadedCapacityException extends RuntimeException {
  public UnderloadedCapacityException(String message) {
    super(message);
  }
}
