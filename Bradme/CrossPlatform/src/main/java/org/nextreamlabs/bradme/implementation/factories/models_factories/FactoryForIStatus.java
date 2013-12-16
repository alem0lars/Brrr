package org.nextreamlabs.bradme.implementation.factories.models_factories;

import org.nextreamlabs.bradme.implementation.dal.repositories.AvailableStatusesRepository;
import org.nextreamlabs.bradme.implementation.models.Status;
import org.nextreamlabs.bradme.implementation.support.L10N;
import org.nextreamlabs.bradme.interfaces.dal.descriptors.IStatusDescriptor;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIStatus;
import org.nextreamlabs.bradme.interfaces.models.IStatus;

public class FactoryForIStatus
    extends FactoryForIModelWithCache<IStatusDescriptor, IStatus>
    implements IFactoryForIStatus {

  // { Construction

  protected FactoryForIStatus() {
    super();
    this.initializeCache();
  }

  public static IFactoryForIStatus create() {
    return new FactoryForIStatus();
  }

  // }

  // { IFactoryForIStatus implementation

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
      this.getCache().put(statusDescriptor, this.createElement(statusDescriptor));
    }
  }

  // }

}
