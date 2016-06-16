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

/**
 * A Model representing a musical composition.
 */
public class MusicModel implements GenericMusicPiece {
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
  public MusicModel() {
    this.notes = new ArrayList<Note>();
    this.totalLength = 0;
    this.isFlat = false;
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
    for (Note n: this.notes) {
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
    for (Note n: this.notes) {
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
    for (Note n: this.notes) {
      toReturn.add(n.copy());
    }
    return toReturn;
  }

  @Override
  public List<Note> getNotes(int time) {
    List<Note> toReturn = new ArrayList<Note>();
    for (Note n: this.notes) {
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
}
