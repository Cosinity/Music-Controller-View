package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.swing.*;

import cs3500.music.controller.IMusicController;

/**
 * Displays the piece with a graphical interface
 */
public class GuiViewFrame extends JFrame implements IMusicView {
  private MusicPanel displayPanel;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    super();
    this.displayPanel = new MusicPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(displayPanel.getWidth(), displayPanel.getHeight());
  }

  @Override
  public void play(IMusicController piece) {
    this.displayPanel.setPiece(piece);
    this.displayPanel.repaint();
    this.setSize(displayPanel.getWidth(), displayPanel.getHeight());
  }
}
