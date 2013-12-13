package org.nextreamlabs.bradme.interfaces.models;

import javafx.beans.property.ObjectProperty;

public interface IStatusWithCommand extends IStatus {

  public ObjectProperty<ICommand> command();

  public boolean equalsToStatus(IStatus status);

}
