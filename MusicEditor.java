package cs3500.music;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.InteractiveMusicController;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewFactory;
import cs3500.music.view.IMusicView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    try {
      IMusicView view = ViewFactory.make(args[1]);
      FileReader file = new FileReader(args[0]);
      MusicModel.Builder builder = new MusicModel.Builder();
      IMusicController<Note> controller = new InteractiveMusicController
              (MusicReader.parseFile(file, builder));
      controller.play(view);
    } catch (FileNotFoundException ex) {
      System.out.println("File " + args[0] + " not found");
      System.exit(1);
    } catch (Exception ex) {
      System.out.println("Runtime exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(2);
    }
  }
}