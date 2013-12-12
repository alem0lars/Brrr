package org.nextreamlabs.bradme.dal.descriptors;

public class StatusWithCommandDescriptor extends StatusDescriptor implements IStatusWithCommandDescriptor {

  public String commandOnEnter;

  // { Construction

  protected StatusWithCommandDescriptor(String id, String commandOnEnter) {
    super(id);
    this.commandOnEnter = commandOnEnter;
  }

  public static IStatusWithCommandDescriptor create(String id, String commandOnEnter) {
    return new StatusWithCommandDescriptor(id, commandOnEnter);
  }

  // }

  // { IStatusWithCommandDescriptor implementation

  public String getCommandOnEnter() {
    return this.commandOnEnter;
  }

  // }

  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof StatusWithCommandDescriptor)) {
      return false;
    }

    StatusWithCommandDescriptor otherStatusDescriptor = (StatusWithCommandDescriptor) o;

    return super.equals(o)
        && this.commandOnEnter.equals(otherStatusDescriptor.commandOnEnter);
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
