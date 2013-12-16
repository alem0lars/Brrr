package org.nextreamlabs.bradme.interfaces.models.commands;

import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.commands.ICommandRunner;
import org.nextreamlabs.bradme.interfaces.models.IModel;

public interface ICommand extends IModel {

  public ListProperty<StringProperty> commandArgs();
  public StringProperty workDir();

  public ICommandRunner runner();
  public String commandString();

}
