package org.nextreamlabs.bradme.interfaces.models;

import javafx.beans.property.StringProperty;

public interface IStatus extends IModel {

  public StringProperty name();
  public StringProperty desc();
  public StringProperty actionName();
  public String getPrettyName();

}
