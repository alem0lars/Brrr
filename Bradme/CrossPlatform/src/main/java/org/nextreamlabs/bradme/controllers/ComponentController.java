package org.nextreamlabs.bradme.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.nextreamlabs.bradme.models.component.IComponent;
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
  protected final IComponent component;

  // }

  // { Construction

  public ComponentController(IComponent component) {
    this.component = component;
  }

  // }

  // { IController implementation

  @Override
  public void initialize() {
    super.initialize();
    this.initializeComponentName();
    this.initializeComponentStatus();
    this.initializeComponentAction();
  }

  // }

  // { Initialization

  protected void initializeComponentName() {
    this.componentNameControl.textProperty().bindBidirectional(this.component.name());
  }

  protected void initializeComponentStatus() {
    this.componentStatusControl.textProperty().bindBidirectional(this.component.currentStatus().name());
  }

  protected void initializeComponentAction() {
    this.componentActionControl.textProperty().bindBidirectional(this.component.nextStatus().actionName());
    this.componentActionControl.disableProperty().bind(this.component.areDependenciesSatisfied().not());
  }

  // }

  // { Event handlers

  @FXML
  protected void onComponentAction(ActionEvent actionEvent) {
    Logging.debug(String.format("Clicked on action for component: %s", this.component));
    this.component.execute();
  }

  // }

}
