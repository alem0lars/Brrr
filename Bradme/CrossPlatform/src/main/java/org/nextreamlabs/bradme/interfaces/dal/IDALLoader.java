package org.nextreamlabs.bradme.interfaces.dal;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;

import java.util.Collection;

public interface IDALLoader {
  public void load();
  public Collection<IStatusDescriptor> queryStatusDescriptors();
  public Collection<IComponentDescriptor> queryComponentDescriptors();
}
