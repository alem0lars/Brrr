package org.nextreamlabs.bradme.dal.repositories;

import org.nextreamlabs.bradme.dal.descriptors.IStatusDescriptor;

public interface IAvailableStatusesRepository extends IRepository<IStatusDescriptor> {
  public IStatusDescriptor findByNameKey(String statusNameKey);
}
