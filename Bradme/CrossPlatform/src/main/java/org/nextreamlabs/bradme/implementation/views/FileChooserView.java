package org.nextreamlabs.bradme.implementation.views;

import javafx.stage.FileChooser;
import org.nextreamlabs.bradme.interfaces.views.IFileChooserView;

import java.io.File;

public class FileChooserView
    extends StandaloneView
    implements IFileChooserView {

  // { Fields (+Accessors)

  private String filePath;
  protected void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  private FileChooser fileChooser;
  protected void setFileChooser(FileChooser fileChooser) {
    this.fileChooser = fileChooser;
  }
  protected FileChooser getFileChooser() {
    return this.fileChooser;
  }

  // }

  // { Construction

  protected FileChooserView() {
    super();
    this.setFileChooser(new FileChooser());
    this.getFileChooser().setTitle("###Open Resource File");
    this.setFilePath(null);
  }

  public static IFileChooserView create() {
    return new FileChooserView();
  }

  // }

  // { IFileChooserView implementation

  @Override
  public void show() {
    File chosenFile = this.getFileChooser().showOpenDialog(this.stage);
    if (chosenFile != null) {
      this.setFilePath(chosenFile.getAbsolutePath());
    }
  }

  public String getFilePath() {
    return this.filePath;
  }

  // }

}
