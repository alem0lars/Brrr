package org.nextreamlabs.bradme.dal.descriptors;

import org.nextreamlabs.bradme.support.L10N;

public class ComponentStatusDescriptor {

  public String id;
  public String actionNameKey;
  public String nameKey;
  public String descKey;

  // { Construction

  private ComponentStatusDescriptor(String id) {
    this.id = id;
    this.nameKey = String.format("%s_name", id);
    this.descKey = String.format("%s_desc", id);
    this.actionNameKey = String.format("%s_action_name", id);
  }

  public static ComponentStatusDescriptor create(String id) {
    return new ComponentStatusDescriptor(id);
  }

  // }

  @Override
  public String toString() {
    return L10N.t(this.nameKey);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ComponentStatusDescriptor)) {
      return false;
    }

    ComponentStatusDescriptor otherComponentStatusDescriptor = (ComponentStatusDescriptor) o;

    return this.id.equals(otherComponentStatusDescriptor.id);
  }

  private volatile int hashCode;
  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 17;
      result = 31 * result + this.id.hashCode();
      hashCode = result;
    }
    return result;
  }

}
