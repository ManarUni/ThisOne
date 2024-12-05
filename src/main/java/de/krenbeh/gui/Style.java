package de.krenbeh.gui;

/**
 * The Style class provides a collection of static methods to retrieve various
 * styles used in the user interface of the application. Each method returns a
 * specific type of style as a string.
 */
public class Style {

  private Style() {
    // Versteckt den impliziten öffentlichen Konstruktor
  }

  public static String getBarsstyle() {
    return "-fx-background-color: linear-gradient(to bottom, #cce7f7, #b3e5fc);"
      + " -fx-border-color: #a0c4d6; "
      + "-fx-border-width: 2px; "
      + "-fx-border-style: solid; "
      + "-fx-padding: 2px;";
  }

  public static String sliderStyle() {
    return "-fx-control-inner-background: #f0faff; "
      + "-fx-background-color: linear-gradient(to right, #a0e1e5, #87ceeb); "
      + "-fx-border-color: #00796b; "
      + "-fx-border-width: 2px; "
      + "-fx-border-radius: 8px; "
      + "-fx-padding: 2px; "
      + "-fx-font-size: 10px; "
      + "-fx-tick-label-fill: #003366; "
      + "-fx-tick-mark-stroke: #003366; "
      + "-fx-slider-thumb: linear-gradient(to bottom, #ffffff, #e0f7fa); "
      + "-fx-slider-track-fill: #d0e9f5;"
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2);";
  }

  public static String separatorStyle() {
    return "-fx-background-color: transparent; "
      + "-fx-padding: 1px;"
      + "-fx-opacity: 0.8;";
  }

  public static String separator2Style() {
    return "-fx-background-color: transparent; "
      + "-fx-pref-height: 10px;"
      + "-fx-padding: 1px;"
      + "-fx-opacity: 0.8;";
  }

  public static String labelStyle() {
    return "-fx-font-size: 14px;"
      + "-fx-background-color: #f0f0f0;"
      + "-fx-background-radius: 2px;"
      + "-fx-font-weight: bold;"
      + "-fx-control-inner-background: #f0faff; "
      + "-fx-background-color: linear-gradient(to right, #a0e1e5, #87ceeb); "
      + "-fx-border-color: #00796b; "
      + "-fx-border-width: 2px; "
      + "-fx-border-radius: 5px; "
      + "-fx-padding: 5px; "
      + "-fx-font-size: 12px; "
      + "-fx-tick-label-fill: #003366; "
      + "-fx-tick-mark-stroke: #003366; "
      + "-fx-slider-thumb: linear-gradient(to bottom, #ffffff, #e0f7fa); "
      + "-fx-slider-track-fill: #d0e9f5;"
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2);";
  }

  public static String getAppStyle() {
    return "-fx-background-color: linear-gradient(to bottom, #e0f7fa,"
      + " #ffffff); "
      + "-fx-border-color: #d3eaf5; "
      + "-fx-border-width: 2px; "
      + "-fx-border-style: solid;";
  }

  public static String buttonsStyle() {
    return "-fx-background-color: #e0e0e0;"
      + "-fx-border-color: #cccccc;"
      + "-fx-background-size: 16px 16px;"
      + "-fx-background-repeat: no-repeat;"
      + "-fx-border-color: #00796b; "
      + "-fx-background-position: center;"
      + "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.5) , 10, 0 , 0, 2 );"
      + "-fx-cursor: hand;"
      + "-fx-border-radius: 5px;"
      + "-fx-border-width: 2px; "
      + "-fx-background-radius: 5px;"
      + "-fx-background-color: linear-gradient(to bottom, #d0d0d0, #e0e0e0),"
      + " #e0e0e0;"
      + "-fx-background-insets: 0, 1;"
      + "-fx-background-color: linear-gradient(to bottom, #c0c0c0, #d0d0d0),"
      + " #d0d0d0;"
      + "-fx-text-fill: #004d40;";
  }

  public static String buttons2Style() {
    return "-fx-background-color: linear-gradient(to bottom, #d0e7ff, #80c9ff);"
      + "-fx-border-color: #0083b0;"
      + "-fx-focus-color: #66ccff;"
      + "-fx-faint-focus-color: #a0e7ff;"
      + "-fx-mark-color: #004080;"
      + "-fx-control-inner-background: linear-gradient(to bottom, #d0f0ff,"
      + " #a0dfff);"
      + "-fx-border-width: 2px;"
      + "-fx-border-radius: 3px;"
      + "-fx-background-radius: 3px;"
      + "-fx-padding: 5px 10px;"
      + "-fx-font-size: 14px;"
      + "-fx-cursor: hand;";
  }

  public static String rectStandardstil() {
    return "-fx-stroke: #66ccff;"
      + "-fx-stroke-width: 3;"
      + "-fx-stroke-dash-array: 5 5;"
      + "-fx-stroke-line-cap: round;"
      + "-fx-stroke-dash-offset: 0;"
      + "-fx-border-radius: 5;";
  }

  public static String rectOnClick() {
    return "-fx-stroke: gold;"
      + "-fx-stroke-width: 5;"
      + "-fx-arc-width: 10;"
      + "-fx-arc-height: 10;"
      + "-fx-transition: all 0.2s ease;";
  }

  public static String colorPickerStyle() {
    return "-fx-background-color: linear-gradient(to bottom, #d0e7ff, #80c9ff);"
      + "-fx-border-color: #0083b0;"
      + "-fx-border-width: 2px;"
      + "-fx-border-radius: 5px;"
      + "-fx-background-radius: 5px;"
      + "-fx-padding: 0px;"
      + "-fx-focus-color: #66ccff;"
      + "-fx-faint-focus-color: #a0e7ff;"
      + "-fx-mark-color: #004080;"
      + "-fx-control-inner-background: linear-gradient(to bottom, #d0f0ff,"
      + " #a0dfff);"
      + "-fx-color-label-visible: true;"
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.0, 0, 2);"
      + "-fx-font-size: 12px;";
  }

  public static String radioStyle() {
    return "-fx-mark-color: #66ccff;"
      + "-fx-border-color: #0083b0;"
      + "-fx-border-width: 2px;"
      + "-fx-border-radius: 25%;"
      + "-fx-background-radius: 25%;"
      + "-fx-padding: 3px;"
      + "-fx-background-color: linear-gradient(to bottom, #d0e7ff, #80c9ff);"
      + "-fx-focus-color: #66ccff;"
      + "-fx-selected-color: #66ccff;"
      + "-fx-font-size: 11px;"
      + "-fx-font-weight: bold;"
      + "-fx-text-fill: #444444;"
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.0, 0, 2);";
  }

  public static String scrollPaneStyle() {
    return "-fx-background-color: linear-gradient(to bottom, #e0f7fa, #f0faff);"
      + " -fx-border-color: #d0e9f5; "
      + "-fx-border-width: 1px; "
      + "-fx-padding: 5px; ";
  }

  public static String scrollBarStyle() {
    return "-fx-background-color: #d0e9f5; "
      + "-fx-border-color: #b0d4e0; "
      + "-fx-border-width: 2px; "
      + "-fx-pref-width: 18px; "
      + "-fx-pref-height: 18px;";
  }

  public static String scrollBarThumbStyle() {
    return "-fx-background-color: linear-gradient(to bottom, #6a8fa3,"
      + " #527a8a);"
      + " -fx-border-radius: 5px; "
      + "-fx-background-radius: 5px; "
      + "-fx-padding: 3px; "
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1); ";
  }

  public static String getTooltipStyle() {
    return "-fx-background-color: rgba(255, 255, 255, 0.85); "
      + "-fx-background-radius: 8px; "
      + "-fx-padding: 5px; "
      + "-fx-font-size: 12px; "
      + "-fx-text-fill: #003366; "
      + "-fx-border-color: #87ceeb; "
      + "-fx-border-width: 1px; "
      + "-fx-border-radius: 8px;";
  }

  public static String getTextFieldStyle() {
    return "-fx-border-color: lightgray;"
      + "-fx-border-width: 1px;"
      + "-fx-background-color: white;";
  }

  public static String getErrorTextFieldStyle() {
    return "-fx-border-color: red;"
      + "-fx-background-color: #ffe6e6;";
  }

  public static String getValidTextFieldStyle() {
    return "-fx-border-color: green;"
      + "-fx-background-color: #e6ffe6;";
  }

  public static String recttOnClick() {
    return "-fx-text-fill: white;"
      + "-fx-border-color: #00cee1;"
      + "-fx-border-width: 2px;";
  }

  public static String rectAktiviertStil() {
    return "-fx-stroke: green;"
      + "-fx-stroke-width: 4;"
      + "-fx-border-radius: 5;";
  }

  public static String warningStyle() {
    return "-fx-font-size: 12px;"
      + "-fx-background-color: #f0f0f0;"
      + "-fx-background-radius: 2px;"
      + "-fx-font-weight: bold;"
      + "-fx-control-inner-background: #f0faff; "
      + "-fx-background-color: linear-gradient(to right, #a0e1e5,"
      + " #87ceeb); "
      + "-fx-border-color: #00796b; "
      + "-fx-border-width: 2px; "
      + "-fx-border-radius: 3px; "
      + "-fx-padding: 3px; "
      + "-fx-tick-label-fill: #003366; "
      + "-fx-tick-mark-stroke: #003366; "
      + "-fx-slider-thumb: linear-gradient(to bottom, #ffffff, #e0f7fa); "
      + "-fx-slider-track-fill: #d0e9f5;"
      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2);"
      + "-fx-text-fill: black;";
  }
}