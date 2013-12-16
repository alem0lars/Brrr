package org.nextreamlabs.bradme.implementation.dal.repositories;

import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.interfaces.dal.IDALLoader;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.repositories.IAvailableStatusesRepository;

import java.util.Collection;

public class AvailableStatusesRepository
    extends Repository<IStatusDescriptor>
    implements IAvailableStatusesRepository {

  /**
   * Hold the instance for the repository of the available statuses.
   */
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

  // { IAvailableStatusesRepository implementation

  public IStatusDescriptor findByNameKey(String statusNameKey) {
    for (IStatusDescriptor descriptor : this.values()) {
      if (descriptor.getNameKey().equals(statusNameKey)) {
        return descriptor;
      }
    }
    return null;
  }

  // }

}
