package org.nextreamlabs.bradme.implementation.views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import org.nextreamlabs.bradme.interfaces.views.IDialogView;

public class DialogView
    extends StandaloneView
    implements IDialogView {

  // { Fields (+Accessors)

  private final String text;
  protected String getText() {
    return this.text;
  }

  // }

  // { Construction

  protected DialogView(String text) {
    super();
    this.text = text;
    this.initializeViewContent();
  }

  public static IDialogView create(String text) {
    return new DialogView(text);
  }

  // }

  // { Initialization

  protected void initializeViewContent() {
    TextArea textArea = new TextArea(this.text);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    Scene scene = new Scene(new Group(textArea));
    this.stage.setScene(scene);
  }

  // }

}
