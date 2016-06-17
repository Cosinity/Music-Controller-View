package cs3500.music.util;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiViewImpl;

/**
 * Creates a view based on what string is passed
 */
public class ViewFactory {
  GenericMusicPiece model;
  public ViewFactory(GenericMusicPiece model) {
    this.model = model;
  }

  public IMusicView make(String typeOfView) {
    switch (typeOfView.toLowerCase()) {
      case "visual" : return new GuiViewFrame();
      case "midi": return new MidiViewImpl();
      case "console": return new ConsoleView();
      default:
        throw new IllegalArgumentException("Invalid display type!");
    }
  }
}
