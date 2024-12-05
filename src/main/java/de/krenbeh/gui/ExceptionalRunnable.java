package de.krenbeh.gui;

import de.krenbeh.gui.automaton.AutomatonException;

/**
 * Represents a functional interface similar to Runnable but allows for the
 * possibility of throwing an AutomatonException.
 * <p>
 * This interface can be used to create lambda expressions or method references
 * that may throw an AutomatonException, facilitating cleaner exception handling
 * in functional programming contexts.
 * <p>
 * The single abstract method, run, is intended to be implemented with the
 * desired execution logic that may throw an AutomatonException. This is useful,
 * for example, in scenarios where certain operations have to handle specific
 * checked exceptions, like toggling a mode in an application.
 */
public interface ExceptionalRunnable {
  void run() throws AutomatonException;
}
