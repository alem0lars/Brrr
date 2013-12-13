package org.nextreamlabs.bradme.interfaces.commands;


import java.io.IOException;

public interface ICommandRunner {

  public void start() throws IOException;
  public void stop() throws IOException;

}
