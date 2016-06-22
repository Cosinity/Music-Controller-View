package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Represents the functionality of a view for MusicPlayer with interactability
 */
public interface InteractiveView<T> extends IMusicView<T> {
  // Note-specific methods
  /**
   * Selects the given note. Specifically, puts a colored outline around the note and updates the
   * text fields with its parameters.
   */
  void selectNote(Note n);

  /**
   * Gets the input from the duration text field
   */
  String getNewDuration();

  /**
   * Gets the input from the starting beat text field
   */
  String getNewStartBeat();

  /**
   * Gets the input from the pitch text field
   */
  String getNewPitch();

  // Piece-wide methods
  /**
   * Gets the input from the tempo text field
   */
  String getNewTempo();

  /**
   * Gets the input from the transposition text field
   */
  String getTranspose();

  /**
   * Returns whichever note is at the given x and y coordinates
   */
  T noteAtPos(int x, int y);
}
