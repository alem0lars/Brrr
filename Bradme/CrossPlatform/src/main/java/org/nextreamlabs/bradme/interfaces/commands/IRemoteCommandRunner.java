package org.nextreamlabs.bradme.interfaces.commands;

import java.io.IOException;

public interface IRemoteCommandRunner extends ICommandRunner {

  public void releaseResources() throws IOException;

}
