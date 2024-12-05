package de.krenbeh.gui.colorpicker;

import de.krenbeh.gui.Style;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorPickers extends VBox {
  // Statische Farb-Map
  public static final Map<Integer, Color> stateColors = new HashMap<>();
  public static Color selectedColor;
  private static final List<Runnable> listeners = new ArrayList<>();

  public ColorPickers(int numOfColors) {
    setSpacing(10);
    setPadding(new Insets(5));
    setStyle(Style.getBarsstyle());
    initializeStateColors(numOfColors);
    initializeColorPickers();
  }

  // Statische Methode zur Initialisierung der Farben
  public static void initializeStateColors(int numOfColors) {
    stateColors.put(0, Color.WHITE);
    stateColors.put(1, Color.BLACK);
    for (int state = 2; state < numOfColors; state++) {
      stateColors.put(state, Colors.ranFarbe());
    }
    notifyColorChangeListeners();
  }

  public static void addColorChangeListener(Runnable listener) {
    listeners.add(listener);
  }

  private static void notifyColorChangeListeners() {
    for (Runnable listener : listeners) {
      listener.run();
    }
  }

  private void initializeColorPickers() {
    ToggleGroup toggleGroup = new ToggleGroup();
    for (Map.Entry<Integer, Color> entry : stateColors.entrySet()) {
      int state = entry.getKey();
      Color color = entry.getValue();
      HBox colorEntry = createColorEntry(state, color, toggleGroup);
      getChildren().add(colorEntry);
    }

    toggleGroup.selectedToggleProperty().addListener(
      (observable, oldValue, newValue) -> {
        if (newValue != null) {
          selectedColor = (Color) newValue.getUserData();
        }
      }
    );
  }

private static HBox createColorEntry(int state, Color color, ToggleGroup toggleGroup) {
  HBox hBox = new HBox();
  hBox.setSpacing(50);
  hBox.setPadding(new Insets(5, 5, 5, 5));

  ColorPicker colorPicker = createColorPicker(color, state);
  RadioButton radioButton = createRadioButton(state, color, toggleGroup);

  hBox.getChildren().addAll(radioButton, colorPicker);
  return hBox;
}

private static ColorPicker createColorPicker(Color color, int state) {
  ColorPicker colorPicker = new ColorPicker(color);
  colorPicker.setStyle(Style.colorPickerStyle());
  colorPicker.setOnAction(e -> {
    Color newColor = colorPicker.getValue();
    stateColors.put(state, newColor);
    if (selectedColor.equals(color)) {
      selectedColor = newColor;
    }
    notifyColorChangeListeners();
  });
  return colorPicker;
}

private static RadioButton createRadioButton(int state, Color color, ToggleGroup toggleGroup) {
  RadioButton radioButton = new RadioButton("" + state);
  radioButton.setToggleGroup(toggleGroup);
  radioButton.setUserData(color);
  radioButton.setStyle(Style.radioStyle());
  radioButton.setOnAction(e -> selectedColor = stateColors.get(state));
  if (state == 1) {
    radioButton.setSelected(true);
    selectedColor = color;
  }
  return radioButton;
}

  public static Integer getStateForColor(Color color) {
    for (Map.Entry<Integer, Color> entry : stateColors.entrySet()) {
      if (entry.getValue().equals(color)) {
        return entry.getKey();
      }
    }
    return null;
  }
}
