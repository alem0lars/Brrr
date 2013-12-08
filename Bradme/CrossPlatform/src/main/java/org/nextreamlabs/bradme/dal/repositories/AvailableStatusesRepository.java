package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.StatusDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

import java.util.Collection;

public class AvailableStatusesRepository
    extends Repository<StatusDescriptor>
    implements IAvailableStatusesRepository {

  private static IAvailableStatusesRepository instance;

  // { Construction

  private AvailableStatusesRepository(Collection<StatusDescriptor> statusDescriptors) {
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

  public StatusDescriptor findByNameKey(String statusNameKey) {
    for (StatusDescriptor descriptor : this.values()) {
      if (descriptor.nameKey.equals(statusNameKey)) {
        return descriptor;
      }
    }
    return null;
  }

}
