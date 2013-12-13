package org.nextreamlabs.bradme.interfaces.models;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.IModel;

public interface ICommand extends IModel {

  public StringProperty commandString();
  public StringProperty workDir();

}
