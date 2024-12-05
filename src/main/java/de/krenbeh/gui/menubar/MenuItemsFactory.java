package de.krenbeh.gui.menubar;

import de.krenbeh.gui.ExceptionalRunnable;
import de.krenbeh.gui.automaton.AutomatonException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Abstract base class representing a menu bar with various menu items.
 * The MenuBar class provides methods to create and manage menu items,
 * and requires concrete implementations to handle specific actions.
 */
public abstract class MenuItemsFactory implements MenuActionHandler {
  private static final Logger logger = Logger.getLogger(MenuItemsFactory.class.getName());
  private MenuBar menuBar;


  private MenuItem createMenuItem(
    String text, String iconPath, EventHandler<ActionEvent> eventHandler, String id) {
    ImageView icon = null;
    if (iconPath != null) {
      try {
        Image image = new Image(
          Objects.requireNonNull(
            getClass().getResourceAsStream(iconPath)
          )
        );
        icon = new ImageView(image);
      } catch (NullPointerException e) {
        logger.info("Icon not found at path: " + iconPath);
      }
    }
    MenuItem menuItem = new MenuItem(text, icon);
    menuItem.setOnAction(eventHandler);
    if (id != null) {
      menuItem.setId(id); // ID setzen
    }
    return menuItem;
  }

  public MenuBar creatMenu() {
    // Menü "Automat"
    Menu automat = new Menu("_Automat");
    String unVisibleIcon = "/icons/unvis16.gif";
    MenuItem neu = createMenuItem("_Neu...", "/icons/New16.gif", this::handleNeu, "Neu");
    MenuItem laden = createMenuItem("_Laden", "/icons/Load16.gif", this::handleLaden, "Laden");
    MenuItem editor = createMenuItem("_Editor", unVisibleIcon, this::handleEditor, "Editor");
    MenuItem beenden = createMenuItem("_Beenden", unVisibleIcon, this::handleBeenden, "Beenden");
    automat.getItems().addAll(neu, laden, editor, new SeparatorMenuItem(), beenden);

    // Menü "Population"
    Menu population = new Menu("P_opulation");
    MenuItem groesseAendern = createMenuItem("_Größe ändern...", null, this::handleGroesseAendern, "Size");
    MenuItem loeschen = createMenuItem("_Löschen", null, this::handleLoeschen, "Delete");
    MenuItem erzeugen = createMenuItem("_Erzeugen", null, this::handleErzeugen, "Erzeugen");
    MenuItem torus = new CheckMenuItem("_Torus");
    torus.setOnAction(this::handleTorus);
    torus.setId("Torus");
    MenuItem vergroessern = createMenuItem("_Vergrößern", null, this::handleVergroessern, "ZoomIn");
    MenuItem verkleinern = createMenuItem("_Verkleinern", null, this::handleVerkleinern, "ZoomOut");

    // Untermenü "Speichern"
    Menu speichern = new Menu("_Speichern");
    MenuItem xmlSpeichern = createMenuItem("_XML", null, this::handleXmlSpeichern, "XMLSpeichern");
    MenuItem serialisieren = createMenuItem("_Serialisieren", null, this::handleSerialisieren, "Serialisieren");
    speichern.getItems().addAll(xmlSpeichern, serialisieren);

    MenuItem ladenPopulation = createMenuItem("_Laden", null, this::handleLadenPopulation, "LadenPopulation");
    MenuItem drucken = createMenuItem("_Drucken", null, this::handleDrucken, "Drucken");
    population.getItems().addAll(
      groesseAendern, loeschen, erzeugen, torus, new SeparatorMenuItem(),
      vergroessern, verkleinern, new SeparatorMenuItem(), speichern,
      new SeparatorMenuItem(), ladenPopulation, drucken
    );

    // Menü "Simulation"
    Menu simulation = new Menu("S_imulation");
    MenuItem schritt = createMenuItem("_Schritt..", "/icons/unvis16.gif",
      e -> handleException(() -> handleSchritt(e)), "Step");
    MenuItem start = createMenuItem("_Start", "/icons/Start16.gif", this::handleStart, "Play");
    MenuItem stop = createMenuItem("_Stop", "/icons/Stop16.gif", this::handleStop, "Stop");
    simulation.getItems().addAll(schritt, new SeparatorMenuItem(), start, stop);

    // Erstellen der Haupt-Menüleiste
    menuBar = new javafx.scene.control.MenuBar();
    menuBar.getMenus().addAll(automat, population, simulation);

    // Setzen von Acceleratoren
    neu.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
    laden.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
    editor.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
    beenden.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
    groesseAendern.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+G"));
    loeschen.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+L"));
    erzeugen.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
    torus.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+T"));
    vergroessern.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+I"));
    verkleinern.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+O"));
    xmlSpeichern.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+X"));
    serialisieren.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
    ladenPopulation.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+L"));
    drucken.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
    schritt.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
    start.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+A"));
    stop.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+Q"));

    return menuBar;
  }

  public MenuItem getMenuItem(String id) {
    return menuBar.getMenus().stream()
      .flatMap(menu -> menu.getItems().stream())
      .filter(menuItem -> id.equals(menuItem.getId()))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("No menu item with id " + id));
  }

  public MenuBar getMenu() {
    return this.menuBar;
  }

  private void handleException(ExceptionalRunnable runnable) {
    try {
      runnable.run();
    } catch (AutomatonException e) {
      logger.severe("Failed to toggle Torus mode: " + e.getMessage());
      // Show an alert dialog or handle error gracefully here
    }
  }
}