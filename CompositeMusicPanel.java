package cs3500.music.view;

import java.awt.*;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.Note;

/**
 * Adds functionality related to the composite view to MusicPanel
 */
public class CompositeMusicPanel extends MusicPanel {
  private long currentBeat;
  private Note selectedNote;

  public CompositeMusicPanel() {
    super();
    this.currentBeat = 0;
  }

  @Override
  public void setPiece(IMusicController<Note> piece) {
    super.setPiece(piece);
    this.currentBeat = 0;
  }

  @Override
  public void paintComponent(Graphics g) {
    if (highNote != null && lowNote != null) {
      int vertOffset = NOTE_SIZE;
      int horiOffset = NOTE_SIZE * 2;
      java.util.List<Note> notes = piece.getNotes();

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
        if (n.getStartTime() <= this.currentBeat &&
                n.getStartTime() + n.getDuration() >= this.currentBeat) {
          g.setColor(Color.DARK_GRAY);
          g.fillRect(n.getStartTime() * NOTE_SIZE + horiOffset,
                  noteHeight + 1 + vertOffset, NOTE_SIZE,
                  NOTE_SIZE - 1);
          for (int i = n.getStartTime() + 1; i < n.getStartTime() + n.getDuration(); i++) {
            g.setColor(Color.ORANGE);
            g.fillRect(i * NOTE_SIZE + horiOffset, noteHeight + 1 + vertOffset,
                    NOTE_SIZE, NOTE_SIZE - 1);
          }
        } else {
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

        if (n.equals(this.selectedNote)) {
          g.setColor(Color.CYAN);
          g.drawRect(n.getStartTime() * NOTE_SIZE + horiOffset, noteHeight + vertOffset,
                  n.getDuration() * NOTE_SIZE, NOTE_SIZE);
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
    g.setColor(Color.RED);
    g.drawLine((NOTE_SIZE * 2) + ((int) this.currentBeat * NOTE_SIZE), NOTE_SIZE,
            (NOTE_SIZE * 2) + ((int) this.currentBeat * NOTE_SIZE), this.getHeight());
  }

  /**
   * Gets the note at the given x and y position
   */
  public Note noteAtPos(int x, int y) {
    int noteX = x - (NOTE_SIZE * 2);
    int noteY = y - NOTE_SIZE;
    Note high = this.highNote.copy();
    Note low = this.lowNote.copy();
    int highNotePos = 0;
    int lowNotePos = this.staffHeight();
    while (!((highNotePos <= noteY + NOTE_SIZE) && (highNotePos >= noteY - NOTE_SIZE)
            || (lowNotePos <= noteY + NOTE_SIZE) && (lowNotePos >= noteY - NOTE_SIZE))) {
      high.transpose(-1);
      low.transpose(1);
      highNotePos += NOTE_SIZE;
      lowNotePos -= NOTE_SIZE;
    }
    Note seek;
    if ((highNotePos <= noteY + NOTE_SIZE) && (highNotePos >= noteY - NOTE_SIZE)) {
      seek = new Note(high.getPitch(), high.getOctave(), 1, noteX / NOTE_SIZE);
    } else {
      seek = new Note(low.getPitch(), low.getOctave(), 1, noteX / NOTE_SIZE);
    }
    boolean inPiece = false;
    for (Note n : this.piece.getNotes()) {
      if (n.getPitch().equals(seek.getPitch()) &&
              n.getOctave() == seek.getOctave() &&
              n.getStartTime() <= seek.getStartTime() &&
              n.getStartTime() + n.getDuration() >= seek.getStartTime() + 1) {
        inPiece = true;
        seek = n;
      }
    }
    if (inPiece) {
      this.selectedNote = seek;
      return seek;
    } else {
      return null;
    }
  }

  /**
   * Set the current beat to the given beat
   */
  public void setBeat(long b) {
    if (b < 0) {
      throw new IllegalArgumentException("Illegal beat");
    } else {
      this.currentBeat = b;
    }
  }
}