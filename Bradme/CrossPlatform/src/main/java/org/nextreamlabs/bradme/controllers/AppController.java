package org.nextreamlabs.bradme.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.nextreamlabs.bradme.dal.DALLoader;
import org.nextreamlabs.bradme.dal.IDALLoader;
import org.nextreamlabs.bradme.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.dal.repositories.AvailableStatusesRepository;
import org.nextreamlabs.bradme.exceptions.CannotCreateViewException;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.factories.models_factories.ComponentsFactory;
import org.nextreamlabs.bradme.factories.models_factories.IComponentsFactory;
import org.nextreamlabs.bradme.factories.models_factories.IStatusesFactory;
import org.nextreamlabs.bradme.factories.models_factories.StatusesFactory;
import org.nextreamlabs.bradme.models.component.IComponent;
import org.nextreamlabs.bradme.support.L10N;
import org.nextreamlabs.bradme.support.Logging;
import org.nextreamlabs.bradme.views.*;

import java.util.Collection;
import java.util.LinkedList;

public class AppController extends Controller implements IController {

  private IComponentsFactory componentsFactory;
  private IStatusesFactory statusesFactory;

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

  private AppController() {
    this.initializeIsConfigured();
  }

  public static AppController create() {
    return new AppController();
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

    String filePath = null;

    IFileChooserView fileChooserView = FileChooserView.create();
    fileChooserView.show();
    filePath = fileChooserView.getFilePath();

    if (filePath != null) {
      try {
        this.isConfigured.setValue(false);
        IDALLoader dalLoader = DALLoader.create(filePath);
        dalLoader.load();
        AvailableStatusesRepository.configureRepository(dalLoader);
        AvailableComponentsRepository.configureRepository(dalLoader);
        this.statusesFactory = StatusesFactory.create();
        this.componentsFactory = ComponentsFactory.create(this.statusesFactory);
        this.isConfigured.setValue(true);
      } catch (InvalidConfigurationException e) {
        Logging.error(e.getMessage());
      }
    }
  }

  @FXML
  protected void showAboutDialog() {
    Logging.debug("Showing the about dialog");
    IStandaloneView dialogView = DialogView.create(L10N.t("about_content"));
    dialogView.show();
  }

  @FXML
  protected void close() {
    Logging.debug("Closing the application");
    Platform.exit();
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

  protected void initializeIsConfigured() {
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

  protected void initializeComponents() {
    for (IComponentDescriptor componentDescriptor : AvailableComponentsRepository.getInstance().values()) {
      IComponent component = this.componentsFactory.get(componentDescriptor);
      if (component != null) {
        this.components.add(component);
      }
    }

    for (final IComponent component : this.components) {
      IController controller = ComponentController.create(component);

      try {
        IComponentView appView = ComponentView.create(controller);
        this.componentsControl.getChildren().add(appView.getRootNode());
      } catch(CannotCreateViewException exc) {
        Logging.warn(String.format("Skipping the component: %s. Reason: %s", component, exc.getMessage()));
      }
    }
  }

  // }

}
