package org.nextreamlabs.bradme.dal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.nextreamlabs.bradme.dal.descriptors.ComponentDescriptor;
import org.nextreamlabs.bradme.dal.descriptors.ComponentStatusDescriptor;
import org.nextreamlabs.bradme.dal.repositories.AvailableComponentStatusesRepository;
import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.support.Logging;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.LinkedList;

public class DALLoader implements IDALLoader {

  protected final Yaml yaml;
  protected String dbPath;

  // { Construction

  private DALLoader(String dbPath) {
    this.yaml = new Yaml();
    this.dbPath = dbPath;
  }

  public static DALLoader create(String dbPath) {
    return new DALLoader(dbPath);
  }

  // }

  public void load() throws FileNotFoundException {
    Object componentsData = this.yaml.load(new BufferedReader(new FileReader(this.dbPath)));
    Logging.debug("Loaded the components data: " + componentsData);
  }

  @Override
  public Collection<ComponentStatusDescriptor> queryComponentStatusDescriptors() {
    Collection<ComponentStatusDescriptor> componentStatusDescriptors = new LinkedList<>();

    // { TODO
    componentStatusDescriptors.add(ComponentStatusDescriptor.create("starting_name", "starting_desc"));
    componentStatusDescriptors.add(ComponentStatusDescriptor.create("started_name", "started_desc"));
    componentStatusDescriptors.add(ComponentStatusDescriptor.create("stopped_name", "stopped_desc"));
    // }

    return componentStatusDescriptors;
  }

  @Override
  public Collection<ComponentDescriptor> queryComponentDescriptors() {
    Collection<ComponentDescriptor> componentDescriptors = new LinkedList<>();

    // { TODO
    ComponentStatusDescriptor stoppedStatus = AvailableComponentStatusesRepository.getInstance().findByNameKey("stopped_name");
    if (stoppedStatus == null) {
      throw InvalidConfigurationException.create("The stopped status is required");
    }
    ComponentDescriptor brolDescriptor = ComponentDescriptor.create("brol_name", "brol_desc", stoppedStatus, new Pair[]{ });
    componentDescriptors.add(brolDescriptor);
    ComponentDescriptor buffererDescriptor = ComponentDescriptor.create(
        "bufferer_name", "bufferer_desc", stoppedStatus,
        new Pair[]{
            new ImmutablePair<>(brolDescriptor, stoppedStatus)
        });
    componentDescriptors.add(buffererDescriptor);
    ComponentDescriptor readerDescriptor = ComponentDescriptor.create(
        "reader_name", "reader_desc", stoppedStatus,
        new Pair[]{
            new ImmutablePair<>(buffererDescriptor, stoppedStatus)
        });
    componentDescriptors.add(readerDescriptor);
    ComponentDescriptor pusherDescriptor = ComponentDescriptor.create(
        "pusher_name", "pusher_desc", stoppedStatus,
        new Pair[]{
            new ImmutablePair<>(readerDescriptor, stoppedStatus)
        });
    componentDescriptors.add(pusherDescriptor);
    // }

    return componentDescriptors;
  }
}
