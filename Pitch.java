package cs3500.music.model;

/**
 * Created by Nick on 2016-06-07.
 */
public enum Pitch {
  C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B;

  boolean flat;

  /**
   * Default pitch constructor - defaults to sharps instead of flats
   */
  Pitch() {
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
    String thisNote = this.name();
    if (thisNote.contains("SHARP")) {
      if (this.flat) {
        thisNote = values()[this.ordinal() + 1].toString();
        return thisNote.substring(0, 1) + "b";
      }
      return thisNote.substring(0, 1) + "#";
    }
    return thisNote;
  }
}