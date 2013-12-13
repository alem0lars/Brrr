package org.nextreamlabs.bradme.interfaces.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.models.IModel;

public interface IFactoryForIModel<TKey, TValue extends IModel> {

  public TValue getNew(TKey key);

}
