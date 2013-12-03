package org.nextreamlabs.bradme.support;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
  private static final Logger DEFAULT_LOGGER;
  private static final String DEFAULT_LOGGER_CONFIG_PATH;

  // { Construction

  static {
    DEFAULT_LOGGER = LoggerFactory.getLogger("org.nextreamlabs.bradme");
    DEFAULT_LOGGER_CONFIG_PATH = "/org/nextreamlabs/bradme/config/log4j.xml";

    DOMConfigurator.configure(Logging.class.getResource(DEFAULT_LOGGER_CONFIG_PATH));
  }

  private Logging() {}

  // }

  public static void error(String message) {
    log(Severity.ERROR, message);
  }

  public static void warn(String message) {
    log(Severity.WARN, message);
  }

  public static void debug(String message) {
    log(Severity.DEBUG, message);
  }

  public static void info(String message) {
    log(Severity.INFO, message);
  }

  public static void log(Severity severity, String message) {
    log(DEFAULT_LOGGER, severity, message);
  }

  // { Utilities

  private static void log(Logger logger, Severity severity, String message) {
    switch (severity) {
      case ERROR:
        logger.error(message);
        break;
      case WARN:
        logger.warn(message);
        break;
      case INFO:
        logger.info(message);
        break;
      case DEBUG:
        logger.debug(message);
        break;
      default:
        throw new IllegalArgumentException("severity");
    }
  }

  // }

  public enum Severity {
    ERROR, WARN, INFO, DEBUG
  }

}
