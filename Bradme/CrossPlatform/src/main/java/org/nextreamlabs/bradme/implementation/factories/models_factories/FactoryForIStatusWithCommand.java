package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusWithCommandDescriptor;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForICommand;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIStatusWithCommand;
import org.nextreamlabs.bradme.interfaces.models.IStatusWithCommand;
import org.nextreamlabs.bradme.implementation.models.StatusWithCommand;
import org.nextreamlabs.bradme.implementation.support.L10N;

public class FactoryForIStatusWithCommand
    extends FactoryForIModel<IStatusWithCommandDescriptor, IStatusWithCommand>
    implements IFactoryForIStatusWithCommand {

  // { Fields (+Accessors)

  private final IFactoryForICommand factoryForICommand;
  protected IFactoryForICommand getFactoryForICommand() {
    return this.factoryForICommand;
  }

  // }

  // { Construction

  protected FactoryForIStatusWithCommand(IFactoryForICommand factoryForICommand) {
    super();
    this.factoryForICommand = factoryForICommand;
  }

  public static IFactoryForIStatusWithCommand create(IFactoryForICommand factoryForICommand) {
    return new FactoryForIStatusWithCommand(factoryForICommand);
  }

  // }

  // { IFactoryForIStatusWithCommand implementation

  @Override
  protected IStatusWithCommand createElement(IStatusWithCommandDescriptor statusWithCommandDescriptor) {
    return StatusWithCommand.create(
        L10N.t(statusWithCommandDescriptor.getNameKey()),
        L10N.t(statusWithCommandDescriptor.getDescKey()),
        L10N.t(statusWithCommandDescriptor.getActionNameKey()),
        this.getFactoryForICommand().getNew(statusWithCommandDescriptor.getCommandOnEnter()));
  }

  // }

}
