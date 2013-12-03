package org.nextreamlabs.bradme.views;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class StandaloneView implements IStandaloneView {

  protected Stage stage;

  // { Construction

  protected StandaloneView() {
    this.initializeStage();
  }

  // }

  // { Initialization

  protected void initializeStage() {
    this.stage = new Stage();
    this.stage.initStyle(StageStyle.UTILITY);
  }

  // }

  // { IStandaloneView implementation

  @Override
  public void show() {
    this.stage.show();
  }

  // }

}
