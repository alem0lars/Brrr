package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIModel;
import org.nextreamlabs.bradme.interfaces.models.IModel;

public abstract class FactoryForIModel<TKey, TValue extends IModel> implements IFactoryForIModel<TKey, TValue> {

  // { IModelFactory implementation

  public TValue getNew(TKey key) {
    return this.createElement(key);
  }

  // }

  // { Element Creation

  protected abstract TValue createElement(TKey statusType);

  // }

}
