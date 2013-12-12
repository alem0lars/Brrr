package org.nextreamlabs.bradme.dal;

import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.StatusDescriptor;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.support.Logging;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DALLoader implements IDALLoader {

  protected final Yaml yaml;
  protected String dbPath;
  protected Map<String, Object> loadedContent;

  // { Construction

  private DALLoader(String dbPath) {
    this.yaml = new Yaml();
    this.dbPath = dbPath;
  }

  public static DALLoader create(String dbPath) {
    return new DALLoader(dbPath);
  }

  // }

  public void load() {
    try {
      this.loadedContent = (Map<String, Object>) this.yaml.load(new BufferedReader(new FileReader(this.dbPath)));
    } catch (Exception e) {
      Logging.error(String.format("Cannot load configuration file: '%s': %s", this.dbPath, e.getMessage()));
      throw InvalidConfigurationException.create(e.getMessage());
    }
    Logging.debug("Loaded the components data into: " + this.loadedContent);
  }

  // { Queries

  @Override
  public Collection<StatusDescriptor> queryStatusDescriptors() {
    Collection<StatusDescriptor> statusDescriptors = new LinkedList<>();
    String statusesKey = "statuses";

    this.ensureContains(this.loadedContent, statusesKey);
    List loadedStatuses = this.ensureClass(List.class, loadedContent.get(statusesKey), String.format("%s isn't a list", statusesKey));

    for (Object loadedStatus : loadedStatuses) {
      Map<String, String> statusInfo = this.ensureClass(Map.class, loadedStatus, String.format("The status %s isn't an hash", loadedStatus));
      this.ensureContains(statusInfo, "id");
      String statusId = statusInfo.get("id");
      StatusDescriptor statusDescriptor = StatusDescriptor.create(statusId);
      statusDescriptors.add(statusDescriptor);
    }

    return statusDescriptors;
  }

  @Override
  public Collection<ComponentDescriptor> queryComponentDescriptors() {
    Collection<StatusDescriptor> availableStatusDescriptors = queryStatusDescriptors();
    Collection<ComponentDescriptor> componentDescriptors = new LinkedList<>();
    String componentsKey = "components";

    this.ensureContains(this.loadedContent, componentsKey);
    List loadedComponents = ensureClass(List.class, loadedContent.get(componentsKey), String.format("%s doesn't contain a list", componentsKey));

    for (Object loadedComponent : loadedComponents) {
      Map<String, Object> componentInfo = this.ensureClass(Map.class, loadedComponent, String.format("Invalid component info"));

      this.ensureContains(componentInfo, "id");
      String componentId = this.ensureClass(String.class, componentInfo.get("id"), String.format("the component id should be a string"));

      Collection<ComponentStatusDescriptor> selectedStatuses = new LinkedList<>();
      List<Object> loadedStatuses = this.ensureClass(List.class, componentInfo.get("statuses"), String.format("statuses for component %s are invalid", componentId));

      for (Object loadedStatus : loadedStatuses) {
        Boolean found = false;
        Map<String, Object> statusInfo = this.ensureClass(Map.class, loadedStatus, String.format("the component status informations should be a dictionary"));
        String statusId = this.ensureClass(String.class, statusInfo.get("id"), String.format("Invalid component status identifier"));
        String statusCommandOnStart = this.ensureClass(String.class, statusInfo.get("cmd"), String.format("Invalid component status command (on start)"));
        for (StatusDescriptor statusDescriptor : availableStatusDescriptors) {
          if (statusDescriptor.id.equals(statusId)) {
            selectedStatuses.add(ComponentStatusDescriptor.create(statusId, statusCommandOnStart));
            found = true;
            break;
          }
        }
        if (!found) {
          throw InvalidConfigurationException.create(String.format("status %s hasn't been declared", statusId));
        }
      }

      Map<ComponentDescriptor, StatusDescriptor> dependencies = new HashMap<>();
      List<Object> dependenciesInfo = this.ensureClass(List.class, componentInfo.get("dependencies"), String.format("The dependencies for component %s are invalid", componentId));

      for (Object dependencyInfo : dependenciesInfo) {
        Map<String, String> dependencyMap = this.ensureClass(Map.class, dependencyInfo, "A dependency should be an hash");

        ComponentDescriptor dependencyComponent = null;
        String dependencyComponentId = dependencyMap.get("component");
        for (ComponentDescriptor componentDescriptor : componentDescriptors) {
          if (componentDescriptor.id.equals(dependencyComponentId)) {
            dependencyComponent = componentDescriptor;
            break;
          }
        }
        if (dependencyComponent == null) {
          throw InvalidConfigurationException.create(String.format("The dependency refers to the component %s, which hasn't been declared yet", dependencyComponentId));
        }

        StatusDescriptor dependencyStatus = null;
        String dependencyStatusId = dependencyMap.get("status");
        for (StatusDescriptor statusDescriptor : availableStatusDescriptors) {
          if (statusDescriptor.id.equals(dependencyStatusId)) {
            dependencyStatus = statusDescriptor;
            break;
          }
        }
        if (dependencyStatus == null) {
          throw InvalidConfigurationException.create(String.format("The dependency refers to the status %s, which hasn't been declared", dependencyStatusId));
        }

        dependencies.put(dependencyComponent, dependencyStatus);
      }

      ComponentDescriptor componentDescriptor = ComponentDescriptor.create(componentId, selectedStatuses, dependencies);
      componentDescriptors.add(componentDescriptor);
    }

    return componentDescriptors;
  }

  // }

  // { Data handling utilities

  protected <T> T typize(Object object, String message) {
    T result;
    try {
        result = (T) object;
    } catch (ClassCastException exc) {
        throw InvalidConfigurationException.create(message);
    }
    return result;
  }

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

  // }

}
