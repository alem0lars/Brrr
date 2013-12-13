package org.nextreamlabs.bradme.interfaces.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.models.IModel;

public interface IFactoryForIModelWithCache<TKey, TValue extends IModel>
    extends IFactoryForIModel<TKey, TValue> {

  public TValue get(TKey key);

}
