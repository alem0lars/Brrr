package org.nextreamlabs.bradme.repositories;

import org.nextreamlabs.bradme.models.component_status.ComponentStatus;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;
import org.nextreamlabs.bradme.support.Resources;

import java.util.HashMap;
import java.util.Map;

public class ComponentStatusesRepository implements IRepository<StatusType, IComponentStatus> {
  private static IRepository<StatusType, IComponentStatus> instance = new ComponentStatusesRepository();
  Map<StatusType, IComponentStatus> cache;

  // { Construction

  protected ComponentStatusesRepository() {
    this.cache = new HashMap<>();
  }

  public static IRepository<StatusType, IComponentStatus> getInstance() {
    return instance;
  }

  // }

  // { IRepository implementation

  public IComponentStatus getCached(StatusType key) {
    return this.cache.get(key);
  }

  public IComponentStatus getNew(StatusType key) {
    return this.cache.put(key, this.createStatus(key));
  }

  // }

  // { Uncategorized

  protected IComponentStatus createStatus(StatusType statusType) {
    switch (statusType) {
      case STARTING:
        return ComponentStatus.create(Resources.t(Resources.Keys.I18N.STARTING_NAME), Resources.t(Resources.Keys.I18N.STARTING_DESC));
      case STARTED:
        return ComponentStatus.create(Resources.t(Resources.Keys.I18N.STARTED_NAME), Resources.t(Resources.Keys.I18N.STARTED_DESC));
      case STOPPED:
        return ComponentStatus.create(Resources.t(Resources.Keys.I18N.STOPPED_NAME), Resources.t(Resources.Keys.I18N.STOPPED_DESC));
      default:
        throw new IllegalArgumentException("statusType");
    }
  }

  // }

}
