package org.nextreamlabs.bradme.interfaces.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.IComponentDescriptor;
import org.nextreamlabs.bradme.interfaces.models.IComponent;

public interface IFactoryForIComponent
    extends IFactoryForIModelWithCache<IComponentDescriptor, IComponent> {
}
