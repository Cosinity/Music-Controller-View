package cs3500.music.model;

import java.util.Objects;

/**
 * Created by Nick on 2016-06-07.
 */
public class Note implements Comparable<Note> {
  private Pitch pitch;
  private int octave;
  private int duration;
  private int startTime;
  private int instrument;
  private int volume;

  /**
   * Constructor for a new Note
   *
   * @param p The pitch of the new note
   * @param o The octave of the new note
   * @param d The duration (in beats) of the new note
   * @param s Start time for the new note
   * @throws IllegalArgumentException when passed null, sub-zero, or too large arguments
   */
  public Note(Pitch p, int o, int d, int s) throws IllegalArgumentException {
    if (p == null) {
      throw new IllegalArgumentException("Cannot use null pitch or octave.");
    }
    if (d < 0 || s < 0) {
      throw new IllegalArgumentException("Cannot have negative duration or start time");
    }
    if (d == 0) {
      throw new IllegalArgumentException("Cannot have 0 duration.");
    }
    if (o < 0) {
      throw new IllegalArgumentException("Cannot have negative octave.");
    }
    this.pitch = p;
    this.octave = o;
    this.duration = d;
    this.startTime = s;
    this.instrument = 0;
    this.volume = 100;
  }

  /**
   * Constructor for a new Note
   *
   * @param p The pitch of the new note
   * @param o The octave of the new note
   * @param d The duration (in beats) of the new note
   * @param s Start time for the new note
   * @param i Instrument played by the MIDI player
   * @param v Volume of the played note
   * @throws IllegalArgumentException when passed null, sub-zero, or too large arguments
   */
  public Note(Pitch p, int o, int d, int s, int i, int v) throws IllegalArgumentException {
    this(p, o, d, s);
    if (i < 0) {
      throw new IllegalArgumentException("Invalid instrument.");
    }
    this.instrument = i;
    if (v < 0) {
      throw new IllegalArgumentException("Invalid volume.");
    }
    this.volume = v;

  }


  /**
   * Makes a copy of this note
   * @return a copy of this note
   */
  public Note copy() {
    return new Note(this.pitch, this.octave, this.duration, this.startTime);
  }


  /**
   * Getter method for note pitch
   *
   * @return This note's pitch
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Getter method for note octave
   *
   * @return This note's octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Getter method for note duration
   *
   * @return This note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Getter method for note start time
   *
   * @return This note's start time
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Transposes the note up/down the given amount of semitones
   *
   * @param semitones Amount of semitones to transpose the note
   */
  public void transpose(int semitones) {
    int pitchOrd = this.getPitch().ordinal();
    pitchOrd += semitones;
    Pitch[] pitches = Pitch.values();

    // If an upward change in octave is detected
    if (pitchOrd >= pitches.length) {
      this.octave += pitchOrd / pitches.length; // to account for movements of > 1 octave
      pitchOrd %= pitches.length;
    }
    // If a downward change in octave is detected
    else if (pitchOrd < 0) {
      this.octave += (pitchOrd - 12) / pitches.length; // to account for being just under octave
      pitchOrd = (pitchOrd + 1200) % pitches.length;
      if (pitchOrd < 0) {
        pitchOrd += pitches.length;
      }
      // There's no proper modulo operation in Java, it's a remainder function.
      // As a result when applying modulo with negative numbers, it doesn't work correctly
      // and you must add the number you're modding by to make the output correct.
    }

    this.pitch = pitches[pitchOrd];
  }

  /**
   * Renders the note as an up to 4 character long string
   */
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  /**
   * Changes the starting time by the given amount of beats
   *
   * @param beats Amount of beats by which to change the starting time
   */
  public void changeStart(int beats) {
    this.startTime += beats;
  }

  /**
   * Makes the note's pitch flat
   */
  public void toggleFlat() {
    this.pitch.toggleFlat();
  }

  /**
   * Compares one note to another based on octave and pitch
   *
   * @param o Note to be compared to
   * @return Negative number if this is smaller, 0 if equal, positive if greater than
   */
  public int compareTo(Note o) {
    if (this.getOctave() == o.getOctave() && this.getPitch().equals(o.getPitch())) {
      return this.getStartTime() - o.getStartTime();
    } else if (this.getOctave() == o.getOctave()) {
      return this.getPitch().compareTo(o.getPitch());
    } else {
      return this.getOctave() - o.getOctave();
    }
  }

  /**
   * Changes the duration of this note by the given amount of beats
   * @param b the amount of beats to extend/shorten this note's duration by
   */
  public void changeDuration(int b) throws IllegalArgumentException {
    int d = this.duration;
    d += b;
    if (d > 0) {
      this.duration = d;
    }
    else {
      throw new IllegalArgumentException("Cannot decrease duration to be less than 0.");
    }
  }

  /**
   * Returns the instrument the note is to be played on
   * @return the instrument the note is to be played on
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Returns the volume (velocity) the note is to be played at
   * @return the volume (velocity) the note is to be played at
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Compares equality with another object
   *
   * @param o other object to compare to
   * @return whether this note is equal to that object
   */
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o instanceof Note) {
      Note that = (Note) o;
      return (this.getPitch() == that.getPitch()
              && this.getOctave() == that.getOctave()
              && this.getDuration() == that.getDuration()
              && this.getStartTime() == that.getStartTime());
    }

    return false;
  }

  /**
   * Override for hashcode generation
   * @return the note's hashcode based off of its parameters
   */
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave, this.duration, this.startTime);
  }
}