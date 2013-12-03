package org.nextreamlabs.bradme.views;

import javafx.scene.Node;

public interface IViewWithTemplate<TRootNode extends Node> extends IView {
  public TRootNode getRootNode();
}
