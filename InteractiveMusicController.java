package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.KeyboardListener;
import cs3500.music.util.MouseClickListener;
import cs3500.music.view.CompositeView;
import cs3500.music.view.InteractiveView;

/**
 * Interactive Music Controller for the Composite view
 */
public class InteractiveMusicController extends MusicController implements ActionListener {

  private InteractiveView<Note> view;
  private MouseClickListener mouseClickListener;
  private KeyboardListener keyboardListener;
  private Note selectedNote;

  public InteractiveMusicController(GenericMusicPiece<Note> piece) {
    super(piece);
    this.init();
  }

  private void init() {
    this.mouseClickListener = new MouseClickListener();
    this.keyboardListener = new KeyboardListener();
    this.view = new CompositeView();
    this.setKeyboardActions();
    this.setMouseActions();
    this.view.addActionListener(this);
    this.view.addKeyListener(this.keyboardListener);
    this.view.addMouseListener(this.mouseClickListener);
  }

  private void setMouseActions() {
    this.mouseClickListener.setLeftAction((int x, int y) -> {
      this.selectedNote = this.view.noteAtPos(x, y);
      this.view.selectNote(this.selectedNote);
    });
  }

  private void setKeyboardActions() {
    Map<Character, Runnable> typed = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> pressed = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> released = new HashMap<Integer, Runnable>();

    typed.put(' ', () -> this.view.togglePause()); // pause/unpause with spacebar
    pressed.put(KeyEvent.VK_HOME, () -> this.view.goToStart());
    pressed.put(KeyEvent.VK_END, () -> this.view.goToEnd());

    this.keyboardListener.setTypedMap(typed);
    this.keyboardListener.setPressedMap(pressed);
    this.keyboardListener.setReleasedMap(released);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("hello")) {
      System.out.println("Eureka");
    }
    Pitch p;
    int octave;
    int duration;
    int startBeat;
    int tempo;
    int transpose;
    try {
      String pitch = this.view.getNewPitch();
      if (pitch.length() == 2) {
        p = Pitch.create(pitch.substring(0, 1));
        octave = Integer.parseInt(pitch.substring(1, 2));
      } else {
        p = Pitch.create(pitch.substring(0, 2));
        octave = Integer.parseInt(pitch.substring(2, 3));
      }
      duration = Integer.parseInt(this.view.getNewDuration());
      startBeat = Integer.parseInt(this.view.getNewStartBeat());
      tempo = Integer.parseInt(this.view.getNewTempo());
      transpose = Integer.parseInt(this.view.getTranspose());
    } catch (Exception ex) {
      return;
    }
    switch (e.getActionCommand()) {
      case "Add":
        this.addNote(new Note(p, octave, duration, startBeat));
        this.view.resetFocus();
        break;
      case "Edit":
        this.changeDuration(this.selectedNote, duration);
        this.changeStart(this.selectedNote, startBeat);
        Note n = this.selectedNote.copy();
        int semitones = 0;
        if (this.selectedNote.compareTo(new Note(p, octave, 0, 0)) < 0) {
          while (!(n.getPitch().equals(p) && n.getOctave() == octave)) {
            n.transpose(1);
            semitones++;
          }
        } else {
          while (!(n.getPitch().equals(p) && n.getOctave() == octave)) {
            n.transpose(-1);
            semitones++;
          }
        }
        this.changePitch(this.selectedNote, semitones);
        this.view.resetFocus();
        break;
      case "Change":
        this.transpose(transpose);
        this.setTempo(tempo);
        this.view.resetFocus();
        break;
      default:
        break;
    }
  }
}
