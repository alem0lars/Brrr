package org.nextreamlabs.bradme.interfaces.dal.descriptors.commands;

import java.util.List;

public interface ICommandDescriptor {

  public List<String> getCommand();
  public String getWorkDir();

}
