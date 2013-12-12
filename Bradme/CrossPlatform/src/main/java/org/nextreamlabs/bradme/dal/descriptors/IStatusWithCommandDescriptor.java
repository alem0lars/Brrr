package org.nextreamlabs.bradme.dal.descriptors;

public interface IStatusWithCommandDescriptor extends IStatusDescriptor {
  public String getWorkDir();
  public String getCommandOnEnter();
}
