package org.nextreamlabs.bradme.interfaces.views;

import javafx.scene.Node;

public interface IViewWithTemplate<TRootNode extends Node>
    extends IView {

  public TRootNode getRootNode();

}
