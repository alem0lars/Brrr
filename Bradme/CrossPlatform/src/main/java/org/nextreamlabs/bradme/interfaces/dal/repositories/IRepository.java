package org.nextreamlabs.bradme.interfaces.dal.repositories;

import java.util.Collection;

public interface IRepository<T> {
  public Collection<T> values();
}
