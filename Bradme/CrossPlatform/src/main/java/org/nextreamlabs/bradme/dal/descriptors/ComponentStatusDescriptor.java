package org.nextreamlabs.bradme.dal.descriptors;

public class ComponentStatusDescriptor extends StatusDescriptor {

  public String commandOnEnter;

  // { Construction

  protected ComponentStatusDescriptor(String id, String commandOnEnter) {
    super(id);
    this.commandOnEnter = commandOnEnter;
  }

  public static ComponentStatusDescriptor create(String id, String commandOnEnter) {
    return new ComponentStatusDescriptor(id, commandOnEnter);
  }

  // }

  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof ComponentStatusDescriptor)) {
      return false;
    }

    ComponentStatusDescriptor otherComponentStatusDescriptor = (ComponentStatusDescriptor) o;

    return super.equals(o)
        && this.commandOnEnter.equals(otherComponentStatusDescriptor.commandOnEnter);
  }

  private volatile int hashCode;
  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 17;
      result = 31 * result + this.id.hashCode();
      result = 31 * result + this.commandOnEnter.hashCode();
      hashCode = result;
    }
    return result;
  }

}
