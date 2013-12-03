package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.*;
import org.nextreamlabs.bradme.models.IModel;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;

public interface IComponent extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public ObjectProperty<IComponentStatus> currentStatus();
  public ObjectProperty<IComponentStatus> nextStatus();
  public ListProperty<ObjectProperty<IComponentStatus>> statuses();
  public MapProperty<ObjectProperty<IComponent>, ObjectProperty<IComponentStatus>> dependencies();
  public BooleanProperty areDependenciesSatisfied();

  public void computeAreDependenciesSatisfied();
  public void execute();

}
