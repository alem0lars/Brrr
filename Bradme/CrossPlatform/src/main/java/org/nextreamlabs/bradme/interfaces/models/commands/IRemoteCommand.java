package org.nextreamlabs.bradme.interfaces.models.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface IRemoteCommand extends ICommand {

  public StringProperty hostName();
  public IntegerProperty port();
  public StringProperty executingUser();

}
