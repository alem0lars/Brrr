package org.nextreamlabs.bradme.implementation.dal.descriptors.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.IRemoteCommandDescriptor;

import java.util.List;

public class RemoteCommandDescriptor
    extends CommandDescriptor
    implements IRemoteCommandDescriptor {

  private final Integer port;
  private final String user;
  private final String host;

  // { Construction.

  protected RemoteCommandDescriptor(List<String> command, String workDir, String user, String host, Integer port) {
    super(command, workDir);
    this.port = port;
    this.user = user;
    this.host = host;
  }

  public static IRemoteCommandDescriptor create(List<String> command, String workDir, String user, String host, Integer port) {
    return new RemoteCommandDescriptor(command, workDir, user, host, port);
  }

  // }

  // { IRemoteCommandDescriptor implementation.

  @Override
  public String getUser() {
    return this.user;
  }

  @Override
  public String getHost() {
    return this.host;
  }

  @Override
  public Integer getPort() {
    return this.port;
  }

  // }

}
