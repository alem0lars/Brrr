package org.nextreamlabs.bradme.dal;

import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface IDALLoader {
  public void load() throws FileNotFoundException;
  public Collection<ComponentStatusDescriptor> queryComponentStatusDescriptors();
  public Collection<ComponentDescriptor> queryComponentDescriptors();
}
