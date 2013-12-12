package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.IStatusWithCommandDescriptor;
import org.nextreamlabs.bradme.models.status.IStatusWithCommand;
import org.nextreamlabs.bradme.models.status.StatusWithCommand;
import org.nextreamlabs.bradme.support.L10N;

public class ComponentStatusesFactory
    extends ModelFactory<IStatusWithCommandDescriptor, IStatusWithCommand>
    implements IComponentStatusesFactory {

  // { Construction

  protected ComponentStatusesFactory() {
    super();
  }

  public static IComponentStatusesFactory create() {
    return new ComponentStatusesFactory();
  }

  // }

  // { ModelFactoryWithCache implementation

  @Override
  protected IStatusWithCommand createElement(IStatusWithCommandDescriptor statusWithCommandDescriptor) {
    return StatusWithCommand.create(
        L10N.t(statusWithCommandDescriptor.getNameKey()),
        L10N.t(statusWithCommandDescriptor.getDescKey()),
        L10N.t(statusWithCommandDescriptor.getActionNameKey()),
        statusWithCommandDescriptor.getCommandOnEnter(),
        statusWithCommandDescriptor.getWorkDir());
  }

  // }

}
