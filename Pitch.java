package cs3500.music.model;

/**
 * Created by Nick on 2016-06-07.
 * Changed for hw07 to clean it up and add a create() method
 */
public enum Pitch {
  C("C"), C_SHARP("C#"), D("D"), D_SHARP("D#"), E("E"), F("F"), F_SHARP("F#"), G("G"), G_SHARP
          ("G#"), A("A"), A_SHARP("A#"), B("B");

  boolean flat;
  String name;
  String flatName;

  /**
   * Default pitch constructor - defaults to sharps instead of flats
   */
  Pitch() {
    this.flat = false;
  }

  /**
   * Constructor with the name of the pitch
   */
  Pitch(String name) {
    this.name = name;
    this.flatName = name;
    if (this.name.contains("#")) {
      if (name.charAt(0) == 'G') {
        this.flatName = "Ab";
      } else {
        int charValue = name.charAt(0);
        this.flatName = String.valueOf((char) (charValue + 1)) + "b";
      }
    }
    this.flat = false;
  }

  /**
   * Toggles between flats and sharps
   */
  public void toggleFlat() {
    this.flat = !this.flat;
  }

  /**
   * Renders the pitch as a string
   * @return abbreviated String representation of the note
   */
  public String toString() {
    if (this.flat) {
      return this.flatName;
    } else {
      return this.name;
    }
  }

  /**
   * Creates a new Pitch from the given string
   *
   * @return the new pitch
   */
  public static Pitch create(String s) {
    for (Pitch p : values()) {
      if (p.name.equals(s.toUpperCase())) {
        return p;
      }
    }
    throw new IllegalArgumentException("Illegal pitch name");
  }
}