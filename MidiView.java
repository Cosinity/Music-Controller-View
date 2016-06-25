package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Addition to the View interface that allows for fetching of beats, pausing, and restarting
 */
public interface MidiView<T> extends IMusicView<T> {
  /**
   * Returns the beat that's currently playing in the song
   * @return the beat that's currently playing in the song
   */
  long getBeat();

  /**
   * Pauses the playback of the MIDI view
   */
  void pause();

  /**
   * Unpauses the playback of the MIDI view
   */
  void unpause();

  /**
   * Restarts the playback of the MIDI
   */
  void restart();
}
