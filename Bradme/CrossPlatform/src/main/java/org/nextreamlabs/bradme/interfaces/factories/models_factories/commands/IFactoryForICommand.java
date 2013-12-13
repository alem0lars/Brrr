package org.nextreamlabs.bradme.interfaces.factories.models_factories.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;
import org.nextreamlabs.bradme.interfaces.factories.models_factories.IFactoryForIModel;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;

public interface IFactoryForICommand
    extends IFactoryForIModel<ICommandDescriptor, ICommand> {
}
