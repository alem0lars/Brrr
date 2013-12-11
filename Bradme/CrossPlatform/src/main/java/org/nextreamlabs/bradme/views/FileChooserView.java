package org.nextreamlabs.bradme.views;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserView extends StandaloneView implements IFileChooserView {

  protected String filePath;
  protected FileChooser fileChooser;

  // { Construction

  protected FileChooserView() {
    super();
    this.initializeFileChooser();
  }

  public static IFileChooserView create() {
    return new FileChooserView();
  }

  // }

  // { Initialization

  protected void initializeFileChooser() {
    this.fileChooser = new FileChooser();
    this.fileChooser.setTitle("Open Resource File");
  }

  // }

  // { IFileChooserView implementation

  @Override
  public void show() {
    File chosenFile = fileChooser.showOpenDialog(this.stage);
    if (chosenFile != null) {
      this.filePath = chosenFile.getAbsolutePath();
    }
  }

  public String getFilePath() {
    return this.filePath;
  }

  // }

}
