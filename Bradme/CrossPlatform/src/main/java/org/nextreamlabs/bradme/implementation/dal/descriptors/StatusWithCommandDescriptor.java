package org.nextreamlabs.bradme.implementation.dal.descriptors;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusWithCommandDescriptor;

public class StatusWithCommandDescriptor
    extends StatusDescriptor
    implements IStatusWithCommandDescriptor {

  private final ICommandDescriptor commandOnEnter;

  // { Construction

  protected StatusWithCommandDescriptor(String id, ICommandDescriptor commandOnEnter) {
    super(id);
    this.commandOnEnter = commandOnEnter;
  }

  public static IStatusWithCommandDescriptor create(String id, ICommandDescriptor commandOnEnter) {
    return new StatusWithCommandDescriptor(id, commandOnEnter);
  }

  // }

  // { IStatusWithCommandDescriptor implementation

  public ICommandDescriptor getCommandOnEnter() {
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
        && this.getCommandOnEnter().equals(otherStatusDescriptor.getCommandOnEnter());
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
