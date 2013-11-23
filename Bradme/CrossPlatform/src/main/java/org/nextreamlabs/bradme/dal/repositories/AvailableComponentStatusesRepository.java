package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

import java.util.Collection;

public class AvailableComponentStatusesRepository
    extends Repository<ComponentStatusDescriptor>
    implements IRepository<ComponentStatusDescriptor> {

  private static AvailableComponentStatusesRepository instance;

  // { Construction

  private AvailableComponentStatusesRepository(Collection<ComponentStatusDescriptor> componentStatusDescriptors) {
    super(componentStatusDescriptors);
  }

  public static void configureRepository(IDALLoader dalLoader) {
    instance = new AvailableComponentStatusesRepository(dalLoader.queryComponentStatusDescriptors());
  }

  public static Boolean isConfigured() {
    return instance != null;
  }

  public static AvailableComponentStatusesRepository getInstance() {
    if (!isConfigured())
      throw InvalidConfigurationException.create("AvailableComponentStatusRepository instance not configured yet");
    return instance;
  }

  // }

  public ComponentStatusDescriptor findByNameKey(String componentStatusNameKey) {
    for (ComponentStatusDescriptor descriptor : this.values()) {
      if (descriptor.nameKey.equals(componentStatusNameKey)) {
        return descriptor;
      }
    }
    return null;
  }

}
