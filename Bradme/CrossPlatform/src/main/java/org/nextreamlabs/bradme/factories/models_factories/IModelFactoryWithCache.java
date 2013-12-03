package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.models.IModel;

public interface IModelFactoryWithCache<TKey, TValue extends IModel> {
  public TValue get(TKey key);
  public TValue getNew(TKey key);
}
