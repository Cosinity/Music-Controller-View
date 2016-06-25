package cs3500.music.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.Note;

/**
 * Displays a musical piece
 */
public class MusicPanel extends JPanel {
  // I made this field protected so subclasses could access it
  protected IMusicController<Note> piece;
  // I made this field public because it is useful for other classes to have access to it, and
  // it is final so it's safe
  public static final int NOTE_SIZE = 20;  // How large the notes should be when displayed, in
                                           // pixels
  // I made these fields protected so subclasses could access them
  protected Note lowNote;
  protected Note highNote;
  // I've added this field to represent the latest-running note of the piece
  private Note lastNote;
  // I've added this field to avoid recalculating the height every time it's accessed
  protected int height;

  public MusicPanel() {
    super();
    this.piece = new MusicController();
  }

  @Override
  public int getHeight() {
    return height + NOTE_SIZE + 1;
  }

  // I've added this method because it is required for scrolling the panel correctly
  @Override
  public int getWidth() {
    if (lastNote == null) {
      return NOTE_SIZE;
    }
    return ((lastNote.getStartTime() + lastNote.getDuration()) * NOTE_SIZE) + (NOTE_SIZE * 2);
  }

  @Override
  public void paintComponent(Graphics g) {
    if (highNote != null && lowNote != null) {
      int vertOffset = NOTE_SIZE;
      int horiOffset = NOTE_SIZE * 2;
      List<Note> notes = piece.getNotes();

    /*
     * Draw the note names and staff lines
     */
      Note high = highNote.copy();
      int h = 1;
      g.drawLine(horiOffset, (h - 1) * NOTE_SIZE + vertOffset,
              this.getWidth(), (h - 1) * NOTE_SIZE + vertOffset);
      while (!(high.getPitch().equals(lowNote.getPitch()) &&
              high.getOctave() == lowNote.getOctave())) {
        g.drawString(high.getPitch().toString() + high.getOctave(), 0,
                h * NOTE_SIZE - (NOTE_SIZE / 3) + vertOffset);
        g.drawLine(horiOffset, h * NOTE_SIZE + vertOffset, this.getWidth(),
                h * NOTE_SIZE + vertOffset);
        h++;
        high.transpose(-1);
      }
      g.drawString(lowNote.getPitch().toString() + lowNote.getOctave(), 0,
              h * NOTE_SIZE - (NOTE_SIZE / 3) + vertOffset);
      g.drawLine(horiOffset, h * NOTE_SIZE + vertOffset,
              this.getWidth(), h * NOTE_SIZE + vertOffset);

    /*
     * Draw the notes
     */
      for (Note n : notes) {
        int noteHeight = height - (notePosition(n) * NOTE_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(n.getStartTime() * NOTE_SIZE + horiOffset,
                noteHeight + 1 + vertOffset, NOTE_SIZE,
                NOTE_SIZE - 1);
        for (int i = n.getStartTime() + 1; i < n.getStartTime() + n.getDuration(); i++) {
          g.setColor(Color.GRAY);
          g.fillRect(i * NOTE_SIZE + horiOffset, noteHeight + 1 + vertOffset,
                  NOTE_SIZE, NOTE_SIZE - 1);
        }
      }
      this.setSize(this.getWidth(), height);

    /*
     * Draw the beat numbers and bar lines
     */
      for (int i = 0; i < this.getWidth(); i++) {
        if (i % 16 == 0) {
          g.setColor(Color.BLACK);
          g.drawString(String.valueOf(i), i * NOTE_SIZE + horiOffset, vertOffset - 1);
          g.drawLine((i * NOTE_SIZE) + horiOffset, vertOffset, (i * NOTE_SIZE) + horiOffset,
                  this.height + vertOffset);
        } else if (i % 4 == 0) {
          g.drawLine((i * NOTE_SIZE) + horiOffset, vertOffset, (i * NOTE_SIZE) + horiOffset,
                  this.height + vertOffset);
        }
      }
    }
  }

  public void setPiece(IMusicController<Note> piece) {
    this.piece = piece;
    List<Note> notes = piece.getNotes();
    if (notes.isEmpty()) {
      return;
    }
    lowNote = notes.get(0);
    highNote = notes.get(0);
    lastNote = notes.get(0);
    for (Note n : notes) {
      if (n.compareTo(lowNote) < 0) {
        lowNote = n;
      }
      if (n.compareTo(highNote) > 0) {
        highNote = n;
      }
      if (n.getStartTime() + n.getDuration() > lastNote.getStartTime() + lastNote.getDuration()) {
        lastNote = n;
      }
    }
    this.height = staffHeight();
  }

  /**
   * Gets the height required to display all notes in this piece
   *
   * @return the height required to display all notes in this piece
   */
  protected int staffHeight() {
    List<Note> notes = piece.getNotes();
    if (notes.isEmpty()) {
      return NOTE_SIZE;
    }
    return notePosition(highNote) * NOTE_SIZE;
  }

  /**
   * Get the vertical position of a given note in this piece
   *
   * @param n the note to find the position of
   * @return the number of notes (semitones) from the lowest note (exclusive) to the given note
   * (inclusive)
   */
  protected int notePosition(Note n) {
    Note moddable = n.copy();
    int notesBetween = 1;
    while (!(lowNote.getPitch().equals(moddable.getPitch()) &&
            lowNote.getOctave() == moddable.getOctave())) {
      moddable.transpose(-1);
      notesBetween++;
    }
    return notesBetween;
  }
}