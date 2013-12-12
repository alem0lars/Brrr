package org.nextreamlabs.bradme.dal;

import org.nextreamlabs.bradme.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.IStatusDescriptor;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface IDALLoader {
  public void load() throws FileNotFoundException;
  public Collection<IStatusDescriptor> queryStatusDescriptors();
  public Collection<IComponentDescriptor> queryComponentDescriptors();
}
