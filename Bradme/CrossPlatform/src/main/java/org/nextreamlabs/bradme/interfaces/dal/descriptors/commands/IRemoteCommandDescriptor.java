package org.nextreamlabs.bradme.interfaces.dal.descriptors.commands;

public interface IRemoteCommandDescriptor extends ICommandDescriptor {

  public String getUser();
  public String getHost();
  public Integer getPort();

}
