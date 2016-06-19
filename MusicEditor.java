package cs3500.music;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.MusicModel;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewFactory;
import cs3500.music.view.IMusicView;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView view = ViewFactory.make(args[1]);
    FileReader file = new FileReader(args[0]);
    MusicModel.Builder builder = new MusicModel.Builder();
    IMusicController controller = new MusicController(
            MusicReader.parseFile(file, builder));
    controller.play(view);
  }
}
