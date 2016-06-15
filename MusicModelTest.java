package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the model's methods
 */
public class MusicModelTest {

  private GenericMusicPiece musicPiece;
  private GenericMusicPiece sitePiece;
  private List<Note> myExample;
  private List<Note> siteExample;

  private void setUpMusic() {


    this.myExample = new ArrayList<Note>() {
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


    this.musicPiece = new MusicModel(this.myExample);


    siteExample = new ArrayList<Note>() {{
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

    this.sitePiece = new MusicModel(siteExample);
  }

  @Test
  public void addNote() {
    GenericMusicPiece emptyPiece = new MusicModel();
    emptyPiece.addNote(new Note(Pitch.A, 4, 5, 0));
    assertEquals(emptyPiece.print(), "  A4  \n" +
            "0  X            \n" +
            "1  |            \n" +
            "2  |            \n" +
            "3  |            \n" +
            "4  |            \n");
    emptyPiece.addNote(new Note(Pitch.B, 4, 5, 0));
    assertEquals(emptyPiece.print(), "  A4   A#4  B4  \n" +
            "0  X         X  \n" +
            "1  |         |  \n" +
            "2  |         |  \n" +
            "3  |         |  \n" +
            "4  |         |  \n");
    emptyPiece.remove(new Note(Pitch.B, 4, 5, 0));
    emptyPiece.addNote(new Note(Pitch.A_SHARP, 4, 5, 5));
    emptyPiece.addNote(new Note(Pitch.G, 4, 5, 10));
    assertEquals(emptyPiece.print(), "   G4   G#4  A4   A#4 \n" +
            " 0            X            \n" +
            " 1            |            \n" +
            " 2            |            \n" +
            " 3            |            \n" +
            " 4            |            \n" +
            " 5                 X       \n" +
            " 6                 |       \n" +
            " 7                 |       \n" +
            " 8                 |       \n" +
            " 9                 |       \n" +
            "10  X                      \n" +
            "11  |                      \n" +
            "12  |                      \n" +
            "13  |                      \n" +
            "14  |                      \n");
  }


  @Test
  public void emptyPrint() {
    GenericMusicPiece empty = new MusicModel();
    assertEquals(empty.print(), "");
  }

  @Test
  public void appendTo() {
    this.setUpMusic();
    String mySong = this.musicPiece.print();
    String siteSong = this.sitePiece.print();
    assertTrue(mySong.startsWith("  C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  " +
            "B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3 " +
            "  D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3  "));
    assertTrue(mySong.contains("0  X                   X              X                   X" +
            "    X                   X              X                   X    X              " +
            "     X              X                   X  "));
    assertFalse(siteSong.contains("70")); // not 70 lines long
    this.musicPiece.appendTo(this.sitePiece);
    assertEquals(mySong, this.musicPiece.print());
    // appending to another song doesn't affect
    // original song
    siteSong = this.sitePiece.print();
    assertTrue(siteSong.startsWith("   C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  B1" +
            "   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3   D#3  " +
            "E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   D#4  E4   F4   F#4  G4  "));
    assertTrue(siteSong.contains("70")); // now 70 lines long
    assertFalse(siteSong.contains("140")); // not 140 lines long, however
    // for curiosity's sake - if appending a song to itself...
    this.sitePiece.appendTo(this.sitePiece);
    siteSong = this.sitePiece.print();
    assertTrue(siteSong.contains("140")); // Now it's 140 lines long, and appending to self works!

  }

  @Test
  public void append() {
    this.setUpMusic();
    String mySong = this.musicPiece.print();
    assertTrue(mySong.startsWith("  C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  " +
            "B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3 " +
            "  D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3  "));
    assertTrue(mySong.contains("0  X                   X              X                   X" +
            "    X                   X              X                   X    X              " +
            "     X              X                   X  "));
    assertFalse(mySong.contains("70"));// not 70 lines long yet
    this.musicPiece.append(siteExample);
    mySong = this.musicPiece.print();
    assertTrue(mySong.startsWith("   C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  B1" +
            "   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3   D#3  " +
            "E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   D#4  E4   F4   F#4  G4  "));
    assertTrue(mySong.contains("70")); // now 70 lines long!
  }

  @Test
  public void mixTo() {
    this.setUpMusic();
    String mySong = this.musicPiece.print();
    assertTrue(mySong.startsWith("  C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1   A#1  " +
            "B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3   C#3  D3 " +
            "  D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3  "));
    assertTrue(mySong.contains("0  X                   X              X                   X" +
            "    X                   X              X                   X    X              " +
            "     X              X                   X  "));
    this.musicPiece.mixTo(sitePiece);
    assertEquals(musicPiece.print(), mySong); // original song isn't changed by mixing to other
    String siteSong = sitePiece.print();
    assertTrue(siteSong.startsWith("   C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1" +
            "   A#1  B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3" +
            "   C#3  D3   D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   D#4  E4" +
            "   F4   F#4  G4  \n")); // header expanded significantly
    assertTrue(siteSong.contains("0  X                   X              X                   " +
            "X    X                   X              X                   X    X                " +
            "   X              X                   X                        X                 "));

    // To see if mixing song with itself does anything to output
    this.sitePiece.mixTo(this.sitePiece);
    assertEquals(this.sitePiece.print(), siteSong);
  }

  @Test
  public void mix() {
    this.setUpMusic();
    String siteSong = this.sitePiece.print();
    // Initial state prior to mixing
    assertTrue(siteSong.startsWith("   E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   " +
            "D#4  E4   F4   F#4  G4  "));
    assertTrue(siteSong.contains(" 0                 X                    " +
            "                        X                 "));
    assertTrue(siteSong.contains("63  |                                      " +
            " |                                     "));
    sitePiece.mix(myExample);
    siteSong = this.sitePiece.print();
    assertTrue(siteSong.startsWith("   C1   C#1  D1   D#1  E1   F1   F#1  G1   G#1  A1" +
            "   A#1  B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   A#2  B2   C3" +
            "   C#3  D3   D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   D#4  E4" +
            "   F4   F#4  G4  \n")); // header expanded significantly
    assertTrue(siteSong.contains("0  X                   X              X                   " +
            "X    X                   X              X                   X    X                " +
            "   X              X                   X                        X                 "));
    // Second line changed accordingly
    assertTrue(siteSong.contains("63")); // last line stayed the same (other than expanding)
  }

  @Test
  public void remove() {
    this.setUpMusic();
    // initial print
    String output = this.musicPiece.print();
    // try to remove what isn't there
    this.musicPiece.remove(new Note(Pitch.C, 1, 1, 0));
    // since duration 1 is specified, this is NOT the note at C1, beat 0
    assertEquals(this.musicPiece.print(), output);
    // removing a note with the correct duration, however, does change the note list
    // Note before the change
    assertTrue(output.startsWith("  C1"));
    assertTrue(output.contains("0  X  "));
    assertTrue(output.contains("1  |  "));
    assertTrue(output.contains("2  |  "));
    assertTrue(output.contains("3  |  "));

    this.musicPiece.remove(new Note(Pitch.C, 1, 4, 0));
    // Note after the change
    output = this.musicPiece.print();
    assertTrue(output.startsWith("  D1")); // since lowest note was removed, header changes
    assertTrue(output.contains("0     "));
    assertTrue(output.contains("1     "));
    assertTrue(output.contains("2     "));
    assertTrue(output.contains("3     "));
  }

  @Test
  public void transpose() {
    // Easy yet thorough testing for whether transposing the piece up/down semitones works
    this.setUpMusic();

    // Before transposition
    String output = this.musicPiece.print();
    String originalOutput = output;
    assertTrue(output.contains("C1")); // lowest note in original output
    assertTrue(output.contains("B3")); // highest note in original output
    this.musicPiece.transpose(-7);
    output = this.musicPiece.print();
    assertTrue(output.contains("F0")); // new lowest note
    assertFalse(output.contains("B3")); // no longer contains original highest note
    this.musicPiece.transpose(7);
    output = this.musicPiece.print();
    assertTrue(output.equals(originalOutput));
    // after retransposition the original output
    // matches the new output once again
    this.musicPiece.transpose(15);
    output = this.musicPiece.print();
    assertTrue(output.contains("D5")); // new highest note
    assertFalse(output.contains("C1")); // no longer contained
    assertTrue(output.contains("D#2")); // new lowest note

  }

  @Test
  public void toggleFlat() {
    // This would only affect the header. Actual generated notes will not care about whether
    // they are flat or not, as a sharp A is equal to a flat B, and so on.
    this.setUpMusic();

    // Before toggling
    String output = this.musicPiece.print();
    assertFalse(output.contains("Db") || output.contains("Eb")
            || output.contains("Gb") || output.contains("Ab")
            || output.contains("Bb"));
    assertTrue(output.contains("C#") && output.contains("D#")
            && output.contains("F#") && output.contains("G#")
            && output.contains("A#"));

    // After toggling
    this.musicPiece.toggleFlat();
    output = this.musicPiece.print();
    assertTrue(output.contains("Db") && output.contains("Eb")
            && output.contains("Gb") && output.contains("Ab")
            && output.contains("Bb"));
    assertFalse(output.contains("C#") || output.contains("D#")
            || output.contains("F#") || output.contains("G#")
            || output.contains("A#"));

    // Of course, test whether output changes when toggling back to false
    output = this.musicPiece.print();
    assertFalse(output.contains("Db") || output.contains("Eb")
            || output.contains("Gb") || output.contains("Ab")
            || output.contains("Bb"));
    assertTrue(output.contains("C#") && output.contains("D#")
            && output.contains("F#") && output.contains("G#")
            && output.contains("A#"));

    // QED the header and the notes in the song are changed to display to flat
    // The notes changing is important because once there is a GUI, mousing over the note will
    // show its name.
  }

  @Test
  public void printDupes() {
    // What if you add a note that overlaps with another note? should still work fine
    this.setUpMusic();
    this.musicPiece.addNote(new Note(Pitch.C, 1, 1, 1));
    String output = this.musicPiece.print();
    assertTrue(output.contains("  C1"));
    assertTrue(output.contains("0  X"));
    assertTrue(output.contains("1  X"));
    assertTrue(output.contains("2  |"));
    assertTrue(output.contains("3  |"));
    assertTrue(output.contains("4   "));
    // Endtime of first note still takes place as it's still marked as currently playing
    // Added note's start renders correctly
    // Continuing the tests...
    this.musicPiece.addNote(new Note(Pitch.C, 1, 1, 2));
    output = this.musicPiece.print();
    assertTrue(output.contains("  C1"));
    assertTrue(output.contains("0  X"));
    assertTrue(output.contains("1  X"));
    assertTrue(output.contains("2  X"));
    assertTrue(output.contains("3  |"));
    assertTrue(output.contains("4   "));
    this.musicPiece.addNote(new Note(Pitch.C, 1, 1, 3));
    output = this.musicPiece.print();
    assertTrue(output.contains("  C1"));
    assertTrue(output.contains("0  X"));
    assertTrue(output.contains("1  X"));
    assertTrue(output.contains("2  X"));
    assertTrue(output.contains("3  X"));
    assertTrue(output.contains("4   "));
    // What if you add one more with the same start time?
    // Doesn't affect output.
    this.musicPiece.addNote(new Note(Pitch.C, 1, 1, 3));
    output = this.musicPiece.print();
    assertTrue(output.contains("  C1"));
    assertTrue(output.contains("0  X"));
    assertTrue(output.contains("1  X"));
    assertTrue(output.contains("2  X"));
    assertTrue(output.contains("3  X"));
    assertTrue(output.contains("4   "));
    // QED, this edge case is accounted for.
  }

  @Test
  public void testSidebar() {
    // test n length compositions to make sure that sidebar is always correctly padded
    this.setUpMusic();
    String output = this.musicPiece.print();
    assertTrue(output.contains("  C1"));
    assertTrue(output.contains("0  X"));
    assertTrue(output.contains("1  |"));
    assertTrue(output.contains("2  |"));
    assertTrue(output.contains("3  |"));
    this.musicPiece.addNote(new Note(Pitch.C, 1, 4, 20));
    output = this.musicPiece.print();
    assertTrue(output.contains("   C1"));
    assertTrue(output.contains(" 0  X"));
    assertTrue(output.contains(" 1  |"));
    assertTrue(output.contains(" 2  |"));
    assertTrue(output.contains(" 3  |"));
    this.musicPiece.addNote(new Note(Pitch.C, 1, 4, 200));
    output = this.musicPiece.print();
    assertTrue(output.contains("    C1"));
    assertTrue(output.contains("  0  X"));
    assertTrue(output.contains("  1  |"));
    assertTrue(output.contains("  2  |"));
    assertTrue(output.contains("  3  |"));
    this.musicPiece.addNote(new Note(Pitch.C, 1, 4, 2000));
    output = this.musicPiece.print();
    assertTrue(output.contains("     C1"));
    assertTrue(output.contains("   0  X"));
    assertTrue(output.contains("   1  |"));
    assertTrue(output.contains("   2  |"));
    assertTrue(output.contains("   3  |"));
    this.musicPiece.addNote(new Note(Pitch.C, 1, 4, 10000));
    output = this.musicPiece.print();
    assertTrue(output.contains("      C1"));
    assertTrue(output.contains("    0  X"));
    assertTrue(output.contains("    1  |"));
    assertTrue(output.contains("    2  |"));
    assertTrue(output.contains("    3  |"));
    // If you have a piece with over 99999 measures you're going for a Guinness World Record.
    // That aside, the regex scaling of the sidebar generation guarantees that all sidebars
    // are rendered correctly.
  }

  @Test
  public void getNotesTest() {
    setUpMusic();
    assertArrayEquals(musicPiece.getNotes().toArray(), myExample.toArray());
    assertArrayEquals(sitePiece.getNotes().toArray(), siteExample.toArray());
    ArrayList<Note> firstBeat = new ArrayList<Note>() {{
      add(new Note(Pitch.C, 1, 4, 0));
      add(new Note(Pitch.C, 2, 4, 0));
      add(new Note(Pitch.C, 3, 4, 0));
      add(new Note(Pitch.E, 1, 4, 0));
      add(new Note(Pitch.E, 2, 4, 0));
      add(new Note(Pitch.E, 3, 4, 0));
      add(new Note(Pitch.G, 1, 4, 0));
      add(new Note(Pitch.G, 2, 4, 0));
      add(new Note(Pitch.G, 3, 4, 0));
      add(new Note(Pitch.B, 1, 4, 0));
      add(new Note(Pitch.B, 2, 4, 0));
      add(new Note(Pitch.B, 3, 4, 0));
    }};
    Collections.sort(firstBeat);
    ArrayList<Note> notesAtFirstBeat = new ArrayList<Note>();
    notesAtFirstBeat.addAll(musicPiece.getNotes(0));
    Collections.sort(notesAtFirstBeat);
    assertArrayEquals(firstBeat.toArray(), notesAtFirstBeat.toArray());
  }

}