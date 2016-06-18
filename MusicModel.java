package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import cs3500.music.util.CompositionBuilder;

/**
 * A Model representing a musical composition.
 */
public class MusicModel implements GenericMusicPiece<Note> {
  /**
   * List for keeping the notes that are in the composition.
   */
  private List<Note> notes;
  /**
   * Tracks the composition's total length.
   */
  private int totalLength;
  /**
   * Determines whether headers are rendered as flats or sharps.
   */
  private boolean isFlat;

  /**
   * Default constructor - initializes the notes list to empty, total length to 0, and turns off
   * rendering of headers as flat.
   */

  /**
   * The tempo of the piece in microseconds per beat
   */
  private int tempo;

  public MusicModel() {
    this.notes = new ArrayList<Note>();
    this.totalLength = 0;
    this.isFlat = false;
    this.tempo = 500000;
  }

  /**
   * Convenience constructor. Calls default constructor then initializes the note list to match
   * a passed list of notes.
   *
   * @param notesToAdd The list of notes the user wishes to be in the composition from the get-go.
   */
  public MusicModel(List<Note> notesToAdd) {
    this();
    notesToAdd.forEach(this::addNote);
  }

  /**
   * Sets up a music model with the given tempo
   *
   * @param tempo the given tempo for this piece
   */
  public MusicModel(int tempo) {
    this();
    this.tempo = tempo;
  }

  /**
   * Sets up a music model with the given tempo and list of notes
   *
   * @param tempo the given tempo for this piece
   * @param toAdd the notes to be added
   */
  public MusicModel(int tempo, List<Note> toAdd) {
    this(toAdd);
    this.tempo = tempo;
  }

  @Override
  public void addNote(Note toAdd) {
    this.notes.add(toAdd);
    this.totalLength = Math.max(this.totalLength, toAdd.getDuration() + toAdd.getStartTime() - 1);
  }

  /**
   * Adds all the notes in the specified list of notes to the model's notelist
   * Essentially a helper method for the mix and append methods
   *
   * @param notesToAdd notes to add to the model's note list
   */
  private void addAllNotes(List<Note> notesToAdd) {
    notesToAdd.forEach(this::addNote);
  }

  @Override
  public void appendTo(GenericMusicPiece other) {
    ArrayList<Note> notesToSend = new ArrayList<Note>();
    for (Note n : this.notes) {
      notesToSend.add(n.copy());
    }
    other.append(notesToSend);
  }

  @Override
  public void append(List<Note> other) {
    for (Note n : other) {
      n.changeStart(this.totalLength);
    }
    this.addAllNotes(other);
  }

  @Override
  public void mixTo(GenericMusicPiece other) {
    ArrayList<Note> notesToSend = new ArrayList<Note>();
    for (Note n : this.notes) {
      notesToSend.add(n.copy());
    }
    other.mix(notesToSend);
  }

  @Override
  public void mix(List<Note> other) {
    this.addAllNotes(other);
  }

  @Override
  public void remove(Note n) {
    this.notes.remove(n);
  }

  @Override
  public void transpose(int semitones) {
    this.notes.forEach(n -> n.transpose(semitones));
  }

  @Override
  public void toggleFlat() {
    this.isFlat = !this.isFlat;
    this.notes.forEach(Note::toggleFlat);
  }

  @Override
  public List<Note> getNotes() {
    List<Note> toReturn = new ArrayList<Note>();
    for (Note n : this.notes) {
      toReturn.add(n.copy());
    }
    return toReturn;
  }

  @Override
  public List<Note> getNotes(int time) {
    List<Note> toReturn = new ArrayList<Note>();
    for (Note n : this.notes) {
      if (n.getStartTime() == time) {
        toReturn.add(n.copy());
      }
    }
    return toReturn;
  }

  @Override
  public void changeStart(int b) {
    this.notes.forEach(n -> n.changeStart(b));
  }

  @Override
  public void changePitch(Note n, int s) {
    if (this.notes.contains(n)) {
      int index = this.notes.indexOf(n);
      this.notes.get(index).transpose(s);
    }
  }

  @Override
  public boolean isFlat() {
    return this.isFlat;
  }

  @Override
  public void setTempo(int t) {
    this.tempo = t;
  }

  @Override
  public void changeDuration(Note n, int b) {
    if (this.notes.contains(n)) {
      int index = this.notes.indexOf(n);
      this.notes.get(index).changeDuration(b);
    }
  }

  @Override
  public void changeStart(Note n, int b) {
    if (this.notes.contains(n)) {
      int index = this.notes.indexOf(n);
      this.notes.get(index).changeStart(b);
    }
  }

  @Override
  public long getTempo() {
    return this.tempo;
  }

  /**
   * Music Model Builder
   */
  public static final class Builder implements CompositionBuilder<GenericMusicPiece> {

    private GenericMusicPiece model;

    /**
     * Makes a new model
     */
    public Builder() {
      this.model = new MusicModel();
    }

    /**
     * Makes a builder with a given model
     * @param m the given model
     */
    public Builder(GenericMusicPiece m) {
      this.model = m;
    }

    /**
     * Builds a model from a list of notes
     * @param list notes to build a model from
     */
    public Builder(List<Note> list) {
      this.model = new MusicModel(list);
    }

    @Override
    public GenericMusicPiece build() {
      return this.model;
    }

    @Override
    public CompositionBuilder<GenericMusicPiece> setTempo(int tempo) {
      this.model.setTempo(tempo);
      return this;
    }

    @Override
    public CompositionBuilder<GenericMusicPiece> addNote(int start, int end, int instrument,
                                                         int pitch, int volume) {
      Pitch p = Pitch.values()[pitch % Pitch.values().length];
      int octave = (pitch / Pitch.values().length);
      int duration = end - start + 1;
      Note toAdd = new Note(p, octave, duration, start, instrument, volume);
      this.model.addNote(toAdd);
      return this;
    }
  }
}
