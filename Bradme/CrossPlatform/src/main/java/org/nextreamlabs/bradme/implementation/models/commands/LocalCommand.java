package org.nextreamlabs.bradme.implementation.models.commands;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nextreamlabs.bradme.implementation.commands.LocalCommandRunner;
import org.nextreamlabs.bradme.interfaces.commands.ICommandRunner;
import org.nextreamlabs.bradme.interfaces.commands.ILocalCommandRunner;
import org.nextreamlabs.bradme.interfaces.models.commands.ILocalCommand;

import java.util.List;

public class LocalCommand extends Command implements ILocalCommand {

  private final ILocalCommandRunner runner;

  // { Construction

  protected LocalCommand(ListProperty<StringProperty> commandArgs, StringProperty workDir) {
    super(commandArgs, workDir);
    this.runner = LocalCommandRunner.create(this);
  }

  public static ILocalCommand create(List<String> commandArgs, String workDir) {
    // TODO: factor the following transform (into `Command` ?).
    // Transform `commandArgs` into an `ObservableList`.
    ObservableList<StringProperty> obsCommandArgs = FXCollections.observableArrayList();
    for (String arg : commandArgs) {
      obsCommandArgs.add(new SimpleStringProperty(arg));
    }

    return new LocalCommand(new SimpleListProperty<>(obsCommandArgs), new SimpleStringProperty(workDir));
  }

  // }

  // { ICommand implementation.

  @Override
  public ICommandRunner runner() {
    return this.runner;
  }

  // }

}
