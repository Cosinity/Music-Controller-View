package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.tests.mocks.SequenceMock;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.*;

/**
 * A series of tests for the MIDI view
 */
public class MidiViewImplTest {

  private IMusicController<Note> myControl;
  private IMusicController<Note> maryControl;
  private MidiViewImpl playMidi;


  /**
   * Init method for initializing the testing materials
   */
  private void init() {
    Sequence seq = null;
    try {
      seq = new SequenceMock(Sequence.PPQ, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<Note> myExample = new ArrayList<Note>() {
      {
        add(new Note(Pitch.C, 1, 4, 0));
        add(new Note(Pitch.C, 2, 4, 0));
        add(new Note(Pitch.C, 3, 4, 0));
        add(new Note(Pitch.D, 1, 4, 4));
        add(new Note(Pitch.D, 2, 4, 4));
        add(new Note(Pitch.D, 3, 4, 4));
        add(new Note(Pitch.E, 1, 4, 0));
        add(new Note(Pitch.E, 2, 4, 0));
        add(new Note(Pitch.E, 3, 4, 0));
        add(new Note(Pitch.F, 1, 4, 4));
        add(new Note(Pitch.F, 2, 4, 4));
        add(new Note(Pitch.F, 3, 4, 4));
        add(new Note(Pitch.G, 1, 4, 0));
        add(new Note(Pitch.G, 2, 4, 0));
        add(new Note(Pitch.G, 3, 4, 0));
        add(new Note(Pitch.A, 1, 4, 4));
        add(new Note(Pitch.A, 2, 4, 4));
        add(new Note(Pitch.A, 3, 4, 4));
        add(new Note(Pitch.B, 1, 4, 0));
        add(new Note(Pitch.B, 2, 4, 0));
        add(new Note(Pitch.B, 3, 4, 0));
      }
    };

    List<Note> mary = new ArrayList<Note>() {{
      add(new Note(Pitch.G, 3, 7, 0));
      add(new Note(Pitch.G, 3, 7, 8));
      add(new Note(Pitch.G, 3, 8, 16));
      add(new Note(Pitch.G, 3, 2, 24));
      add(new Note(Pitch.G, 3, 8, 32));
      add(new Note(Pitch.G, 3, 8, 40));
      add(new Note(Pitch.G, 3, 8, 48));
      add(new Note(Pitch.E, 4, 2, 0));
      add(new Note(Pitch.E, 4, 2, 8));
      add(new Note(Pitch.E, 4, 2, 10));
      add(new Note(Pitch.E, 4, 3, 12));
      add(new Note(Pitch.E, 4, 2, 24));
      add(new Note(Pitch.E, 4, 2, 32));
      add(new Note(Pitch.E, 4, 2, 40));
      add(new Note(Pitch.E, 4, 2, 42));
      add(new Note(Pitch.E, 4, 2, 44));
      add(new Note(Pitch.E, 4, 2, 46));
      add(new Note(Pitch.E, 4, 2, 52));
      add(new Note(Pitch.C, 4, 2, 4));
      add(new Note(Pitch.C, 4, 2, 36));
      add(new Note(Pitch.C, 4, 8, 56));
      add(new Note(Pitch.E, 3, 8, 56));
      add(new Note(Pitch.G, 4, 2, 26));
      add(new Note(Pitch.G, 4, 4, 28));
      add(new Note(Pitch.D, 4, 2, 2));
      add(new Note(Pitch.D, 4, 2, 6));
      add(new Note(Pitch.D, 4, 2, 16));
      add(new Note(Pitch.D, 4, 5, 18));
      add(new Note(Pitch.D, 4, 4, 20));
      add(new Note(Pitch.D, 4, 2, 34));
      add(new Note(Pitch.D, 4, 2, 38));
      add(new Note(Pitch.D, 4, 2, 48));
      add(new Note(Pitch.D, 4, 2, 50));
      add(new Note(Pitch.D, 4, 2, 54));
    }};


    GenericMusicPiece<Note> myPiece = new MusicModel(myExample);
    GenericMusicPiece<Note> maryPiece = new MusicModel(mary);
    this.myControl = new MusicController(myPiece);
    this.maryControl = new MusicController(maryPiece);
    this.playMidi = new MidiViewImpl(seq);
  }

  @Test
  public void playMyExample() {
    init();
    this.myControl.play(playMidi);
    String log = SequenceMock.log.toString();
    assertTrue(log.startsWith("Beat: 0. Note On. Instrument: 0. Key: 12. Volume: 100."));
    int noteOff = 0;
    int noteOn = 0;
    Matcher off = Pattern.compile("\\bNote Off.\\b").matcher(log);
    Matcher on = Pattern.compile("\\bNote On.\\b").matcher(log);
    while (off.find()) {
      noteOff++;
    }
    while (on.find()) {
      noteOn++;
    }
    assertEquals(noteOff, noteOn); // same amount of notes off and on
    assertTrue(log.contains("Beat: 8. Note Off. Instrument: 0. Key: 17. Volume: 100."));
  }

  @Test
  public void playMary() {
    init();
    this.maryControl.play(playMidi);
    System.out.print(SequenceMock.log.toString());
    String log = SequenceMock.log.toString();
    assertTrue(log.startsWith("Beat: 0. Note On. Instrument: 0. Key: 43. Volume: 100."));
    int noteOff = 0;
    int noteOn = 0;
    Matcher off = Pattern.compile("\\bNote Off.\\b").matcher(log);
    Matcher on = Pattern.compile("\\bNote On.\\b").matcher(log);
    while (off.find()) {
      noteOff++;
    }
    while (on.find()) {
      noteOn++;
    }
    assertEquals(noteOff, noteOn); // same amount of notes off and on
    assertTrue(log.contains("Beat: 64. Note Off. Instrument: 0. Key: 40. Volume: 100."));
  }

}