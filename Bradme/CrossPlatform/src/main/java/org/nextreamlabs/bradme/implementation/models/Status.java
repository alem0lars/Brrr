package org.nextreamlabs.bradme.implementation.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.IStatus;

public class Status implements IStatus {

  private StringProperty name;
  private StringProperty desc;
  private StringProperty actionName;

  // { Construction

  protected Status(StringProperty name, StringProperty desc, StringProperty actionName) {
    this.name = name;
    this.desc = desc;
    this.actionName = actionName;
  }

  public static IStatus create(String name, String desc, String actionName) {
    return new Status(new SimpleStringProperty(name), new SimpleStringProperty(desc), new SimpleStringProperty(actionName));
  }

  // }

  // { IStatus implementation

  public StringProperty name() {
    return this.name;
  }

  public StringProperty desc() {
    return this.desc;
  }

  public StringProperty actionName() {
    return this.actionName;
  }

  public String getPrettyName() {
    return this.name().get();
  }

  // }

  /**
   * Two statuses are equals if they have:
   * - The same name
   *
   * @param o The other object to be compared to.
   * @return true if o is equal to this, otherwise false.
   */
  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof Status)) {
      return false;
    }
    IStatus otherStatus = (IStatus) o;

    return otherStatus.name().getValue().equals(this.name().getValue());
  }

}
