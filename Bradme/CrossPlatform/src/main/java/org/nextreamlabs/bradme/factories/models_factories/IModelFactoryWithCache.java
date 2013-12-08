package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.models.IModel;

public interface IModelFactoryWithCache<TKey, TValue extends IModel> extends IModelFactory<TKey, TValue> {
  public TValue get(TKey key);
}
