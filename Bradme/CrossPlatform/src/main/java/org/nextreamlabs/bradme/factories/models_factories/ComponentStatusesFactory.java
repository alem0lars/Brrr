package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.models.component_status.ComponentStatus;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.support.L10N;

public class ComponentStatusesFactory
    extends ModelFactory<ComponentStatusDescriptor, IComponentStatus>
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
  protected IComponentStatus createElement(ComponentStatusDescriptor componentStatusDescriptor) {
    return ComponentStatus.create(
        L10N.t(componentStatusDescriptor.getNameKey()),
        L10N.t(componentStatusDescriptor.getDescKey()),
        L10N.t(componentStatusDescriptor.getActionNameKey()),
        componentStatusDescriptor.commandOnEnter);
  }

  // }

}
