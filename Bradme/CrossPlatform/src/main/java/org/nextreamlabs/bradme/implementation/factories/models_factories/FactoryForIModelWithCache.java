package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIModelWithCache;
import org.nextreamlabs.bradme.interfaces.models.IModel;

import java.util.HashMap;
import java.util.Map;

public abstract class FactoryForIModelWithCache<TKey, TValue extends IModel>
    extends FactoryForIModel<TKey, TValue>
    implements IFactoryForIModelWithCache<TKey, TValue> {

  // { Fields (+Accessors)

  private final Map<TKey, TValue> cache;
  protected Map<TKey, TValue> getCache() {
    return this.cache;
  }

  // }

  // { Construction

  protected FactoryForIModelWithCache() {
    this.cache = new HashMap<>();
  }

  // }

  // { IModelFactoryWithCache implementation

  public TValue get(TKey key) {
    return this.getCache().get(key);
  }

  // }

  // { Creation

  protected abstract void initializeCache();

  // }

}
