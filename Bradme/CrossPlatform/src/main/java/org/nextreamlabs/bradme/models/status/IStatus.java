package org.nextreamlabs.bradme.models.status;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.IModel;

public interface IStatus extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public StringProperty actionName();
  public String getPrettyName();

}
