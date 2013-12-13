package org.nextreamlabs.bradme.implementation.exceptions;

import org.nextreamlabs.bradme.interfaces.models.IStatus;

public class CannotChangeComponentStatusException extends RuntimeException implements IException {

  protected final String reason;
  protected final IStatus nextStatus;
  protected final IStatus currentStatus;

  // { Construction

  private CannotChangeComponentStatusException(IStatus currentStatus, IStatus nextStatus, String reason) {
    this.currentStatus = currentStatus;
    this.nextStatus = nextStatus;
    this.reason = reason;
  }

  public static CannotChangeComponentStatusException create(IStatus currentStatus, IStatus nextStatus, String reason) {
    return new CannotChangeComponentStatusException(currentStatus, nextStatus, reason);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return String.format("CannotChangeComponentStatusException:<reason=%s>", this.reason);
  }

  @Override
  public String getMessage() {
    return String.format("Cannot change status from %s to %s. Reason: %s", currentStatus.name().getValue(), nextStatus.name().getValue(), this.reason);
  }

  // }

}
