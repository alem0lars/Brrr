package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.models.IModel;

import java.util.HashMap;
import java.util.Map;

public abstract class ModelFactoryWithCache<TKey, TValue extends IModel> implements IModelFactoryWithCache<TKey, TValue> {
  protected final Map<TKey, TValue> cache;

  // { Construction

  protected ModelFactoryWithCache() {
    this.cache = new HashMap<>();
  }

  // }

  // { IModelFactoryWithCache implementation

  public TValue get(TKey key) {
    return this.cache.get(key);
  }

  public TValue getOrCreate(TKey key) {
    return this.cache.put(key, this.createElement(key));
  }

  // }

  // { Creation

  protected abstract void initializeCache();

  protected abstract TValue createElement(TKey statusType);

  // }

}
