package cs3500.music.view;

/**
 * Addition to the View interface that allows for fetching of beats
 */
public interface MidiView<Note> extends IMusicView<Note> {
  /**
   * Returns the beat that's currently playing in the song
   * @return the beat that's currently playing in the song
   */
  long getBeats();

  /**
   * Pauses the playback of the MIDI view
   */
  void pause();

  /**
   * Unpauses the playback of the MIDI view
   */
  void unpause();
}
