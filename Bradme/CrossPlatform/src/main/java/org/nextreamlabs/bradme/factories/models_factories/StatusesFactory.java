package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.StatusDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableStatusesRepository;
import org.nextreamlabs.bradme.models.status.Status;
import org.nextreamlabs.bradme.models.status.IStatus;
import org.nextreamlabs.bradme.support.L10N;

public class StatusesFactory
    extends ModelFactoryWithCache<StatusDescriptor, IStatus>
    implements IStatusesFactory {

  // { Construction

  protected StatusesFactory() {
    super();
    this.initializeCache();
  }

  public static IStatusesFactory create() {
    return new StatusesFactory();
  }

  // }

  // { ModelFactoryWithCache implementation

  @Override
  protected IStatus createElement(StatusDescriptor statusDescriptor) {
    return Status.create(
        L10N.t(statusDescriptor.nameKey),
        L10N.t(statusDescriptor.descKey),
        L10N.t(statusDescriptor.actionNameKey));
  }

  @Override
  protected void initializeCache() {
    for (StatusDescriptor statusDescriptor : AvailableStatusesRepository.getInstance().values()) {
      this.cache.put(statusDescriptor, this.createElement(statusDescriptor));
    }
  }

  // }

}
