package org.nextreamlabs.bradme.implementation.dal.repositories;

import org.nextreamlabs.bradme.interfaces.dal.IDALLoader;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.interfaces.dal.repositories.IAvailableComponentsRepository;

import java.util.Collection;

public class AvailableComponentsRepository
    extends Repository<IComponentDescriptor>
    implements IAvailableComponentsRepository {

  /**
   * Hold the instance for the repository of the available components.
   */
  private static IAvailableComponentsRepository instance;

  // { Construction

  protected AvailableComponentsRepository(Collection<IComponentDescriptor> componentDescriptors) {
    super(componentDescriptors);
  }

  public static void configureRepository(IDALLoader dalLoader) {
    AvailableStatusesRepository.configureRepository(dalLoader);
    instance = new AvailableComponentsRepository(dalLoader.queryComponentDescriptors());
  }

  public static Boolean isConfigured() {
    return instance != null;
  }

  public static IAvailableComponentsRepository getInstance() {
    if (!isConfigured()) {
      throw InvalidConfigurationException.create("AvailableComponentsRepository instance not configured yet");
    }
    return instance;
  }

  // }

}
