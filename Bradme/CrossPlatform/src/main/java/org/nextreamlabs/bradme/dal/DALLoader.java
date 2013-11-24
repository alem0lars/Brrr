package org.nextreamlabs.bradme.dal;

import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
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

  public void load() throws FileNotFoundException {
    this.loadedContent = (Map<String, Object>) this.yaml.load(new BufferedReader(new FileReader(this.dbPath)));
    Logging.debug("Loaded the components data into: " + this.loadedContent);
  }

  // { Queries

  @Override
  public Collection<ComponentStatusDescriptor> queryComponentStatusDescriptors() {
    Collection<ComponentStatusDescriptor> componentStatusDescriptors = new LinkedList<>();
    String statusesKey = "statuses";

    this.ensureContains(this.loadedContent, statusesKey);
    List loadedStatuses = this.typize(loadedContent.get(statusesKey), String.format("%s isn't a list", statusesKey));

    for (Object loadedStatus : loadedStatuses) {
      Map<String, String> statusInfo = this.typize(loadedStatus, String.format("The status %s isn't an hash", loadedStatus));
      this.ensureContains(statusInfo, "id");
      String statusId = statusInfo.get("id");
      ComponentStatusDescriptor componentStatusDescriptor = ComponentStatusDescriptor.create(statusId);
      componentStatusDescriptors.add(componentStatusDescriptor);
    }

    return componentStatusDescriptors;
  }

  @Override
  public Collection<ComponentDescriptor> queryComponentDescriptors() {
    Collection<ComponentStatusDescriptor> availableComponentStatusDescriptors = queryComponentStatusDescriptors();
    Collection<ComponentDescriptor> componentDescriptors = new LinkedList<>();
    String componentsKey = "components";

    this.ensureContains(this.loadedContent, componentsKey);
    List loadedComponents = typize(loadedContent.get(componentsKey), String.format("%s doesn't contain a list", componentsKey));

    for (Object loadedComponent : loadedComponents) {
      Map<String, Object> componentInfo = this.typize(loadedComponent, String.format("Invalid component info"));

      this.ensureContains(componentInfo, "id");
      String componentId = this.typize(componentInfo.get("id"), String.format("the component id should be a string"));

      Collection<ComponentStatusDescriptor> selectedComponentStatuses = new LinkedList<>();
      List<String> componentStatusesInfo = this.typize(componentInfo.get("statuses"), String.format("statuses for component %s are invalid", componentId));

      for (String componentStatusId : componentStatusesInfo) {
        Boolean found = false;
        for (ComponentStatusDescriptor componentStatusDescriptor : availableComponentStatusDescriptors) {
          if (componentStatusDescriptor.id.equals(componentStatusId)) {
            selectedComponentStatuses.add(componentStatusDescriptor);
            found = true;
            break;
          }
        }
        if (!found) {
          throw InvalidConfigurationException.create(String.format("status %s hasn't been declared", componentStatusId));
        }
      }

      Map<ComponentDescriptor, ComponentStatusDescriptor> dependencies = new HashMap<>();
      List<Object> dependenciesInfo = this.typize(componentInfo.get("dependencies"), String.format("The dependencies for component %s are invalid", componentId));

      for (Object dependencyInfo : dependenciesInfo) {
        Map<String, String> dependencyMap = this.typize((Map<String, String>) dependencyInfo, "A dependency should be an hash");

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

        ComponentStatusDescriptor dependencyStatus = null;
        String dependencyStatusId = dependencyMap.get("status");
        for (ComponentStatusDescriptor componentStatusDescriptor : availableComponentStatusDescriptors) {
          if (componentStatusDescriptor.id.equals(dependencyStatusId)) {
            dependencyStatus = componentStatusDescriptor;
            break;
          }
        }
        if (dependencyStatus == null) {
          throw InvalidConfigurationException.create(String.format("The dependency refers to the status %s, which hasn't been declared", dependencyStatusId));
        }

        dependencies.put(dependencyComponent, dependencyStatus);
      }

      ComponentDescriptor componentStatusDescriptor = ComponentDescriptor.create(componentId, selectedComponentStatuses, dependencies);
      componentDescriptors.add(componentStatusDescriptor);
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

  protected void ensureContains(Map map, String key) {
    if (!map.containsKey(key)) {
      throw InvalidConfigurationException.create(String.format("The key %s is missing", key));
    }
  }

  // }

}
