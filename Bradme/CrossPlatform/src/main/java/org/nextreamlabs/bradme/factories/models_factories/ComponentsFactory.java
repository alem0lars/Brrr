package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.models.component.Component;
import org.nextreamlabs.bradme.models.component.IComponent;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.support.L10N;
import org.nextreamlabs.bradme.support.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ComponentsFactory
    extends ModelFactoryWithCache<ComponentDescriptor, IComponent>
    implements IModelFactoryWithCache<ComponentDescriptor, IComponent> {

  private final ComponentStatusesFactory componentStatusesFactory;

  // { Construction

  protected ComponentsFactory(ComponentStatusesFactory componentStatusesFactory) {
    super();
    this.componentStatusesFactory = componentStatusesFactory;
    this.initializeCache();
  }

  public static ComponentsFactory create(ComponentStatusesFactory componentStatusesFactory) {
    return new ComponentsFactory(componentStatusesFactory);
  }

  // }

  // { ModelFactoryWithCache implementation

  @Override
  protected IComponent createElement(ComponentDescriptor componentDescriptor) {
    Map<IComponent, IComponentStatus> dependencies = this.createDependencies(componentDescriptor.dependencies);
    Collection<IComponentStatus> statuses = new LinkedList<>();
    for (ComponentStatusDescriptor componentStatusDescriptor : componentDescriptor.statuses) {
      statuses.add(this.componentStatusesFactory.get(componentStatusDescriptor));
    }
    return Component.create(
        L10N.t(componentDescriptor.nameKey),
        L10N.t(componentDescriptor.descKey),
        new LinkedList<>(statuses),
        dependencies);
  }

  @Override
  protected void initializeCache() {
    for (ComponentDescriptor availableComponentDescriptor : AvailableComponentsRepository.getInstance().values()) {
      IComponent component = this.createElement(availableComponentDescriptor);
      Logging.debug(String.format("Caching the component: %s", component.toString()));
      this.cache.put(availableComponentDescriptor, component);
    }
  }

  // }

  // { Utilities

  protected Map<IComponent, IComponentStatus> createDependencies(Map<ComponentDescriptor, ComponentStatusDescriptor> dependenciesDescriptor) {
    Map<IComponent, IComponentStatus> dependencies = new HashMap<>();

    for (Map.Entry<ComponentDescriptor, ComponentStatusDescriptor> entry : dependenciesDescriptor.entrySet()) {
      ComponentDescriptor componentDescriptor = entry.getKey();
      ComponentStatusDescriptor statusDescriptor = entry.getValue();
      IComponent component = this.get(componentDescriptor);
      IComponentStatus componentStatus = this.componentStatusesFactory.get(statusDescriptor);
      dependencies.put(component, componentStatus);
    }

    return dependencies;
  }

  // }

}
