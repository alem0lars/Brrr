package org.nextreamlabs.bradme.implementation.commands;

import org.nextreamlabs.bradme.interfaces.models.commands.ILocalCommand;
import org.nextreamlabs.bradme.interfaces.commands.ILocalCommandRunner;

import java.io.File;
import java.io.IOException;

public class LocalCommandRunner
    extends CommandRunner<ILocalCommand>
    implements ILocalCommandRunner {

  // { Fields (+Accessors)

  private final ProcessBuilder processBuilder;
  protected ProcessBuilder getProcessBuilder() {
    return this.processBuilder;
  }

  private Process process;
  protected void setProcess(Process process) {
    this.process = process;
  }
  protected Process getProcess() {
    return this.process;
  }

  // }

  // { Creation

  protected LocalCommandRunner(ILocalCommand command) {
    super(command);
    this.processBuilder = new ProcessBuilder(this.getCommand().commandString().getValue());
    this.getProcessBuilder().directory(new File(this.getCommand().workDir().getValue()));
    this.setProcess(null);
  }

  public static ILocalCommandRunner create(ILocalCommand command) {
    return new LocalCommandRunner(command);
  }

  // }

  // { ILocalCommandRunner implementation

  @Override
  public void start() throws IOException {
    this.setProcess(this.getProcessBuilder().start());
  }

  @Override
  public void stop() throws IOException {
    if (this.getProcess() != null) {
      this.getProcess().destroy();
    }
  }

  public void waitTermination() throws InterruptedException {
    if (this.getProcess() != null) {
      this.getProcess().waitFor();
    }
  }

  // }

}
