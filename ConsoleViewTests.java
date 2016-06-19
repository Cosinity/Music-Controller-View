import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.ConsoleView;

import static org.junit.Assert.*;

/**
 * Tests for the Console View
 */
public class ConsoleViewTests {
  private IMusicController<Note> myController;
  private IMusicController<Note> maryController;
  private ConsoleView view;
  private OutputStream output;

  private void init() {
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
    this.myController = new MusicController(myPiece);
    this.maryController = new MusicController(maryPiece);
    this.output = new ByteArrayOutputStream();
    this.view = new ConsoleView(output);
  }

  @Test
  public void noMusic() {
    init();
    IMusicController<Note> control = new MusicController(new MusicModel());
    control.play(view);
    assertEquals("", output.toString());
  }

  @Test
  public void mySong() {
    init();
    myController.play(view);
    assertEquals("  C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  B1   C2   C#2  " +
            "D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3   D#3  E3   " +
            "F3   F#3  G3   G#3  A3   A#3  B3  \n" +
            "0  X                   X              X                   X    X           " +
            "        X              X                   X    X                   X      " +
            "        X                   X  \n" +
            "1  |                   |              |                   |    |           " +
            "        |              |                   |    |                   |      " +
            "        |                   |  \n" +
            "2  |                   |              |                   |    |           " +
            "        |              |                   |    |                   |      " +
            "        |                   |  \n" +
            "3  |                   |              |                   |    |           " +
            "        |              |                   |    |                   |      " +
            "        |                   |  \n" +
            "4            X              X                   X                        X " +
            "             X                   X                        X              X " +
            "                  X            \n" +
            "5            |              |                   |                        | " +
            "             |                   |                        |              | " +
            "                  |            \n" +
            "6            |              |                   |                        | " +
            "             |                   |                        |              | " +
            "                  |            \n" +
            "7            |              |                   |                        | " +
            "             |                   |                        |              | " +
            "                  |            \n", output.toString());
  }

  @Test
  public void mary() {
    init();
    maryController.play(view);
    assertEquals(
           "   E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   D#4  E4   F4   F#4  G4  \n" +
           " 0                 X                                            X                 \n" +
           " 1                 |                                            |                 \n" +
           " 2                 |                                  X                           \n" +
           " 3                 |                                  |                           \n" +
           " 4                 |                        X                                     \n" +
           " 5                 |                        |                                     \n" +
           " 6                 |                                  X                           \n" +
           " 7                                                    |                           \n" +
           " 8                 X                                            X                 \n" +
           " 9                 |                                            |                 \n" +
           "10                 |                                            X                 \n" +
           "11                 |                                            |                 \n" +
           "12                 |                                            X                 \n" +
           "13                 |                                            |                 \n" +
           "14                 |                                            |                 \n" +
           "15                                                                                \n" +
           "16                 X                                  X                           \n" +
           "17                 |                                  |                           \n" +
           "18                 |                                  X                           \n" +
           "19                 |                                  |                           \n" +
           "20                 |                                  X                           \n" +
           "21                 |                                  |                           \n" +
           "22                 |                                  |                           \n" +
           "23                 |                                  |                           \n" +
           "24                 X                                            X                 \n" +
           "25                 |                                            |                 \n" +
           "26                                                                             X  \n" +
           "27                                                                             |  \n" +
           "28                                                                             X  \n" +
           "29                                                                             |  \n" +
           "30                                                                             |  \n" +
           "31                                                                             |  \n" +
           "32                 X                                            X                 \n" +
           "33                 |                                            |                 \n" +
           "34                 |                                  X                           \n" +
           "35                 |                                  |                           \n" +
           "36                 |                        X                                     \n" +
           "37                 |                        |                                     \n" +
           "38                 |                                  X                           \n" +
           "39                 |                                  |                           \n" +
           "40                 X                                            X                 \n" +
           "41                 |                                            |                 \n" +
           "42                 |                                            X                 \n" +
           "43                 |                                            |                 \n" +
           "44                 |                                            X                 \n" +
           "45                 |                                            |                 \n" +
           "46                 |                                            X                 \n" +
           "47                 |                                            |                 \n" +
           "48                 X                                  X                           \n" +
           "49                 |                                  |                           \n" +
           "50                 |                                  X                           \n" +
           "51                 |                                  |                           \n" +
           "52                 |                                            X                 \n" +
           "53                 |                                            |                 \n" +
           "54                 |                                  X                           \n" +
           "55                 |                                  |                           \n" +
           "56  X                                       X                                     \n" +
           "57  |                                       |                                     \n" +
           "58  |                                       |                                     \n" +
           "59  |                                       |                                     \n" +
           "60  |                                       |                                     \n" +
           "61  |                                       |                                     \n" +
           "62  |                                       |                                     \n" +
           "63  |                                       |                                     \n",
            output.toString());
  }
}
