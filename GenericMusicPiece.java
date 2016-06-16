package cs3500.music.model;

import java.util.List;

/**
 * Interface for a general representation of a musical composition/piece
 */
public interface GenericMusicPiece {

  /**
   * Adds all the notes from this piece to the other by passing it this piece's note list.
   * Changes the start times of this piece's notes so that it is added to the end of the
   * other piece
   * @param other The music piece that this is going to be added to
   */
  void appendTo(GenericMusicPiece other);

  /**
   * Adds all the notes from a given list of notes
   * @param other The notes that are to be added to this piece.
   */
  void append(List<Note> other);

  /**
   * Adds all the notes from this piece to the other by passing it this piece's note list.
   * @param other The music piece that is to be added to this one.
   */
  void mixTo(GenericMusicPiece other);

  /**
   * Adds all the notes in an arraylist to this piece's list of notes
   * @param other The notes that are to be added to this piece
   */
  void mix(List<Note> other);

  /**
   * Transposes the piece up or down the given amount of semitones
   * @param semitones the amount of semitones to transpose the piece by
   */
  void transpose(int semitones);

  /**
   * Add a note to the music piece
   * @param toAdd the note to be added
   */
  void addNote(Note toAdd);

  /**
   * Remove a specific note from the piece. If the note doesn't exist, the method does nothing.
   * @param n The note to remove from the piece
   */
  void remove(Note n);

  /**
   * Toggle piece's notes between being rendered as sharp and flat
   */
  void toggleFlat();

  /**
   * Returns the full list of notes (cloned, of course)
   * @return the full list of notes in the song
   */
  List<Note> getNotes();

  /**
   * Returns the notes at a specific starting time
   * @param time the time from which to retrieve notes
   * @return the notes at a specific starting time
   */
  List<Note> getNotes(int time);

  /**
   * Changes the start time of the piece
   * @param b the amount of beats to change the notes' start times by
   */
  void changeStart(int b);

  /**
   * Changes the start time of a specific note
   * @param n the note to change the startTime of
   * @param b the amount of beats to change the notes start time by
   */
  void changeStart(Note n, int b);

  /**
   * Changes the duration of a specific note
   * @param n the note to change the duration of
   * @param b the amount of beats to change the note's duration by
   */
  void changeDuration(Note n, int b);


  /**
   * Change a specific note's pitch by this many semitones
   * @param n the note to be edited
   * @param s the amount of semitones to be transposed by
   */
  void changePitch(Note n, int s);

  /**
   * Returns whether the piece is currently flat
   * @return whether thepiece is currently flat
   */
  boolean isFlat();
}