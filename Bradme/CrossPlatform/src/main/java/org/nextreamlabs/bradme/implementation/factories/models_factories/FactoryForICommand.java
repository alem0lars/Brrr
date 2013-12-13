package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForICommand;
import org.nextreamlabs.bradme.interfaces.models.ICommand;

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
    return null; // TODO
  }

  // }

}
