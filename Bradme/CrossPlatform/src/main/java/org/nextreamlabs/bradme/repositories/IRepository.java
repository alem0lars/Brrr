package org.nextreamlabs.bradme.repositories;

import org.nextreamlabs.bradme.models.IModel;

public interface IRepository<TKey extends Object, TValue extends IModel> {
  public TValue getCached(TKey key);
  public TValue getNew(TKey key);
}
