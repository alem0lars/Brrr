package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.IStatusWithCommandDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.models.component.Component;
import org.nextreamlabs.bradme.models.component.IComponent;
import org.nextreamlabs.bradme.models.status.IStatus;
import org.nextreamlabs.bradme.models.status.IStatusWithCommand;
import org.nextreamlabs.bradme.support.L10N;
import org.nextreamlabs.bradme.support.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ComponentsFactory
    extends ModelFactoryWithCache<IComponentDescriptor, IComponent>
    implements IComponentsFactory {

  private final IStatusesFactory statusesFactory;

  // { Construction

  protected ComponentsFactory(IStatusesFactory statusesFactory) {
    super();
    this.statusesFactory = statusesFactory;
    this.initializeCache();
  }

  public static IComponentsFactory create(IStatusesFactory statusesFactory) {
    return new ComponentsFactory(statusesFactory);
  }

  // }

  // { ModelFactoryWithCache implementation

  @Override
  protected IComponent createElement(IComponentDescriptor componentDescriptor) {
    ComponentStatusesFactory componentStatusesFactory = new ComponentStatusesFactory();

    Map<IComponent, IStatus> dependencies = this.createDependencies(componentDescriptor.getDependencies());
    Collection<IStatusWithCommand> statuses = new LinkedList<>();

    for (IStatusWithCommandDescriptor statusWithCommandDescriptor : componentDescriptor.getStatuses()) {
      statuses.add(componentStatusesFactory.createElement(statusWithCommandDescriptor));
    }

    return Component.create(
        L10N.t(componentDescriptor.getNameKey()),
        L10N.t(componentDescriptor.getDescKey()),
        new LinkedList<>(statuses),
        dependencies);
  }

  @Override
  protected void initializeCache() {
    for (IComponentDescriptor availableComponentDescriptor : AvailableComponentsRepository.getInstance().values()) {
      IComponent component = this.createElement(availableComponentDescriptor);
      Logging.debug(String.format("Caching the component: %s", component.toString()));
      this.cache.put(availableComponentDescriptor, component);
    }
  }

  // }

  // { Utilities

  protected Map<IComponent, IStatus> createDependencies(Map<IComponentDescriptor, IStatusDescriptor> dependenciesDescriptor) {
    Map<IComponent, IStatus> dependencies = new HashMap<>();

    for (Map.Entry<IComponentDescriptor, IStatusDescriptor> entry : dependenciesDescriptor.entrySet()) {
      IComponentDescriptor componentDescriptor = entry.getKey();
      IStatusDescriptor statusDescriptor = entry.getValue();
      IComponent component = this.get(componentDescriptor);
      IStatus componentStatus = this.statusesFactory.getNew(statusDescriptor);
      dependencies.put(component, componentStatus);
    }

    return dependencies;
  }

  // }

}
