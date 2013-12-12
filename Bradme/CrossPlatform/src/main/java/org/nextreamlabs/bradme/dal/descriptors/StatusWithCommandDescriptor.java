package org.nextreamlabs.bradme.dal.descriptors;

public class StatusWithCommandDescriptor
    extends StatusDescriptor
    implements IStatusWithCommandDescriptor {

  protected String commandOnEnter;
  protected String workDir;

  // { Construction

  protected StatusWithCommandDescriptor(String id, String commandOnEnter, String workDir) {
    super(id);
    this.commandOnEnter = commandOnEnter;
    this.workDir = workDir;
  }

  public static IStatusWithCommandDescriptor create(String id, String commandOnEnter, String workDir) {
    return new StatusWithCommandDescriptor(id, commandOnEnter, workDir);
  }

  // }

  // { IStatusWithCommandDescriptor implementation

  @Override
  public String getWorkDir() {
    return this.workDir;  //To change body of implemented methods use File | Settings | File Templates.
  }

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
        && this.getCommandOnEnter().equals(otherStatusDescriptor.getCommandOnEnter())
        && this.getWorkDir().equals(otherStatusDescriptor.getWorkDir());
  }

  private volatile int hashCode;
  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 17;
      result = 31 * result + this.id.hashCode();
      result = 31 * result + this.commandOnEnter.hashCode();
      result = 31 * result + this.workDir.hashCode();
      hashCode = result;
    }
    return result;
  }

}
