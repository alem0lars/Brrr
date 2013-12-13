package org.nextreamlabs.bradme.interfaces.dal.repositories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;

public interface IAvailableStatusesRepository
    extends IRepository<IStatusDescriptor> {

  public IStatusDescriptor findByNameKey(String statusNameKey);

}
