package org.nextreamlabs.bradme.implementation.support;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to handle logging and provide logging facilities.
 */
public class Logging {

  private static final Logger DEFAULT_LOGGER;
  private static final String DEFAULT_LOGGER_CONFIG_PATH;

  // { Construction

  /**
   * Initialize the constants.
   */
  static {
    DEFAULT_LOGGER = LoggerFactory.getLogger("org.nextreamlabs.bradme");
    DEFAULT_LOGGER_CONFIG_PATH = "/org/nextreamlabs/bradme/config/log4j.xml";

    DOMConfigurator.configure(Logging.class.getResource(DEFAULT_LOGGER_CONFIG_PATH));
  }

  /**
   * This constructor is private to prevent instantiation.
   */
  private Logging() { }

  // }

  /**
   * Log the provided message as an error (fatal or non-fatal).
   *
   * @param message The message to be logged.
   */
  public static void error(String message) {
    log(Severity.ERROR, message);
  }

  /**
   * Log the provided message as a warning.
   *
   * @param message The message to be logged.
   */
  public static void warn(String message) {
    log(Severity.WARN, message);
  }

  /**
   * Log the provided message for debugging purposes.
   *
   * @param message The message to be logged.
   */
  public static void debug(String message) {
    log(Severity.DEBUG, message);
  }

  /**
   * Log the provided message as an information.
   *
   * @param message The message to be logged.
   */
  public static void info(String message) {
    log(Severity.INFO, message);
  }

  /**
   * Log the provided message.
   *
   * @param severity The severity which the message is logged.
   * @param message The message to be logged.
   */
  public static void log(Severity severity, String message) {
    log(DEFAULT_LOGGER, severity, message);
  }

  // { Utilities

  /**
   * The logging mechanism implementation.
   *
   * @param logger The logger to be used.
   * @param severity The severity which the message is logged.
   * @param message The message to be logged.
   */
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

  /**
   * The available severities are values of this enum.
   */
  public enum Severity {
    ERROR,
    WARN,
    INFO,
    DEBUG
  }

}
