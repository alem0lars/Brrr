package org.nextreamlabs.bradme.implementation.controllers;

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
import org.nextreamlabs.bradme.implementation.dal.DALLoader;
import org.nextreamlabs.bradme.interfaces.dal.IDALLoader;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.implementation.dal.repositories.AvailableComponentsRepository;
import org.nextreamlabs.bradme.implementation.dal.repositories.AvailableStatusesRepository;
import org.nextreamlabs.bradme.implementation.exceptions.CannotCreateViewException;
import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.implementation.factories.models_factories.FactoryForIComponent;
import org.nextreamlabs.bradme.implementation.factories.models_factories.FactoryForIStatus;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIComponent;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIStatus;
import org.nextreamlabs.bradme.interfaces.models.IComponent;
import org.nextreamlabs.bradme.implementation.support.L10N;
import org.nextreamlabs.bradme.implementation.support.Logging;
import org.nextreamlabs.bradme.implementation.views.*;
import org.nextreamlabs.bradme.interfaces.controllers.IAppController;
import org.nextreamlabs.bradme.interfaces.controllers.IComponentController;
import org.nextreamlabs.bradme.interfaces.views.IComponentView;
import org.nextreamlabs.bradme.interfaces.views.IFileChooserView;
import org.nextreamlabs.bradme.interfaces.views.IStandaloneView;

import java.util.Collection;
import java.util.LinkedList;

public class AppController
    extends Controller
    implements IAppController {

  private IFactoryForIComponent componentsFactory;
  private IFactoryForIStatus statusesFactory;

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

  public static IAppController create() {
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
        this.statusesFactory = FactoryForIStatus.create();
        this.componentsFactory = FactoryForIComponent.create(this.statusesFactory);
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
      IComponentController controller = ComponentController.create(component);

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
