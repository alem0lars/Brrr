package org.nextreamlabs.bradme.dal.repositories;

import java.util.Collection;

public interface IRepository<T> {
  public Collection<T> values();
}
