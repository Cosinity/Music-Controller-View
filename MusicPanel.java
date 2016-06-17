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
public class MusicPanel extends JPanel implements Scrollable {
  private IMusicController piece;
  private final int NOTE_SIZE = 20;  // How large the notes should be when displayed, in pixels
  Note lowNote;
  Note highNote;

  public MusicPanel() {
    super();
    piece = new MusicController();
    this.setSize(NOTE_SIZE * 64, NOTE_SIZE);
  }

  public MusicPanel(IMusicController piece) {
    super();
    this.piece = piece;
    List<Note> notes = piece.getNotes();
    lowNote = notes.get(0);
    highNote = notes.get(0);
    for (Note n : notes) {
      if (n.compareTo(lowNote) < 0) {
        lowNote = n;
      }
      if (n.compareTo(highNote) > 0) {
        highNote = n;
      }
    }
    this.setSize(NOTE_SIZE * 64, preferredHeight());
  }

  @Override
  public int getHeight() {
    return preferredHeight() + (NOTE_SIZE * 3);
  }

  @Override
  public void paintComponent(Graphics g) {
    int vertOffset = NOTE_SIZE;
    int horiOffset = NOTE_SIZE * 2;
    super.paintComponent(g);
    List<Note> notes = piece.getNotes();
    int height = preferredHeight();

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
      g.drawLine(horiOffset, h * NOTE_SIZE + vertOffset, this.getWidth(), h * NOTE_SIZE + vertOffset);
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
      g.fillRect(n.getStartTime() * NOTE_SIZE + horiOffset, noteHeight + 1 + vertOffset, NOTE_SIZE,
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
        g.drawString(String.valueOf(i), i * NOTE_SIZE + horiOffset, vertOffset);
        g.drawLine((i * NOTE_SIZE) + horiOffset, vertOffset, (i * NOTE_SIZE) + horiOffset,
                this.preferredHeight() + vertOffset);
      }else if (i % 4 == 0) {
        g.drawLine((i * NOTE_SIZE) + horiOffset, vertOffset, (i * NOTE_SIZE) + horiOffset,
                this.preferredHeight() + vertOffset);
      }
    }
  }

  public void setPiece(IMusicController piece) {
    this.piece = piece;
    List<Note> notes = piece.getNotes();
    lowNote = notes.get(0);
    highNote = notes.get(0);
    for (Note n : notes) {
      if (n.compareTo(lowNote) < 0) {
        lowNote = n;
      }
      if (n.compareTo(highNote) > 0) {
        highNote = n;
      }
    }
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension(NOTE_SIZE * 64, preferredHeight());
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.VERTICAL) {
      return 0;
    } else {
      return NOTE_SIZE;
    }
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.VERTICAL) {
      return 0;
    } else {
      return NOTE_SIZE;
    }
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return true;
  }

  /**
   * Gets the height required to display all notes in this piece
   *
   * @return the height required to display all notes in this piece
   */
  private int preferredHeight() {
    List<Note> notes = piece.getNotes();
    if (notes.isEmpty()) {
      return NOTE_SIZE;
    }
    Note high = new Note(highNote.getPitch(), highNote.getOctave(), highNote.getDuration(),
            highNote.getStartTime());

    return notePosition(high) * NOTE_SIZE;
  }

  /**
   * Get the vertical position of a given note in this piece
   *
   * @param n the note to find the position of
   * @return the number of notes (semitones) from the lowest note (exclusive) to the given note
   * (inclusive)
   */
  private int notePosition(Note n) {
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
