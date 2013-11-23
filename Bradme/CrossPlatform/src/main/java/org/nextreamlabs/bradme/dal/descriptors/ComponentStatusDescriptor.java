package org.nextreamlabs.bradme.dal.descriptors;

import org.nextreamlabs.bradme.support.L10N;

public class ComponentStatusDescriptor {
  public String nameKey;
  public String descKey;

  // { Construction

  private ComponentStatusDescriptor(String nameKey, String descKey) {
    this.nameKey = nameKey;
    this.descKey = descKey;
  }

  public static ComponentStatusDescriptor create(String nameKey, String descKey) {
    return new ComponentStatusDescriptor(nameKey, descKey);
  }

  // }

  @Override
  public String toString() {
    return L10N.t(this.nameKey);
  }

}
