package org.nextreamlabs.bradme.factories.models_factories;

import org.nextreamlabs.bradme.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableStatusesRepository;
import org.nextreamlabs.bradme.models.status.IStatus;
import org.nextreamlabs.bradme.models.status.Status;
import org.nextreamlabs.bradme.support.L10N;

public class StatusesFactory
    extends ModelFactoryWithCache<IStatusDescriptor, IStatus>
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
  protected IStatus createElement(IStatusDescriptor statusDescriptor) {
    return Status.create(
        L10N.t(statusDescriptor.getNameKey()),
        L10N.t(statusDescriptor.getDescKey()),
        L10N.t(statusDescriptor.getActionNameKey()));
  }

  @Override
  protected void initializeCache() {
    for (IStatusDescriptor statusDescriptor : AvailableStatusesRepository.getInstance().values()) {
      this.cache.put(statusDescriptor, this.createElement(statusDescriptor));
    }
  }

  // }

}
