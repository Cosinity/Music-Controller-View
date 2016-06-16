package cs3500.music.view;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.GenericMusicPiece;

/**
 * Represents the functionality of a MusicPlayer view
 */
public interface IMusicView {
  /**
   * Plays the given piece in the manner of this view (e.g. displays it to a GUI, plays the
   * notes' audio, etc.)
   */
  void play(IMusicController piece);
}
