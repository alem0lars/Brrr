package org.nextreamlabs.bradme.dal.descriptors;

import org.nextreamlabs.bradme.support.L10N;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ComponentDescriptor {

  public String id;
  public String nameKey;
  public String descKey;
  public Collection<ComponentStatusDescriptor> statuses;
  public Map<ComponentDescriptor, ComponentStatusDescriptor> dependencies;

  // { Construction

  protected ComponentDescriptor(String id, Collection<ComponentStatusDescriptor> statuses, Map<ComponentDescriptor, ComponentStatusDescriptor> dependencies) {
    this.id = id;
    this.nameKey = String.format("%s_name", id);
    this.descKey = String.format("%s_desc", id);
    this.statuses = new LinkedList<>(statuses);
    this.dependencies = new HashMap<>(dependencies);
  }

  public static ComponentDescriptor create(String id, Collection<ComponentStatusDescriptor> statuses, Map<ComponentDescriptor, ComponentStatusDescriptor> dependencies) {
    return new ComponentDescriptor(id, statuses, dependencies);
  }

  // }

  @Override
  public String toString() {
    return L10N.t(this.nameKey);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ComponentDescriptor)) {
      return false;
    }

    ComponentDescriptor otherComponentDescriptor = (ComponentDescriptor) o;

    return this.id.equals(otherComponentDescriptor.id)
        && this.statuses.equals(otherComponentDescriptor.statuses)
        && this.dependencies.equals(otherComponentDescriptor.dependencies);
  }

  private volatile int hashCode;
  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 17;
      result = 31 * result + this.id.hashCode();
      result = 31 * result + this.statuses.hashCode();
      result = 31 * result + this.dependencies.hashCode();
      hashCode = result;
    }
    return result;
  }

}
