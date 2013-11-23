package org.nextreamlabs.bradme.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.nextreamlabs.bradme.controllers.IController;
import org.nextreamlabs.bradme.exceptions.CannotCreateViewException;
import org.nextreamlabs.bradme.support.Logging;
import org.nextreamlabs.bradme.support.Templates;

import java.io.IOException;

public abstract class ViewWithTemplate<TRootNode extends Node> implements IViewWithTemplate {

  private final String templateName;
  private final FXMLLoader loader;
  private final TRootNode rootNode;

  // { Construction

  protected ViewWithTemplate(String templateName, IController controller) {
    this.templateName = templateName;
    this.loader = new FXMLLoader(Templates.getTemplateURL(this.templateName));
    if (controller != null) {
      this.loader.setController(controller);
    }
    Object loadedObject = null;
    try {
      loadedObject = loader.load();
    } catch (IOException e) {
      Logging.error(String.format("Cannot load the template: %s", this.templateName));
    }
    if (loadedObject != null) {
      try {
        this.rootNode = (TRootNode) loadedObject;
      } catch(ClassCastException exc) {
        Logging.error("The template have a wrong root node");
        throw CannotCreateViewException.create(this.getTemplateName());
      }
    } else {
      throw CannotCreateViewException.create(this.getTemplateName());
    }
  }

  // }

  // { IViewWithTemplate implementation

  public TRootNode getRootNode() {
    return this.rootNode;
  }

  // }

  protected String getTemplateName() {
    return this.templateName;
  }

}
