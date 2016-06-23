package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.Note;

/**
 * Implements the functionality provided by InteractiveView for a view that both displays notes
 * to a GUI and plays them with MIDI
 */
public class CompositeView extends JFrame implements InteractiveView<Note> {
  private MusicPanel staffPanel;
  private JPanel editPanel;
  private JScrollPane scrollPanel;
  private JTextField durationText;
  private JTextField startBeatText;
  private JTextField pitchText;
  private JTextField tempoText;
  private JTextField transposeText;

  public CompositeView() {
    super();
    this.setLayout(new BorderLayout());

    this.staffPanel = new CompositeMusicPanel();
    this.scrollPanel = new JScrollPane(staffPanel);
    this.getContentPane().add(scrollPanel, BorderLayout.CENTER);

    this.editPanel = new JPanel();
    initEditPanel();
    this.getContentPane().add(editPanel, BorderLayout.SOUTH);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Initialize the edit panel with the appropriate buttons and text fields
   */
  private void initEditPanel() {
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

    // Panel that contains the note-related components
    JPanel notePanel = new JPanel();
    notePanel.setLayout(new FlowLayout());

    notePanel.add(new JLabel("Pitch: "));
    this.pitchText = new JTextField(3);
    notePanel.add(pitchText);

    notePanel.add(new JLabel("Duration: "));
    this.durationText = new JTextField(3);
    notePanel.add(durationText);

    notePanel.add(new JLabel("Start Beat: "));
    this.startBeatText = new JTextField(4);
    notePanel.add(startBeatText);

    notePanel.add(new JButton("Add"));
    notePanel.add(new JButton("Edit"));

    // Panel that contains the piece-related components
    JPanel piecePanel = new JPanel();
    piecePanel.setLayout(new FlowLayout());

    piecePanel.add(new JLabel("Tempo: "));
    this.tempoText = new JTextField(10);
    piecePanel.add(tempoText);

    piecePanel.add(new JLabel("Transpose: "));
    this.transposeText = new JTextField(2);
    piecePanel.add(transposeText);

    piecePanel.add(new JButton("Change"));

    editPanel.add(new JLabel("Note"));
    editPanel.add(notePanel);
    editPanel.add(new JLabel("Piece"));
    editPanel.add(piecePanel);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1500, 800);
  }

  @Override
  public void selectNote(Note n) {

  }

  @Override
  public String getNewDuration() {
    return durationText.getText();
  }

  @Override
  public String getNewStartBeat() {
    return startBeatText.getText();
  }

  @Override
  public String getNewPitch() {
    return pitchText.getText();
  }

  @Override
  public String getNewTempo() {
    return tempoText.getText();
  }

  @Override
  public String getTranspose() {
    return transposeText.getText();
  }

  @Override
  public Note noteAtPos(int x, int y) {
    return null;
  }

  @Override
  public void play(IMusicController<Note> piece) {
    staffPanel.setPiece(piece);
    staffPanel.setPreferredSize(new Dimension(staffPanel.getWidth(), staffPanel.getHeight()));
    this.repaint();
  }
}
