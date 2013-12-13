package org.nextreamlabs.bradme.implementation.models;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.nextreamlabs.bradme.interfaces.commands.ILocalCommandRunner;
import org.nextreamlabs.bradme.interfaces.commands.IRemoteCommandRunner;
import org.nextreamlabs.bradme.implementation.commands.LocalCommandRunner;
import org.nextreamlabs.bradme.implementation.commands.RemoteCommandRunner;
import org.nextreamlabs.bradme.implementation.exceptions.CannotChangeComponentStatusException;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;
import org.nextreamlabs.bradme.interfaces.models.commands.ILocalCommand;
import org.nextreamlabs.bradme.interfaces.models.commands.IRemoteCommand;
import org.nextreamlabs.bradme.interfaces.models.IComponent;
import org.nextreamlabs.bradme.interfaces.models.IStatus;
import org.nextreamlabs.bradme.interfaces.models.IStatusWithCommand;
import org.nextreamlabs.bradme.implementation.support.Logging;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class Component implements IComponent {

  private StringProperty name;
  private StringProperty desc;
  private ObjectProperty<IStatusWithCommand> currentStatus;
  private ObjectProperty<IStatusWithCommand> nextStatus;
  private ListProperty<ObjectProperty<IStatusWithCommand>> statuses;
  private MapProperty<ObjectProperty<IComponent>, ObjectProperty<IStatus>> dependencies;
  private BooleanProperty areDependenciesSatisfied;

  // { Construction

  protected Component(
      String name, String desc,
      Collection<IStatusWithCommand> statuses,
      Map<IComponent, IStatus> dependencies) {
    if (statuses.size() == 0) {
      throw new IllegalArgumentException("statuses cannot be empty");
    }

    this.name = new SimpleStringProperty(name);

    this.desc = new SimpleStringProperty(desc);

    ObservableList<ObjectProperty<IStatusWithCommand>> tmpStatuses = FXCollections.observableArrayList();
    for (IStatusWithCommand status : statuses) {
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
      Collection<IStatusWithCommand> statuses,
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

  public ObjectProperty<IStatusWithCommand> currentStatus() {
    return this.currentStatus;
  }

  public ListProperty<ObjectProperty<IStatusWithCommand>> statuses() {
    return this.statuses;
  }

  public MapProperty<ObjectProperty<IComponent>, ObjectProperty<IStatus>> dependencies() {
    return this.dependencies;
  }

  public BooleanProperty areDependenciesSatisfied() {
    return this.areDependenciesSatisfied;
  }

  public ObjectProperty<IStatusWithCommand> nextStatus() {
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
    this.changeState(this.statuses().get(0).getValue());
  }

  protected void goToNextStatus() {
    IStatusWithCommand current = this.currentStatus().getValue();
    IStatusWithCommand next = this.findNextStatus(current);
    this.changeState(next);
  }

  protected void changeState(IStatusWithCommand nextStatus) throws CannotChangeComponentStatusException {
    IStatusWithCommand current = this.currentStatus().getValue();
    Logging.debug(String.format("%s : changing status: '%s' -> '%s'", this, current.getPrettyName(), nextStatus.getPrettyName()));

    // Execute the command
      ICommand currentStatusEnteringCommand = nextStatus.command().getValue();
      if (currentStatusEnteringCommand instanceof ILocalCommand) {
        try {
          ILocalCommandRunner commandRunner = LocalCommandRunner.create((ILocalCommand) currentStatusEnteringCommand);
          commandRunner.start();
        } catch (IOException exc) {
          throw CannotChangeComponentStatusException.create(this.currentStatus().getValue(), nextStatus, exc.getMessage());
        }
      } else if (currentStatusEnteringCommand instanceof IRemoteCommand) {
        try {
          IRemoteCommandRunner commandRunner = RemoteCommandRunner.create((IRemoteCommand) currentStatusEnteringCommand);
          commandRunner.start();
        } catch (IOException exc) {
          throw CannotChangeComponentStatusException.create(this.currentStatus().getValue(), nextStatus, exc.getMessage());
        }
      } else {
        throw CannotChangeComponentStatusException.create(this.currentStatus().getValue(), nextStatus, "Invalid command for next status");
      }

    // Update current status.
    this.currentStatus().setValue(nextStatus);

    // Update next status.
    this.nextStatus().setValue(this.findNextStatus(this.currentStatus().getValue()));
  }

  protected IStatusWithCommand findNextStatus(IStatus status) {
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
