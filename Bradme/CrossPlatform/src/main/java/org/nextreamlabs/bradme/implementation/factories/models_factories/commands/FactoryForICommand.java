package org.nextreamlabs.bradme.implementation.factories.models_factories.commands;

import org.nextreamlabs.bradme.implementation.exceptions.CannotCreateModelException;
import org.nextreamlabs.bradme.implementation.factories.models_factories.FactoryForIModel;
import org.nextreamlabs.bradme.implementation.models.commands.LocalCommand;
import org.nextreamlabs.bradme.implementation.models.commands.RemoteCommand;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ILocalCommandDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.IRemoteCommandDescriptor;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.commands.IFactoryForICommand;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;

public class FactoryForICommand
  extends FactoryForIModel<ICommandDescriptor, ICommand>
  implements IFactoryForICommand {

  // { Construction

  protected FactoryForICommand() {
    super();
  }

  public static IFactoryForICommand create() {
    return new FactoryForICommand();
  }

  // }

  // { IFactoryForICommand implementation

  @Override
  protected ICommand createElement(ICommandDescriptor commandDescriptor) {
    // TODO: a better way to dynamically dispatch?
    if (commandDescriptor instanceof ILocalCommandDescriptor) {
      return createElement((ILocalCommandDescriptor) commandDescriptor);
    } else if (commandDescriptor instanceof IRemoteCommandDescriptor) {
      return createElement((IRemoteCommandDescriptor) commandDescriptor);
    } else {
      throw CannotCreateModelException.create(String.format("Command descriptor not handled: '%s", commandDescriptor));
    }
  }

  protected ICommand createElement(ILocalCommandDescriptor commandDescriptor) {
    return LocalCommand.create(
        commandDescriptor.getCommand(),
        commandDescriptor.getWorkDir());
  }

  protected ICommand createElement(IRemoteCommandDescriptor commandDescriptor) {
    return RemoteCommand.create(
        commandDescriptor.getCommand(),
        commandDescriptor.getWorkDir(),
        commandDescriptor.getHost(),
        commandDescriptor.getPort(),
        commandDescriptor.getUser());
  }

  // }

}
