package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the note class and its methods
 */
public class NoteTest {
  private Note c1;
  private Note cSharp1;
  private Note d1;
  private Note dSharp1;
  private Note c0;
  private Note c2;
  private Note c1StartLater;
  private Note c1StartEarlier;
  private Note c1Long;

  private void setUp() {
    this.c1 = new Note(Pitch.C, 1, 1, 1);
    this.cSharp1 = new Note(Pitch.C_SHARP, 1, 1, 1);
    this.d1 = new Note(Pitch.D, 1, 1, 1);
    this.dSharp1 = new Note(Pitch.D_SHARP, 1, 1, 1);
    this.c0 = new Note(Pitch.C, 0, 1, 1);
    this.c2 = new Note(Pitch.C, 2, 1, 1);
    this.c1StartEarlier = new Note(Pitch.C, 1, 1, 0);
    this.c1StartLater = new Note(Pitch.C, 1, 1, 2);
    this.c1Long = new Note(Pitch.C, 1, 2, 1);
  }

  @Test
  public void copy() {
    setUp();
    assertEquals(this.c1.copy(), this.c1);
    assertEquals(this.c2.copy(), this.c2);
    assertEquals(this.c0.copy(), this.c0);
    assertEquals(this.d1.copy(), this.d1);
    assertEquals(this.dSharp1.copy(), this.dSharp1);
    // should be enough tests for this simple method
  }

  @Test
  public void getPitch() {
    setUp();
    assertEquals(this.c1.getPitch(), Pitch.C);
    assertEquals(this.cSharp1.getPitch(), Pitch.C_SHARP);
    assertEquals(this.dSharp1.getPitch(), Pitch.D_SHARP);
  }

  @Test
  public void getOctave() {
    setUp();
    assertEquals(this.c1.getOctave(), 1);
    assertEquals(this.c0.getOctave(), 0);
    assertEquals(this.c2.getOctave(), 2);
  }

  @Test
  public void getDuration() {
    setUp();
    assertEquals(this.c1.getDuration(), 1);
    assertEquals(this.c1Long.getDuration(), 2);
  }

  @Test
  public void getStartTime() {
    setUp();
    assertEquals(this.c1.getStartTime(), 1);
    assertEquals(this.c1StartEarlier.getStartTime(), 0);
    assertEquals(this.c1StartLater.getStartTime(), 2);

  }

  @Test
  public void transpose() {
    setUp();
    assertEquals(this.c1.getPitch(), Pitch.C);
    assertEquals(this.c1.getOctave(), 1);
    c1.transpose(15);
    assertEquals(this.c1.getPitch(), Pitch.D_SHARP);
    assertEquals(this.c1.getOctave(), 2);
    c1.transpose(-18);
    assertEquals(this.c1.getPitch(), Pitch.A);
    assertEquals(this.c1.getOctave(), 0);
  }

  @Test
  public void testToString() {
    setUp();
    assertEquals(this.c1.toString(), "C1");
    assertEquals(this.c0.toString(), "C0");
    assertEquals(this.d1.toString(), "D1");
    assertEquals(this.c2.toString(), "C2");
    assertEquals(this.cSharp1.toString(), "C#1");
  }

  @Test
  public void changeStart() {
    setUp();
    assertEquals(this.c1.getStartTime(), 1);
    c1.changeStart(500);
    assertEquals(this.c1.getStartTime(), 501);
    c1.changeStart(-251);
    assertEquals(this.c1.getStartTime(), 250);
  }

  @Test
  public void toggleFlat() {
    setUp();
    assertEquals(this.cSharp1.toString(), "C#1");
    this.cSharp1.toggleFlat();
    assertEquals(this.cSharp1.toString(), "Db1");
    // no effect on white keys
    assertEquals(this.c1.toString(), "C1");
    this.c1.toggleFlat();
    assertEquals(this.c1.toString(), "C1");

  }

  @Test
  public void compareTo() {
    setUp();
    assertEquals(c1.compareTo(c2), -1);
    assertEquals(c1.compareTo(cSharp1), -1);
    assertEquals(cSharp1.compareTo(c2), -1);
    assertEquals(c1.compareTo(c1), 0);
    assertEquals(c1.compareTo(c0), 1);
  }

  @Test
  public void equals() {
    setUp();
    assertEquals(c1, c1);
    assertEquals(c1, new Note(Pitch.C, 1, 1, 1));
    assertNotEquals(c1, c1StartEarlier);
    assertNotEquals(c1, c1Long);
  }

  @Test
  public void testHashCode() {
    setUp();
    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c1.hashCode(), new Note(Pitch.C, 1, 1, 1).hashCode());
    assertNotEquals(c1.hashCode(), c1StartEarlier.hashCode());
    assertNotEquals(c1.hashCode(), c1Long.hashCode());
  }

}