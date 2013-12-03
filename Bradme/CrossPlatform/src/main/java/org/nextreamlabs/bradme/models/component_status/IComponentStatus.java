package org.nextreamlabs.bradme.models.component_status;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.IModel;

public interface IComponentStatus extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public StringProperty actionName();
  public String getPrettyName();

}
