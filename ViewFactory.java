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

  public static IMusicView make(String typeOfView) {
    if (typeOfView.equals("g") || typeOfView.equals("gui") || typeOfView.equals("GUI")) {
      return new GuiViewFrame();
    } else if (typeOfView.equals("m") || typeOfView.equals("midi") || typeOfView.equals("MIDI")) {
      return new MidiViewImpl();
    } else if (typeOfView.equals("c") || typeOfView.equals("console") || typeOfView.equals
            ("Console")) {
      return new ConsoleView();
    } else {
      throw new IllegalArgumentException("No such view");
    }
  }
}
