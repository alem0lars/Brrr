package org.nextreamlabs.bradme.interfaces.models;

import javafx.beans.property.*;

public interface IComponent extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public ObjectProperty<IStatusWithCommand> currentStatus();
  public ObjectProperty<IStatusWithCommand> nextStatus();
  public ListProperty<ObjectProperty<IStatusWithCommand>> statuses();
  public MapProperty<ObjectProperty<IComponent>, ObjectProperty<IStatus>> dependencies();
  public BooleanProperty areDependenciesSatisfied();

  public void computeAreDependenciesSatisfied();
  public void execute();

}
