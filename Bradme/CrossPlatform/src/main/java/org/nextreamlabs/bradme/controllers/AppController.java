package org.nextreamlabs.bradme.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.nextreamlabs.bradme.dal.DALLoader;
import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentStatusesRepository;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.factories.MVCFactory;
import org.nextreamlabs.bradme.factories.models_factories.ComponentStatusesFactory;
import org.nextreamlabs.bradme.factories.models_factories.ComponentsFactory;
import org.nextreamlabs.bradme.models.component.IComponent;
import org.nextreamlabs.bradme.support.L10N;
import org.nextreamlabs.bradme.support.Logging;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;

public class AppController extends Controller implements IController {

  private ComponentsFactory componentsFactory;
  private ComponentStatusesFactory componentStatusesFactory;

  // { Controls

  @FXML
  protected Label componentsDashboardTitleControl;
  @FXML
  protected Label detailsTitleControl;
  @FXML
  protected VBox componentsControl;

  // }

  // { Models

  protected Collection<IComponent> components;

  // }

  // { Miscellaneous properties

  protected BooleanProperty isConfigured;

  @FXML
  protected StringProperty componentsDashboardTitleText;
  @FXML
  protected StringProperty detailsTitleText;

  // }

  // { Construction

  public AppController() {
    this.isConfigured = new SimpleBooleanProperty(false);
    this.isConfigured.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          AppController.this.initializeComponents();
        } else {
          AppController.this.initializeEmptyComponents();
        }
      }
    });
  }

  // }

  // { IController implementation

  @Override
  public void initialize() {
    super.initialize();

    this.initializeComponentsDashboard();
    this.initializeDetailsTitle();
    this.initializeEmptyComponents();
  }

  // }

  // { Event handlers

  @FXML
  protected void loadData() {
    try {
      DALLoader dalLoader = DALLoader.create("/Users/alem0lars/Projects/Brrr/data/components.yml");
      dalLoader.load();
      AvailableComponentStatusesRepository.configureRepository(dalLoader);
      AvailableComponentsRepository.configureRepository(dalLoader);
      this.componentStatusesFactory = ComponentStatusesFactory.create();
      this.componentsFactory = ComponentsFactory.create(this.componentStatusesFactory);
      this.isConfigured.setValue(true);
    } catch (FileNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      // TODO
    }
  }

  @FXML
  protected void showAboutDialog() {
    Logging.debug("Showing the about dialog");
  }

  @FXML
  protected void close() {
    Logging.debug("Closing the application");
  }

  // }

  // { Initialization utilities

  protected void initializeComponentsDashboard() {
    this.componentsDashboardTitleText = new SimpleStringProperty(L10N.t("components_dashboard_title"));
    this.componentsDashboardTitleControl.textProperty().bindBidirectional(this.componentsDashboardTitleText);
  }

  protected void initializeDetailsTitle() {
    this.detailsTitleText = new SimpleStringProperty(L10N.t("details_title"));
    this.detailsTitleControl.textProperty().bindBidirectional(this.detailsTitleText);
  }

  protected void initializeEmptyComponents() {
    this.components = new LinkedList<>();
    this.componentsControl.getChildren().clear();
  }

  protected void initializeComponents() {
    for (ComponentDescriptor componentDescriptor : AvailableComponentsRepository.getInstance().values()) {
      IComponent component = this.componentsFactory.get(componentDescriptor);
      if (component != null) {
        this.components.add(component);
      }
    }

    for (final IComponent component : this.components) {
      Node node = MVCFactory.getInstance().createForTemplate("component", new MVCFactory.IControllerFactory() {
        @Override
        public ComponentController createController() {
          return new ComponentController(component);
        }
      }, Node.class);
      if (node != null) {
        this.componentsControl.getChildren().add(node);
      }
    }
  }

  // }

}
