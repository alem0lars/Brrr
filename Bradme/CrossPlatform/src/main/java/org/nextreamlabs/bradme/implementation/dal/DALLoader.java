package org.nextreamlabs.bradme.implementation.dal;

import org.nextreamlabs.bradme.implementation.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.implementation.dal.descriptors.StatusDescriptor;
import org.nextreamlabs.bradme.implementation.dal.descriptors.StatusWithCommandDescriptor;
import org.nextreamlabs.bradme.implementation.dal.descriptors.commands.LocalCommandDescriptor;
import org.nextreamlabs.bradme.implementation.dal.descriptors.commands.RemoteCommandDescriptor;
import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.implementation.support.Logging;
import org.nextreamlabs.bradme.interfaces.dal.IDALLoader;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusWithCommandDescriptor;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static java.lang.Boolean.FALSE;

public class DALLoader implements IDALLoader {

  // { Fields (+Accessors)

  private final Yaml yaml;
  protected Yaml getYaml() {
    return this.yaml;
  }

  private final String dbPath;
  protected String getDBPath() {
    return this.dbPath;
  }

  private Map<String, Object> loadedContent;
  protected void setLoadedContent(Map<String, Object> loadedContent) {
    this.loadedContent = new HashMap<>(loadedContent);
  }
  protected Map<String, Object> getLoadedContent() {
    return this.loadedContent;
  }

  // }

  // { Construction

  private DALLoader(String dbPath) {
    this.yaml = new Yaml();
    this.dbPath = dbPath;
  }

  public static IDALLoader create(String dbPath) {
    return new DALLoader(dbPath);
  }

  // }


  // { IDALLoader implementation

  public void load() {
    try {
      this.setLoadedContent((Map<String, Object>) this.getYaml().load(new BufferedReader(new FileReader(this.dbPath))));
    } catch (Exception e) {
      Logging.error(String.format("Cannot load configuration file: '%s': %s", this.getDBPath(), e.getMessage()));
      throw InvalidConfigurationException.create(e.getMessage());
    }
    Logging.debug("Loaded the components data into: " + this.getLoadedContent());
  }

  public Collection<IStatusDescriptor> queryStatusDescriptors() {
    Collection<IStatusDescriptor> statusDescriptors = new LinkedList<>();
    String statusesKey = "statuses";

    this.ensureContains(this.getLoadedContent(), statusesKey);
    List loadedStatuses = this.ensureClass(List.class, this.getLoadedContent().get(statusesKey), String.format("%s isn't a list", statusesKey));

    for (Object loadedStatus : loadedStatuses) {
      Map<String, String> statusInfo = this.ensureClass(Map.class, loadedStatus, String.format("The status %s isn't an hash", loadedStatus));
      this.ensureContains(statusInfo, "id");
      String statusId = statusInfo.get("id");
      IStatusDescriptor statusDescriptor = StatusDescriptor.create(statusId);
      statusDescriptors.add(statusDescriptor);
    }

    return statusDescriptors;
  }

  public Collection<IComponentDescriptor> queryComponentDescriptors() {
    Collection<IStatusDescriptor> availableStatusDescriptors = queryStatusDescriptors();
    Collection<IComponentDescriptor> componentDescriptors = new LinkedList<>();

    // Components.
    this.ensureContains(this.getLoadedContent(), "components");
    List loadedComponents = ensureClass(List.class, this.getLoadedContent().get("components"), "'components' should be a list");

    for (Object loadedComponent : loadedComponents) {
      // { Component.
      Map<String, Object> componentInfo = this.ensureClass(Map.class, loadedComponent, "each 'components' content should be a map");
      this.ensureContains(componentInfo, "id");
      String componentId = this.ensureClass(String.class, componentInfo.get("id"), String.format("the component id should be a string"));

      // { Statuses.
      Collection<IStatusWithCommandDescriptor> selectedStatuses = new LinkedList<>();
      List<Object> loadedStatuses = this.ensureClass(List.class, componentInfo.get("statuses"), String.format("statuses for component %s are invalid", componentId));

      for (Object loadedStatus : loadedStatuses) {
        // { Status.
        Map<String, Object> statusInfo = this.ensureClass(Map.class, loadedStatus, String.format("the component status informations should be a dictionary"));
        this.ensureContains(statusInfo, "id");
        String statusId = this.ensureClass(String.class, statusInfo.get("id"), String.format("Invalid component status identifier"));

        IStatusDescriptor matchingStatusDescriptor = null;
        for (IStatusDescriptor statusDescriptor : availableStatusDescriptors) {
          if (statusDescriptor.getId().equals(statusId)) {
            matchingStatusDescriptor = statusDescriptor;
            break;
          }
        }
        if (matchingStatusDescriptor == null) {
          throw InvalidConfigurationException.create(String.format("status %s hasn't been declared", statusId));
        }

        // { Command.
        this.ensureContains(statusInfo, "cmd");
        Map<String, Object> commandInfo = this.ensureClass(Map.class, statusInfo.get("cmd"), "'cmd' should be a dictionary");
        ICommandDescriptor commandDescriptor = parseCommandDescriptor(commandInfo);
        // } Command.
        // } Status.
        selectedStatuses.add(StatusWithCommandDescriptor.create(statusId, commandDescriptor));
      }
      // } Statuses.

      // { Dependencies.
      Map<IComponentDescriptor, IStatusDescriptor> dependencies = new HashMap<>();
      List<Object> dependenciesInfo = this.ensureClass(List.class, componentInfo.get("dependencies"), String.format("The dependencies for component %s are invalid", componentId));

      for (Object dependencyInfo : dependenciesInfo) {
        // { Dependency.
        Map<String, String> dependencyMap = this.ensureClass(Map.class, dependencyInfo, "A dependency should be an hash");

        IComponentDescriptor dependencyComponent = null;
        String dependencyComponentId = dependencyMap.get("component");
        for (IComponentDescriptor componentDescriptor : componentDescriptors) {
          if (componentDescriptor.getId().equals(dependencyComponentId)) {
            dependencyComponent = componentDescriptor;
            break;
          }
        }
        if (dependencyComponent == null) {
          throw InvalidConfigurationException.create(String.format("The dependency refers to the component %s, which hasn't been declared yet", dependencyComponentId));
        }

        IStatusDescriptor dependencyStatus = null;
        String dependencyStatusId = dependencyMap.get("status");
        for (IStatusDescriptor statusDescriptor : availableStatusDescriptors) {
          if (statusDescriptor.getId().equals(dependencyStatusId)) {
            dependencyStatus = statusDescriptor;
            break;
          }
        }
        if (dependencyStatus == null) {
          throw InvalidConfigurationException.create(String.format("The dependency refers to the status %s, which hasn't been declared", dependencyStatusId));
        }

        dependencies.put(dependencyComponent, dependencyStatus);
        // } Dependency.
      }

      IComponentDescriptor componentDescriptor = ComponentDescriptor.create(componentId, selectedStatuses, dependencies);
      componentDescriptors.add(componentDescriptor);
      // } Component.
    }

  return componentDescriptors;
  }

  // }

  // { Partial parsing utilities.

  private ICommandDescriptor parseCommandDescriptor(Map<String, Object> cmdInfo) {
    ICommandDescriptor commandDescriptor = null;

    // Generic.
    this.ensureContains(cmdInfo, "command");
    List<Object> commandList = this.ensureClass(List.class, cmdInfo.get("command"), "Command 'command' should be a List.");
    List<String> command = new ArrayList<String>(commandList.size());
    for (Object argObj : commandList) {
      String argStr = this.ensureClass(String.class, argObj, "Command argument should be a String.");
      command.add(argStr);
    }

    this.ensureContains(cmdInfo, "work_dir");
    String workDir = this.ensureClass(String.class, cmdInfo.get("work_dir"), "Command 'work_dir' should be a String.");

    this.ensureContains(cmdInfo, "type");
    String type = this.ensureClass(String.class, cmdInfo.get("type"), "Command 'type' should be a String.");
    this.ensureStringToClass(ICommand.class, String.format("org.nextreamlabs.bradme.interfaces.models.commands.%s", type));

    // Specific.
    if (type.equals("ILocalCommand")) {
      // ILocalCommand.
      commandDescriptor = LocalCommandDescriptor.create(command, workDir);
    } else if (type.equals("IRemoteCommand")) {
      // IRemoteCommand.
      this.ensureContains(cmdInfo, "user");
      String user = this.ensureClass(String.class, cmdInfo.get("user"), "RemoteCommand 'user' should be a String.");
      this.ensureContains(cmdInfo, "host");
      String host = this.ensureClass(String.class, cmdInfo.get("host"), "RemoteCommand 'host' should be a String.");
      this.ensureContains(cmdInfo, "port");
      Integer port = this.ensureClass(Integer.class, cmdInfo.get("port"), "RemoteCommand 'port' should be an Integer.");
      commandDescriptor = RemoteCommandDescriptor.create(command, workDir, user, host, port);
    } else {
      // Not supported.
      throw InvalidConfigurationException.create(String.format("Command '%s' is not currently supported.", type));
    }

    if (commandDescriptor == null) {
      throw InvalidConfigurationException.create("An error occurred while creating a CommandDescriptor: commandDescriptor is null.");
    }
    return commandDescriptor;
  }

  // }

  // { Data handling utilities

  protected <T> T ensureClass(Class<T> klass, Object object, String message) {
    T result;
    try {
        result = klass.cast(object);
    } catch (ClassCastException exc) {
      Logging.error(String.format("Object '%s' cannot be cast as '%s'", object, klass));
      throw InvalidConfigurationException.create(message);
    }
    return result;
  }

  protected void ensureContains(Map map, String key) {
    if (!map.containsKey(key)) {
      throw InvalidConfigurationException.create(String.format("The key %s is missing", key));
    }
  }

  protected void ensureStringToClass(Class klass, String string) {
    Boolean ok = FALSE;
    try {
      // TODO: is `isAssignableFrom()` correct for the needed behaviour?
      ok = klass.isAssignableFrom(Class.forName(string));
    } catch (ClassNotFoundException e) {
      throw InvalidConfigurationException.create(String.format("String '%s' does not represent a class.", string));
    }
    if (!ok) {
      throw InvalidConfigurationException.create(String.format("Class '%s' is incompatible with '%s'.", string, klass));
    }
  }

  // }

}
