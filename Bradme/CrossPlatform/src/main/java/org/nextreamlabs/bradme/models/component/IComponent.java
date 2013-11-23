package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import org.nextreamlabs.bradme.models.IModel;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;

public interface IComponent extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public IComponentStatus status();
  public ObservableMap<IComponent, IComponentStatus> dependencies();
  public BooleanProperty areDependenciesSatisfied();

}
