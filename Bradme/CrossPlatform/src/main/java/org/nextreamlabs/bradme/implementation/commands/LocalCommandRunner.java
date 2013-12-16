package org.nextreamlabs.bradme.implementation.commands;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.implementation.support.Logging;
import org.nextreamlabs.bradme.interfaces.commands.ILocalCommandRunner;
import org.nextreamlabs.bradme.interfaces.models.commands.ILocalCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    List<StringProperty> commandArgs = this.getCommand().commandArgs().getValue();
    List<String> plainArgs = new ArrayList<String>(commandArgs.size());
    for (StringProperty argProperty : commandArgs) {
      plainArgs.add(argProperty.getValue());
    }
    this.processBuilder = new ProcessBuilder(plainArgs);

    // { TODO: TMP: stream redirections are for debug.
    File f = new File("/tmp/LocalCommandRunner_" + command.hashCode());
    f.deleteOnExit();
    this.processBuilder.redirectError(f);
    this.processBuilder.redirectOutput(f);
    // }

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
    if (!this.getCommand().commandArgs().getValue().isEmpty()) {
      this.setProcess(this.getProcessBuilder().start());
    }
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
