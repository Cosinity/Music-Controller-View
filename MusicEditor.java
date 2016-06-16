package cs3500.music;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.MusicModel;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView view = new GuiViewFrame();
    IMusicView midiView = new MidiViewImpl();
    IMusicView console = new ConsoleView();
    FileReader file = new FileReader(args[0]);
    MusicModel.Builder builder = new MusicModel.Builder();
    IMusicController controller = new MusicController(
            MusicReader.parseFile(file, builder));
    String typeView = args[1];
    switch (typeView.toLowerCase().charAt(0)) {
      case 'g': controller.play(view);
        break;
      case 'm': controller.play(midiView);
        break;
      case 'c': controller.play(console);
        break;
      default:
        throw new IllegalArgumentException("Invalid display type");
    }
  }
}
