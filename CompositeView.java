package cs3500.music.view;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.Note;

/**
 * Implements the functionality provided by InteractiveView for a view that both displays notes
 * to a GUI and plays them with MIDI
 */
public class CompositeView extends JFrame implements InteractiveView<Note> {
  private CompositeMusicPanel displayPanel;
  private JPanel editPanel;
  private JScrollPane scrollPanel;
  private JTextField durationText, startBeatText, pitchText, tempoText, transposeText;
  private JButton addButton, editButton, changeButton;
  private MidiView<Note> midiView;
  private Timer timer;

  public CompositeView() {
    super();
    this.setLayout(new BorderLayout());
    this.midiView = new MidiViewImpl();

    this.displayPanel = new CompositeMusicPanel();
    this.scrollPanel = new JScrollPane(displayPanel);
    this.add(this.scrollPanel, BorderLayout.CENTER);

    this.editPanel = new JPanel();
    this.initEditPanel();
    this.add(editPanel, BorderLayout.SOUTH);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.timer = new Timer(1, e -> {
      this.displayPanel.setBeat(this.midiView.getBeat());
      int lastB = this.lastBeat();
      if (this.midiView.getBeat() >= lastB) {
        JScrollBar sb = scrollPanel.getHorizontalScrollBar();
        sb.setValue((int) this.midiView.getBeat() * MusicPanel.NOTE_SIZE);
        lastB = this.lastBeat();
      }
    });
    this.timer.start();
  }

  /**
   * Gets the last beat currently visible on the screen
   */
  private int lastBeat() {
    return this.getWidth() / MusicPanel.NOTE_SIZE +
            this.scrollPanel.getHorizontalScrollBar().getValue() / MusicPanel.NOTE_SIZE - 2;
  }

  /**
   * Initialize the edit panel with the appropriate buttons and text fields
   */
  private void initEditPanel() {
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

    // Panel that contains the note-related components
    JPanel notePanel = new JPanel();
    notePanel.setLayout(new FlowLayout());

    // Pitch label and text box
    notePanel.add(new JLabel("Pitch: "));
    this.pitchText = new JTextField("C4", 3);
    notePanel.add(pitchText);

    // Duration label and text box
    notePanel.add(new JLabel("Duration: "));
    this.durationText = new JTextField("0", 3);
    notePanel.add(durationText);

    // Starting beat label and text box
    notePanel.add(new JLabel("Start Beat: "));
    this.startBeatText = new JTextField("0", 4);
    notePanel.add(startBeatText);

    // Add button
    this.addButton = new JButton("Add");
    this.addButton.setActionCommand("Add");
    notePanel.add(this.addButton);

    // Edit button
    this.editButton = new JButton("Edit");
    editButton.setActionCommand("Edit");
    notePanel.add(editButton);

    // Panel that contains the piece-related components
    JPanel piecePanel = new JPanel();
    piecePanel.setLayout(new FlowLayout());

    // Tempo label and text box
    piecePanel.add(new JLabel("Tempo: "));
    this.tempoText = new JTextField(10);
    piecePanel.add(tempoText);

    // Transposition label and text box
    piecePanel.add(new JLabel("Transpose: "));
    this.transposeText = new JTextField("0", 2);
    piecePanel.add(transposeText);

    // Change button
    this.changeButton = new JButton("Change");
    changeButton.setActionCommand("Change");
    piecePanel.add(changeButton);

    editPanel.add(new JLabel("Note"));
    editPanel.add(notePanel);
    editPanel.add(new JLabel("Piece"));
    editPanel.add(piecePanel);
  }

  @Override
  public void addKeyListener(KeyListener k) {
    this.scrollPanel.addKeyListener(k);
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.scrollPanel.addMouseListener(m);
  }

  @Override
  public void addActionListener(ActionListener al) {
    this.editButton.addActionListener(al);
    this.addButton.addActionListener(al);
    this.changeButton.addActionListener(al);
  }

  @Override
  public void restart(IMusicController<Note> controller) {
    this.play(controller);
  }

  @Override
  public void goToStart() {
    this.scrollPanel.getHorizontalScrollBar().setValue(
            this.scrollPanel.getHorizontalScrollBar().getMinimum());
    this.timer.stop();
    this.midiView.pause();
  }

  @Override
  public void goToEnd() {
    JScrollBar sb = this.scrollPanel.getHorizontalScrollBar();
    sb.setValue(sb.getMaximum());
    this.timer.stop();
    this.midiView.pause();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1500, 800);
  }

  @Override
  public void selectNote(Note n) {
    if (n != null) {
      this.pitchText.setText(n.getPitch().toString() + n.getOctave());
      this.startBeatText.setText(String.valueOf(n.getStartTime()));
      this.durationText.setText(String.valueOf(n.getDuration()));
    }
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
    int absoluteX = x + this.scrollPanel.getHorizontalScrollBar().getValue();
    int absoluteY = y + this.scrollPanel.getVerticalScrollBar().getValue();
    return this.displayPanel.noteAtPos(absoluteX, absoluteY);
  }

  @Override
  public void play(IMusicController<Note> piece) {
    this.tempoText.setText(String.valueOf(piece.getTempo()));
    this.displayPanel.setPiece(piece);
    this.displayPanel.setPreferredSize(new Dimension(displayPanel.getWidth(),
            displayPanel.getHeight()));
    this.midiView.restart();
    this.timer.restart();
    this.midiView.play(piece);
    this.resetFocus();
    this.setVisible(true);
  }

  @Override
  public void togglePause() {
    if (this.timer.isRunning()) {
      this.timer.stop();
      this.midiView.pause();
    }
    else {
      this.timer.start();
      this.midiView.unpause();
    }
  }

  @Override
  public void resetFocus() {
    this.scrollPanel.setFocusable(true);
    this.scrollPanel.requestFocus();
  }
}