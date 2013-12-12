package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

import java.util.Collection;

public class AvailableComponentsRepository
    extends Repository<IComponentDescriptor>
    implements IAvailableComponentsRepository {

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
