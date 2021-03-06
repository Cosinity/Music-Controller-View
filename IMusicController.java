
package cs3500.music.controller;

import java.util.List;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicView;

/**
 * Represents the functionality of a MusicPlayer controller
 */
public interface IMusicController<T> {

  /**
   * Plays the given piece in the manner of the given view (e.g. displays it to a GUI, plays
   * the notes' audio, etc.)
   */
  void play(IMusicView<Note> view);

  /**
   * Appends a music piece to the controller's music piece.
   */
  void append(GenericMusicPiece<T> m);

  /**
   * Appends a music piece to the controller's music piece.
   *
   * @param m the music piece
   */
  void mix(GenericMusicPiece<T> m);

  /**
   * Add a note to the controller's music piece.
   *
   * @param n the note to be added
   */
  void addNote(T n);

  /**
   * Remove a note from the controller's music piece.
   *
   * @param n the note to be removed
   */
  void removeNote(T n);

  /**
   * Fetch all the notes in the piece
   *
   * @return the notes in the piece
   */
  List<Note> getNotes();

  /**
   * Fetch all the notes on a particular beat
   *
   * @param b the beat which to check the currently playing notes on
   * @return the notes on that beat
   */
  List<Note> getNotes(int b);

  /**
   * Change a specific note's pitch by this many semitones
   *
   * @param n the note to be edited
   * @param s the amount of semitones to be added/subtracted from the note's pitch
   */
  void changePitch(T n, int s);

  /**
   * Change the entire piece's pitch by this many semitones
   *
   * @param s the amount of semitones to be added/subtracted from the piece's pitches
   */
  void transpose(int s);

  /**
   * Change a specific note's start time by this many beats
   *
   * @param n the note to be edited
   * @param s the amount of beats to be added/subtracted from the note's start time
   */
  void changeStart(T n, int s);

  /**
   * Change the piece's start time by this many beats
   *
   * @param s the amount of beats to be added/subtracted from the piece's start time
   */
  void changeStart(int s);

  /**
   * Change a specific note's duration by this many beats
   *
   * @param n the note to be edited
   * @param s the amount of beats to be added/subtracted from the note's duration
   */
  void changeDuration(T n, int s);

  /**
   * Returns whether the piece is currently flat
   */
  boolean isFlat();

  /**
   * Fetches the piece's tempo
   */
  long getTempo();

  /**
   * Changes a piece's tempo
   */
  void setTempo(int toSet);
}