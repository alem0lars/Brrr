package org.nextreamlabs.bradme.command;


import java.io.File;
import java.io.IOException;

public class CommandRunner implements ICommandRunner {

  protected final ProcessBuilder processBuilder;
  protected Process process;

  // { Creation.

  protected CommandRunner(String command, File workDir) {
    processBuilder = new ProcessBuilder(command);
    processBuilder.directory(workDir);
    process = null;
  }

  public static ICommandRunner create(String command, String workDir) {
    return new CommandRunner(command, new File(workDir));
  }

  // }

  // { ICommandRunner implementation.

  @Override
  public Process runCommand() throws IOException {
    process = processBuilder.start();
    return process;
  }

  // }

}
