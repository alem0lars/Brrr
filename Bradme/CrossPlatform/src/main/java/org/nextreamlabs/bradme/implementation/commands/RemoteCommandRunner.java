package org.nextreamlabs.bradme.implementation.commands;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Signal;
import org.nextreamlabs.bradme.interfaces.models.commands.IRemoteCommand;
import org.nextreamlabs.bradme.interfaces.commands.IRemoteCommandRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RemoteCommandRunner
    extends CommandRunner<IRemoteCommand>
    implements IRemoteCommandRunner {

  // { Fields (+Accessors)

  private SSHClient sshClient;
  protected void setSSHClient(SSHClient sshClient) {
    this.sshClient = sshClient;
  }
  protected SSHClient getSSHClient() {
    return this.sshClient;
  }

  private Session.Command currentSSHCommand;
  protected void setCurrentSSHCommand(Session.Command currentSSHCommand) {
    this.currentSSHCommand = currentSSHCommand;
  }
  protected Session.Command getCurrentSSHCommand() {
    return this.currentSSHCommand;
  }

  // }

  // { Construction

  protected RemoteCommandRunner(IRemoteCommand command) throws IOException {
    super(command);
    this.initializeSSH();
  }

  public static IRemoteCommandRunner create(IRemoteCommand command) throws IOException {
    return new RemoteCommandRunner(command);
  }

  // }

  // { Initialization

  protected void initializeSSH() throws IOException {
    this.setSSHClient(new SSHClient());
    this.getSSHClient().loadKnownHosts();
    this.getSSHClient().connect(this.getCommand().hostName().getValue(), this.getCommand().port().getValue());
    this.getSSHClient().authPublickey(this.getCommand().executingUser().getValue());
    //noinspection AssignmentToNull
    this.setCurrentSSHCommand(null);
  }

  protected void ensureSSHInitialization() throws IOException {
    if (this.getSSHClient() == null || !this.getSSHClient().isConnected() || !this.getSSHClient().isAuthenticated()) {
      this.initializeSSH();
    }
  }

  // }

  // { IRemoteCommandRunner implementation

  @Override
  public void start() throws IOException {
    this.ensureSSHInitialization();
    try (Session sshSession = this.getSSHClient().startSession()) {
      this.setCurrentSSHCommand(sshSession.exec(this.getCommand().commandString().getValue()));
    }
  }

  @Override
  public void stop() throws IOException {
    if (this.getSSHClient() != null && this.getSSHClient().isConnected() && this.getCurrentSSHCommand() != null) {
      this.getCurrentSSHCommand().signal(Signal.TERM);
      this.getCurrentSSHCommand().join(4, TimeUnit.SECONDS);
      if (this.getCurrentSSHCommand().isOpen()) {
        this.getCurrentSSHCommand().signal(Signal.KILL);
      }
      //noinspection AssignmentToNull
      this.setCurrentSSHCommand(null);
    }
  }

  public void releaseResources() throws IOException {
    if (this.getSSHClient() != null && this.getSSHClient().isConnected()) {
      this.getSSHClient().disconnect();
    }
  }

  // }

}
