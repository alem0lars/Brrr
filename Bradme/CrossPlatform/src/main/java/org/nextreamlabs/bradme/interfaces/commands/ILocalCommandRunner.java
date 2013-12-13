package org.nextreamlabs.bradme.interfaces.commands;

public interface ILocalCommandRunner extends ICommandRunner {

  public void waitTermination() throws InterruptedException;

}
