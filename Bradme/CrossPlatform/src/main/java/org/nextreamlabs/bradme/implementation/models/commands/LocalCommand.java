package org.nextreamlabs.bradme.implementation.models.commands;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.commands.ILocalCommand;

public class LocalCommand extends Command implements ILocalCommand {

  // { Construction

  protected LocalCommand(StringProperty commandString, StringProperty workDir) {
    super(commandString, workDir);
  }

  public static ILocalCommand create(String commandString, String workDir) {
    return new LocalCommand(new SimpleStringProperty(commandString), new SimpleStringProperty(workDir));
  }

  // }

}
