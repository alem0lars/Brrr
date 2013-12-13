package org.nextreamlabs.bradme.implementation.views;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nextreamlabs.bradme.interfaces.views.IStandaloneView;

public abstract class StandaloneView
    implements IStandaloneView {

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
