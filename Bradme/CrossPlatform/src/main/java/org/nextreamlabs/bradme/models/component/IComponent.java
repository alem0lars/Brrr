package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.*;
import org.nextreamlabs.bradme.models.IModel;
import org.nextreamlabs.bradme.models.status.IStatusWithCommand;
import org.nextreamlabs.bradme.models.status.IStatus;

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
