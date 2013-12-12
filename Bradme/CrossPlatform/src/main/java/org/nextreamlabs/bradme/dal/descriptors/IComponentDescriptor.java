package org.nextreamlabs.bradme.dal.descriptors;

import java.util.Collection;
import java.util.Map;

public interface IComponentDescriptor {
  public String getId();
  public String getNameKey();
  public String getDescKey();
  public Collection<IStatusWithCommandDescriptor> getStatuses();
  public Map<IComponentDescriptor, IStatusDescriptor> getDependencies();
}
