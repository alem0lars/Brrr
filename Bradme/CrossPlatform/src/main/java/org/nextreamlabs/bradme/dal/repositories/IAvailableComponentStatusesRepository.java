package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;

public interface IAvailableComponentStatusesRepository extends IRepository<ComponentStatusDescriptor> {
  public ComponentStatusDescriptor findByNameKey(String componentStatusNameKey);
}
