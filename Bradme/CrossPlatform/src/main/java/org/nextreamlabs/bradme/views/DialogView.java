package org.nextreamlabs.bradme.views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

public class DialogView extends StandaloneView implements IDialogView {

  protected String text;

  protected DialogView(String text) {
    super();
    this.text = text;
    this.initializeViewContent();
  }

  public static DialogView create(String text) {
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
