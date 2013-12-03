package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentStatusesRepository;
import org.nextreamlabs.bradme.models.component_status.ComponentStatus;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.support.L10N;

public class ComponentStatusesFactory
    extends ModelFactoryWithCache<ComponentStatusDescriptor, IComponentStatus>
    implements IModelFactoryWithCache<ComponentStatusDescriptor, IComponentStatus> {

  // { Construction

  protected ComponentStatusesFactory() {
    super();
    this.initializeCache();
  }

  public static ComponentStatusesFactory create() {
    return new ComponentStatusesFactory();
  }

  // }

  // { ModelFactoryWithCache implementation

  @Override
  protected IComponentStatus createElement(ComponentStatusDescriptor componentStatusDescriptor) {
    return ComponentStatus.create(
        L10N.t(componentStatusDescriptor.nameKey),
        L10N.t(componentStatusDescriptor.descKey),
        L10N.t(componentStatusDescriptor.actionNameKey));
  }

  @Override
  protected void initializeCache() {
    for (ComponentStatusDescriptor statusDescriptor : AvailableComponentStatusesRepository.getInstance().values()) {
      this.cache.put(statusDescriptor, this.createElement(statusDescriptor));
    }
  }

  // }

}
