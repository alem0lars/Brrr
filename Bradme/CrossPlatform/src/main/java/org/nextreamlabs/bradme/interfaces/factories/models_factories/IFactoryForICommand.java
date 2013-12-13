package org.nextreamlabs.bradme.interfaces.factories.models_factories;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;

public interface IFactoryForICommand
    extends IFactoryForIModel<ICommandDescriptor, ICommand> {
}
