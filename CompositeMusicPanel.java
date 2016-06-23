package cs3500.music.view;

import java.awt.*;

import cs3500.music.model.Note;

/**
 * Adds functionality related to the composite view to MusicPanel
 */
public class CompositeMusicPanel extends MusicPanel {
  private int currentBeat;

  public CompositeMusicPanel() {
    super();
    this.currentBeat = 0;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);
    g.drawLine((NOTE_SIZE * 2) + (currentBeat * NOTE_SIZE), NOTE_SIZE,
            (NOTE_SIZE * 2) + (currentBeat * NOTE_SIZE), this.getHeight());
  }

  /**
   * Gets the note at the given x and y position
   */
  public Note noteAtPos(int x, int y) {
    return null;
  }

  /**
   * Set the current beat to the given beat
   */
  public void setBeat(int b) {
    if (b < 0) {
      throw new IllegalArgumentException("Illegal beat");
    } else {
      this.currentBeat = b;
    }
  }
}