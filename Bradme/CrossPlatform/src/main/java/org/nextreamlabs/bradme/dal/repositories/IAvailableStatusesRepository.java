package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.descriptors.StatusDescriptor;

public interface IAvailableStatusesRepository extends IRepository<StatusDescriptor> {
  public StatusDescriptor findByNameKey(String statusNameKey);
}
