package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusWithCommandDescriptor;
import org.nextreamlabs.bradme.implementation.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.implementation.models.Component;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIComponent;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIStatus;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIStatusWithCommand;
import org.nextreamlabs.bradme.interfaces.models.IComponent;
import org.nextreamlabs.bradme.interfaces.models.IStatus;
import org.nextreamlabs.bradme.interfaces.models.IStatusWithCommand;
import org.nextreamlabs.bradme.implementation.support.L10N;
import org.nextreamlabs.bradme.implementation.support.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FactoryForIComponent
    extends FactoryForIModelWithCache<IComponentDescriptor, IComponent>
    implements IFactoryForIComponent {

  // { Fields (+Accessors)

  private final IFactoryForIStatus factoryForIStatus;
  protected IFactoryForIStatus getFactoryForIStatus() {
    return this.factoryForIStatus;
  }

  // }

  // { Construction

  protected FactoryForIComponent(IFactoryForIStatus factoryForIStatus) {
    super();
    this.factoryForIStatus = factoryForIStatus;
    this.initializeCache();
  }

  public static IFactoryForIComponent create(IFactoryForIStatus factoryForIStatus) {
    return new FactoryForIComponent(factoryForIStatus);
  }

  // }

  // { FactoryForIModelWithCache implementation

  @Override
  protected IComponent createElement(IComponentDescriptor componentDescriptor) {
    IFactoryForIStatusWithCommand factoryForIStatusWithCommand = FactoryForIStatusWithCommand.create(FactoryForICommand.create()); // TODO: Instantiate the right factory

    Map<IComponent, IStatus> dependencies = this.createDependencies(componentDescriptor.getDependencies());
    Collection<IStatusWithCommand> statuses = new LinkedList<>();

    for (IStatusWithCommandDescriptor statusWithCommandDescriptor : componentDescriptor.getStatuses()) {
      statuses.add(factoryForIStatusWithCommand.getNew(statusWithCommandDescriptor));
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
      this.getCache().put(availableComponentDescriptor, component);
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
      IStatus componentStatus = this.getFactoryForIStatus().getNew(statusDescriptor);
      dependencies.put(component, componentStatus);
    }

    return dependencies;
  }

  // }

}
