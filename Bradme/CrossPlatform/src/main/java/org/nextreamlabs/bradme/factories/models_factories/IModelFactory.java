package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.models.IModel;

public interface IModelFactory<TKey, TValue extends IModel> {

  public TValue getNew(TKey key);

}
