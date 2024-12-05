package de.krenbeh.gui.colorpicker;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The Colors class provides utility functions for managing a unique set of colors.
 * It maintains a static list of colors and allows for selecting a random color
 * from the list, ensuring that the same color is not selected more than once.
 */
abstract class Colors {
  private static final List<Color> farben = new ArrayList<>();
  private static final Random random = new Random();

  private static void initializeFarben() {
    farben.clear();
    farben.addAll(Arrays.asList(
      Color.CYAN, Color.TEAL, Color.AQUA, Color.ORANGE, Color.BLUE, Color.YELLOW,
      Color.DARKBLUE, Color.DARKCYAN, Color.DARKGRAY, Color.DARKGREEN,
      Color.DARKMAGENTA, Color.DARKRED, Color.FUCHSIA, Color.GAINSBORO,
      Color.LIGHTBLUE, Color.GRAY, Color.GREEN, Color.LIGHTGREEN,
      Color.LIGHTYELLOW, Color.LIME, Color.MAGENTA, Color.MAROON,
      Color.MEDIUMBLUE, Color.NAVY, Color.OLIVE, Color.PINK, Color.PURPLE,
      Color.RED
    ));
  }

  private Colors() {
    throw new IllegalStateException("Utility class");
  }

  // Private constructor to prevent instantiation
  public static Color ranFarbe() {
    if (farben.isEmpty()) {
      initializeFarben(); // Re-initialize if empty
    }
    int index = random.nextInt(farben.size());
    Color select = farben.get(index);

    // Remove the selected color from the list to ensure uniqueness
    farben.remove(index);
    return select;
  }



}