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
    if (typeOfView.toLowerCase().equals("g") || typeOfView.toLowerCase().equals("gui")
            || typeOfView.toLowerCase().equals("visual")) {
      return new GuiViewFrame();
    } else if (typeOfView.toLowerCase().equals("m") || typeOfView.toLowerCase().equals("midi")) {
      return new MidiViewImpl();
    } else if (typeOfView.toLowerCase().equals("c") || typeOfView.toLowerCase().equals("console")) {
      return new ConsoleView();
    } else {
      throw new IllegalArgumentException("No such view");
    }
  }
}