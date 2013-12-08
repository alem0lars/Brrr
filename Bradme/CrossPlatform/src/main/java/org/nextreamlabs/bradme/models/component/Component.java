package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.models.status.IStatus;

import java.util.Collection;
import java.util.Map;

public class Component implements IComponent {

  private StringProperty name;
  private StringProperty desc;
  private ObjectProperty<IComponentStatus> currentStatus;
  private ObjectProperty<IComponentStatus> nextStatus;
  private ListProperty<ObjectProperty<IComponentStatus>> statuses;
  private MapProperty<ObjectProperty<IComponent>, ObjectProperty<IStatus>> dependencies;
  private BooleanProperty areDependenciesSatisfied;

  // { Construction

  protected Component(
      String name, String desc,
      Collection<IComponentStatus> statuses,
      Map<IComponent, IStatus> dependencies) {
    if (statuses.size() == 0) {
      throw new IllegalArgumentException("statuses cannot be empty");
    }

    this.name = new SimpleStringProperty(name);

    this.desc = new SimpleStringProperty(desc);

    ObservableList<ObjectProperty<IComponentStatus>> tmpStatuses = FXCollections.observableArrayList();
    for (IComponentStatus status : statuses) {
      tmpStatuses.add(new SimpleObjectProperty<>(status));
    }
    this.statuses = new SimpleListProperty<>(tmpStatuses);

    this.currentStatus = new SimpleObjectProperty<>(this.statuses.get(0).getValue());
    this.nextStatus = new SimpleObjectProperty<>(this.findNextStatus(this.currentStatus().getValue()));

    ObservableMap<ObjectProperty<IComponent>, ObjectProperty<IStatus>> tmpDependencies = FXCollections.observableHashMap();
    for (Map.Entry<IComponent, IStatus> entry : dependencies.entrySet()) {
      tmpDependencies.put(new SimpleObjectProperty<>(entry.getKey()), new SimpleObjectProperty<>(entry.getValue()));
    }
    this.dependencies = new SimpleMapProperty<>(tmpDependencies);

    this.initializeAreDependenciesSatisfied();

  }

  public static IComponent create(
      String name, String desc,
      Collection<IComponentStatus> statuses,
      Map<IComponent, IStatus> dependencies) {
    return new Component(name, desc, statuses, dependencies);
  }

  // }

  // { IComponent implementation

  public StringProperty name() {
    return this.name;
  }

  public StringProperty desc() {
    return this.desc;
  }

  public ObjectProperty<IComponentStatus> currentStatus() {
    return this.currentStatus;
  }

  public ListProperty<ObjectProperty<IComponentStatus>> statuses() {
    return this.statuses;
  }

  public MapProperty<ObjectProperty<IComponent>, ObjectProperty<IStatus>> dependencies() {
    return this.dependencies;
  }

  public BooleanProperty areDependenciesSatisfied() {
    return this.areDependenciesSatisfied;
  }

  public ObjectProperty<IComponentStatus> nextStatus() {
    return this.nextStatus;
  }

  public void execute() {
    this.goToNextStatus();
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
        && otherComponent.dependencies().entrySet().equals(this.dependencies().entrySet());
  }

  // { Utilities

  protected void initializeAreDependenciesSatisfied() {
    this.areDependenciesSatisfied = new SimpleBooleanProperty(this.calculateAreDependenciesSatisfied());
    this.dependencies().addListener(new ChangeListener<ObservableMap<ObjectProperty<IComponent>, ObjectProperty<IStatus>>>() {
      @Override
      public void changed(
          ObservableValue<? extends ObservableMap<ObjectProperty<IComponent>, ObjectProperty<IStatus>>> deps,
          ObservableMap<ObjectProperty<IComponent>, ObjectProperty<IStatus>> oldValue,
          ObservableMap<ObjectProperty<IComponent>, ObjectProperty<IStatus>> newValue) {
        computeAreDependenciesSatisfied();
      }
    });
    for (Map.Entry<ObjectProperty<IComponent>, ObjectProperty<IStatus>> entry : this.dependencies().entrySet()) {
      ObjectProperty<IComponent> dependency = entry.getKey();
      dependency.addListener(new ChangeListener<IComponent>() {
        @Override
        public void changed(ObservableValue<? extends IComponent> observableValue, IComponent iComponent, IComponent iComponent2) {
          computeAreDependenciesSatisfied();
        }
      });
      dependency.getValue().currentStatus().addListener(new ChangeListener<IStatus>() {
        @Override
        public void changed(ObservableValue<? extends IStatus> observableValue, IStatus iStatus, IStatus iStatus2) {
          computeAreDependenciesSatisfied();
        }
      });
    }
  }

  protected Boolean calculateAreDependenciesSatisfied() {
    for (Map.Entry<ObjectProperty<IComponent>, ObjectProperty<IStatus>> entry : this.dependencies().entrySet()) {
      ObjectProperty<IComponent> dependency = entry.getKey();
      ObjectProperty<IStatus> dependencyRequiredStatus = entry.getValue();
      if (!dependency.getValue().areDependenciesSatisfied().getValue() ||
          // TODO: Fix next row comparison (it should be done between two IStatus (not a IComponentStatus and a IStatus))
          !dependency.getValue().currentStatus().getValue().equalsToStatus(dependencyRequiredStatus.getValue())) {
        return false;
      }
    }
    return true;
  }

  public void computeAreDependenciesSatisfied() {
    Boolean newValue = this.calculateAreDependenciesSatisfied();
    if (areDependenciesSatisfied().getValue() != newValue) {
      areDependenciesSatisfied().setValue(newValue);
    }
    if (!areDependenciesSatisfied().getValue()) {
      resetCurrentStatus();
    }
  }

  protected void resetCurrentStatus() {
    this.currentStatus().setValue(this.statuses().get(0).getValue());
    this.nextStatus().setValue(this.findNextStatus(this.currentStatus().getValue()));
  }

  protected void goToNextStatus() {
    this.currentStatus().setValue(this.findNextStatus(this.currentStatus().getValue()));
    this.nextStatus().setValue(this.findNextStatus(this.nextStatus().getValue()));
  }

  protected IComponentStatus findNextStatus(IStatus status) {
    int nextStatusIndex = 0;
    for (int i = 0; i < this.statuses().size() - 1; i++) {
      if (this.statuses().get(i).getValue().equals(status)) {
        nextStatusIndex = i + 1;
      }
    }
    return this.statuses().get(nextStatusIndex).getValue();
  }

  // }

}
