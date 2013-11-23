package org.nextreamlabs.bradme.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.nextreamlabs.bradme.controllers.IController;
import org.nextreamlabs.bradme.support.Logging;
import org.nextreamlabs.bradme.support.Templates;

public class MVCFactory {

  private static MVCFactory instance;

  // { Construction

  protected MVCFactory() {  }

  public static MVCFactory getInstance() {
    if (instance == null) {
      instance = new MVCFactory();
    }
    return instance;
  }

  // }

  /**
   * Setup for a controller and a view.
   *
   * Creates a new view by loading its template and creates a controller associating the view with it.
   *
   * @param templateName The name of the template which should be used to create the view.
   * @param controllerFactory The factory used to create the controller.
   * @param nodeClass An object which represents the node class (e.g. Controller.class if TNode is Controller).
   * @param <TNode> The type of the view.
   * @param <TController> The type for the controller.
   * @return The created view.
   */
  public <TNode extends Node, TController extends IController> TNode createForTemplate(String templateName, IControllerFactory<TController> controllerFactory, Class<TNode> nodeClass) {
    TController controller = controllerFactory.createController();
    FXMLLoader loader = new FXMLLoader(Templates.getTemplateURL(templateName));
    loader.setController(controller);
    TNode result = null;
    try {
      Object loadedObject = loader.load();
      if (nodeClass.isAssignableFrom(loadedObject.getClass())) {
        result = nodeClass.cast(loadedObject);
      }
    } catch (Exception exc) {
      Logging.error("Cannot create the pane");
    }
    return result;
  }

  public interface IControllerFactory<TController extends IController> {
    public TController createController();
  }

}
