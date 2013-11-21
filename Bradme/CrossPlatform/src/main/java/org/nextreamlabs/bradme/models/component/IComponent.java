package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.models.IModel;

public interface IComponent extends IModel {

  public StringProperty name();
  public IComponentStatus status();

}
