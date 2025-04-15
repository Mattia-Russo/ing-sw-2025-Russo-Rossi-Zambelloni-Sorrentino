package org.example.ServerPkg.Model.Exceptions;

public class UnderloadedCapacityException extends RuntimeException {
  public UnderloadedCapacityException(String message) {
    super(message);
  }
}
