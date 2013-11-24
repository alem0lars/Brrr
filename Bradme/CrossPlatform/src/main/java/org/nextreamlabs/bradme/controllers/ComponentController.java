package org.nextreamlabs.bradme.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.nextreamlabs.bradme.models.component.IComponent;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.support.Logging;

public class ComponentController extends Controller implements IController {

  // { Controls

  @FXML
  protected Label componentNameControl;
  @FXML
  protected Label componentStatusControl;
  @FXML
  protected Button componentActionControl;

  // }

  // { Models

  @FXML
  protected final ObjectProperty<IComponent> component;

  // }

  // { Construction

  public ComponentController(IComponent component) {
    this.component = new SimpleObjectProperty<>(component);
  }

  // }

  // { IController implementation

  @Override
  public void initialize() {
    super.initialize();
    this.bindComponentNameControl();
    this.bindComponentActionControl();
    this.bindComponentStatusControl();
  }

  // }

  // { Initialization

  private void bindComponentNameControl() {
    this.component.addListener(new ChangeListener<IComponent>() {
      @Override
      public void changed(ObservableValue<? extends IComponent> v, IComponent oldComponent, IComponent newComponent) {
        componentNameControl.textProperty().unbind();
        componentNameControl.textProperty().bind(component.getValue().name());
      }
    });

    componentNameControl.textProperty().bind(component.getValue().name());
  }

  private void bindComponentActionControl() {
    this.component.getValue().nextStatus().addListener(new ChangeListener<IComponentStatus>() {
      @Override
      public void changed(ObservableValue<? extends IComponentStatus> observableValue, IComponentStatus iComponentStatus, IComponentStatus iComponentStatus2) {
        componentActionControl.textProperty().unbind();
        componentActionControl.textProperty().bind(component.getValue().nextStatus().getValue().actionName());
      }
    });

    this.component.getValue().areDependenciesSatisfied().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
        componentActionControl.disableProperty().unbind();
        componentActionControl.disableProperty().bind(component.getValue().areDependenciesSatisfied().not());
      }
    });

    componentActionControl.textProperty().bind(component.getValue().nextStatus().getValue().actionName());
    componentActionControl.disableProperty().bind(component.getValue().areDependenciesSatisfied().not());
  }

  private void bindComponentStatusControl() {
    this.component.getValue().currentStatus().addListener(new ChangeListener<IComponentStatus>() {
      @Override
      public void changed(ObservableValue<? extends IComponentStatus> v, IComponentStatus oldStatus, IComponentStatus newStatus) {
        componentStatusControl.textProperty().unbind();
        componentStatusControl.textProperty().bind(component.getValue().currentStatus().getValue().name());
      }
    });

    componentStatusControl.textProperty().bind(component.getValue().currentStatus().getValue().name());
  }

  // }

  // { Event handlers

  @FXML
  protected void onComponentAction(ActionEvent actionEvent) {
    Logging.debug(String.format("Clicked on action for component: %s", this.component.getValue()));
    this.component.getValue().execute();
  }

  // }

}
