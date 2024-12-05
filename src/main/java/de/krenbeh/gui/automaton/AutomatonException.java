package de.krenbeh.gui.automaton;

/**
 * Exception thrown when an error occurs within the automaton operations.
 */
public class AutomatonException extends Exception {
  public AutomatonException(String message) {
    super(message);
  }

  public AutomatonException(String message, Throwable cause) {
    super(message, cause);
  }

}
