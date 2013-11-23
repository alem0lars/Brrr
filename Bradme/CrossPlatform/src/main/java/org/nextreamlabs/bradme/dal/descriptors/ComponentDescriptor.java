package org.nextreamlabs.bradme.dal.descriptors;

import org.apache.commons.lang3.tuple.Pair;
import org.nextreamlabs.bradme.support.L10N;

import java.util.HashMap;
import java.util.Map;

public class ComponentDescriptor {

  public String nameKey;
  public String descKey;
  public ComponentStatusDescriptor initialStatus;
  public Map<ComponentDescriptor, ComponentStatusDescriptor> dependencies;

  // { Construction

  protected ComponentDescriptor(String nameKey, String descKey, ComponentStatusDescriptor initialStatus, Pair<ComponentDescriptor, ComponentStatusDescriptor>[] dependencies) {
    this.nameKey = nameKey;
    this.descKey = descKey;
    this.initialStatus = initialStatus;

    this.dependencies = new HashMap<>();
    for (Pair<ComponentDescriptor, ComponentStatusDescriptor> pair : dependencies) {
      this.dependencies.put(pair.getLeft(), pair.getRight());
    }
  }

  public static ComponentDescriptor create(String nameKey, String descKey, ComponentStatusDescriptor initialStatus, Pair<ComponentDescriptor, ComponentStatusDescriptor>[] dependencies) {
    return new ComponentDescriptor(nameKey, descKey, initialStatus, dependencies);
  }

  // }

  @Override
  public String toString() {
    return L10N.t(this.nameKey);
  }

}
