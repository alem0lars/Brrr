package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.models.IModel;

public abstract class ModelFactory<TKey, TValue extends IModel> implements IModelFactory<TKey, TValue> {

  // { IModelFactory implementation

  public TValue getNew(TKey key) {
    return this.createElement(key);
  }

  // }

  // { Creation

  protected abstract TValue createElement(TKey statusType);

  // }

}
