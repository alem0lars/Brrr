package org.nextreamlabs.bradme.models.status;

import javafx.beans.property.StringProperty;

public interface IStatusWithCommand extends IStatus {
  public StringProperty getCommandOnEnter();
  public boolean equalsToStatus(IStatus status);
}
