package de.krenbeh.controllers;

import de.krenbeh.gui.Style;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;

public class TooltipHelper {
  private final Tooltip tooltip;

  public TooltipHelper() {
    tooltip = new Tooltip();
    tooltip.setStyle(Style.getTooltipStyle());
  }

  public void formatToInteger(TextField textField, int maxSize) {
    textField.setTextFormatter(new TextFormatter<>(change -> {
      String newText = change.getControlNewText();
      if (newText.matches("\\d*")) {
        if (!newText.isEmpty()) {
          int value = Integer.parseInt(newText);
          if (value > maxSize) {
            showTooltip(textField, "Maximaler Wert ist " + maxSize);
            return null;
          } else if (value < 1) {
            showTooltip(textField, "Minimaler Wert ist 1");
            return null;
          }
        }
        return change;
      } else {
        showTooltip(textField, "Nur Zahlen sind erlaubt");
        return null;
      }
    }));
  }



  public void formatJavaRules(TextField textField) {
    textField.setTextFormatter(new TextFormatter<>(change -> {
      String newText = change.getControlNewText();


      if (!newText.isEmpty() && !newText.matches("[a-zA-Z0-9]*")) {
        showTooltip(textField, "Nur Buchstaben und Zahlen erlaubt");
        return null;
      }


      if (!newText.isEmpty() && newText.matches("^\\d.*")) {
        showTooltip(textField, "Keine Zahlen am Anfang");
        return null;
      }


      if (!newText.isEmpty() && !Character.isUpperCase(newText.charAt(0))) {
        showTooltip(textField, "Erster Buchstabe muss groß sein");
        return null;
      }

      return change;
    }));
  }

  public boolean validateField(TextField textField, String errorMessage) {
    if (textField.getText().isEmpty()) {
      showTooltip(textField, errorMessage);
      return false;
    }
    return true;
  }

  public void showTooltip(TextField textField, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.initOwner(textField.getScene().getWindow()); // Set the parent window
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.setPrefSize(200, 100);
    alert.showAndWait();
  }

  public void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.setTitle("Warnung");
    alert.setHeaderText(null);
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.setPrefSize(200, 100);
    alert.showAndWait();
  }


}