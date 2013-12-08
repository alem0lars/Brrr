package org.nextreamlabs.bradme.models.component_status;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.status.IStatus;

public interface IComponentStatus extends IStatus {
  public StringProperty getCommandOnEnter();
}
