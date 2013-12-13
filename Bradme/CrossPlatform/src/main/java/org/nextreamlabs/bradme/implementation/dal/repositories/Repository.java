package org.nextreamlabs.bradme.implementation.dal.repositories;

import org.nextreamlabs.bradme.interfaces.dal.repositories.IRepository;

import java.util.Collection;
import java.util.LinkedList;

public class Repository<T> implements IRepository<T> {

  private Collection<T> values;

  // { Construction

  protected Repository(Collection<T> values) {
    this.values = new LinkedList<>(values);
  }

  // }

  // { IRepository implementation

  @Override
  public Collection<T> values() {
    return this.values;
  }

  // }

}
