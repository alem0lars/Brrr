package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

import java.util.Collection;

public class AvailableComponentsRepository
    extends Repository<ComponentDescriptor>
    implements IAvailableComponentsRepository {

  private static IAvailableComponentsRepository instance;

  // { Construction

  protected AvailableComponentsRepository(Collection<ComponentDescriptor> componentDescriptors) {
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
