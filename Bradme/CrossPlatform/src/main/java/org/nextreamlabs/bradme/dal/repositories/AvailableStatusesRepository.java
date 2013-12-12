package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

import java.util.Collection;

public class AvailableStatusesRepository
    extends Repository<IStatusDescriptor>
    implements IAvailableStatusesRepository {

  private static IAvailableStatusesRepository instance;

  // { Construction

  private AvailableStatusesRepository(Collection<IStatusDescriptor> statusDescriptors) {
    super(statusDescriptors);
  }

  public static void configureRepository(IDALLoader dalLoader) {
    instance = new AvailableStatusesRepository(dalLoader.queryStatusDescriptors());
  }

  public static Boolean isConfigured() {
    return instance != null;
  }

  public static IAvailableStatusesRepository getInstance() {
    if (!isConfigured()) {
      throw InvalidConfigurationException.create("AvailableStatusesRepository instance not configured yet");
    }
    return instance;
  }

  // }

  public IStatusDescriptor findByNameKey(String statusNameKey) {
    for (IStatusDescriptor descriptor : this.values()) {
      if (descriptor.getNameKey().equals(statusNameKey)) {
        return descriptor;
      }
    }
    return null;
  }

}
