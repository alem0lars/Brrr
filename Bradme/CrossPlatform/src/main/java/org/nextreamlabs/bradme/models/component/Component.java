package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Component implements IComponent {

  private StringProperty name;
  private StringProperty desc;
  private IComponentStatus currentStatus;
  private List<IComponentStatus> statuses;
  private ObservableMap<IComponent, IComponentStatus> dependencies;
  private BooleanProperty areDependenciesSatisfied;

  // { Construction

  protected Component(StringProperty name, StringProperty desc, List<IComponentStatus> statuses, ObservableMap<IComponent, IComponentStatus> dependencies) {
    if (statuses.size() == 0) {
      throw new IllegalArgumentException("statuses cannot be empty");
    }
    this.name = name;
    this.desc = desc;
    this.statuses = new LinkedList<>(statuses);
    this.currentStatus = this.statuses.get(0);
    this.dependencies = dependencies;
    this.initializeAreDependenciesSatisfied();
  }

  public static IComponent create(String name, String desc, List<IComponentStatus> statuses, Map<IComponent, IComponentStatus> dependencies) {
    return new Component(new SimpleStringProperty(name), new SimpleStringProperty(desc), statuses, FXCollections.observableMap(dependencies));
  }

  // }

  // { IComponent implementation

  public StringProperty name() {
    return this.name;
  }

  public StringProperty desc() {
    return this.desc;
  }

  public IComponentStatus currentStatus() {
    return this.currentStatus;
  }

  public List<IComponentStatus> statuses() {
    return this.statuses;
  }

  public ObservableMap<IComponent, IComponentStatus> dependencies() {
    return this.dependencies;
  }

  public BooleanProperty areDependenciesSatisfied() {
    return this.areDependenciesSatisfied;
  }

  public void execute() {
    this.goToNextStatus();
  }

  public IComponentStatus nextStatus() {
    int nextStatusIndex = 0;
    for (int i = 0; i < this.statuses.size() - 1; i++) {
      if (this.statuses.get(i).equals(this.currentStatus)) {
        nextStatusIndex = i + 1;
      }
    }
    return this.statuses.get(nextStatusIndex);
  }

  // }

  @Override
  public String toString() {
    return String.format("Component <#%d,name=%s>", this.hashCode(), this.name().getValue());
  }

  /**
   * Two components are equals if they have:
   * - The same name
   * - The same dependencies
   *
   * @param o The other object to be compared to.
   * @return true if o is equal to this, otherwise false.
   */
  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof Component)) {
      return false;
    }
    IComponent otherComponent = (IComponent) o;

    return otherComponent.name().getValue().equals(this.name().getValue())
        && otherComponent.statuses().equals(this.statuses())
        && otherComponent.dependencies().entrySet().equals(this.dependencies.entrySet());
  }

  // { Utilities

  protected void initializeAreDependenciesSatisfied() {
    this.areDependenciesSatisfied = new SimpleBooleanProperty(this.computeAreDependenciesSatisfied());
    this.dependencies.addListener(new MapChangeListener<IComponent, IComponentStatus>() {
      @Override
      public void onChanged(Change<? extends IComponent, ? extends IComponentStatus> change) {
        Component.this.computeAreDependenciesSatisfied();
      }
    });
  }

  protected Boolean computeAreDependenciesSatisfied() {
    for (Map.Entry<IComponent, IComponentStatus> entry : this.dependencies.entrySet()) {
      IComponent dependency = entry.getKey();
      IComponentStatus dependencyRequiredStatus = entry.getValue();
      if (!dependency.areDependenciesSatisfied().getValue() ||
          !dependency.currentStatus().equals(dependencyRequiredStatus)) {
        return false;
      }
    }
    return true;
  }

  protected void goToNextStatus() {
    this.currentStatus = this.nextStatus();
  }

  // }

}
