package de.krenbeh.gui.menubar;

import de.krenbeh.gui.automaton.AutomatonException;
import javafx.event.ActionEvent;

public interface MenuActionHandler {


  void handleNeu(ActionEvent actionEvent);

  void handleLaden(ActionEvent actionEvent);

  void handleEditor(ActionEvent actionEvent);

  void handleBeenden(ActionEvent actionEvent);

  void handleGroesseAendern(ActionEvent actionEvent);

  void handleLoeschen(ActionEvent actionEvent);

  void handleErzeugen(ActionEvent actionEvent);

  void handleTorus(ActionEvent event);

  void handleVergroessern(ActionEvent actionEvent);

  void handleVerkleinern(ActionEvent actionEvent);

  void handleXmlSpeichern(ActionEvent actionEvent);

  void handleSerialisieren(ActionEvent actionEvent);

  void handleLadenPopulation(ActionEvent actionEvent);

  void handleDrucken(ActionEvent actionEvent);

  void handleSchritt(ActionEvent actionEvent) throws AutomatonException;

  void handleStart(ActionEvent actionEvent);

  void handleStop(ActionEvent actionEvent);
}


