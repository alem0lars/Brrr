package org.nextreamlabs.bradme.dal.repositories;

import java.util.Collection;
import java.util.LinkedList;

public class Repository<T> implements IRepository<T> {

  private Collection<T> values;

  // { Construction

  protected Repository(Collection<T> values) {
    this.values = new LinkedList<>(values);
  }

  // }

  @Override
  public Collection<T> values() {
    return this.values;
  }

}
