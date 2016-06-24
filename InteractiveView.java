package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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

  /**
   * Sets the keyboard listener of the interactive view to the one that is given
   * @param k the keyboard listener to set to
   */
  void addKeyListener(KeyListener k);

  /**
   * Sets the mouse listener of the interactive view to the one that is given
   * @param m the mouse listener to set to
   */
  void addMouseListener(MouseListener m);

  /**
   * Moves the view to the start of the song
   */
  void goToStart();

  /**
   * Moves the view to the end of the song
   */
  void goToEnd();

  /**
   * Toggles whether the piece is currently playing
   */
  void togglePause();

  /**
   * Resets focus to the part of the view with the keyboard listener attached
   */
  void resetFocus();

  void addActionListener(ActionListener al);
}