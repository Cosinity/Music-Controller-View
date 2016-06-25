package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.KeyboardListener;
import cs3500.music.util.MouseClickListener;
import cs3500.music.view.IMusicView;
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
    this.mouseClickListener = new MouseClickListener();
    this.keyboardListener = new KeyboardListener();
  }

  private void setMouseActions() {
    this.mouseClickListener.setLeftAction((int x, int y) -> {
      this.selectedNote = this.view.noteAtPos(x, y);
      this.view.selectNote(this.selectedNote);
      this.view.resetFocus();
    });
    this.mouseClickListener.setRightAction((int x, int y) -> {
      this.removeNote(this.view.noteAtPos(x, y));
      this.view.resetFocus();
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
  public void play(IMusicView<Note> view) {
    try {
      InteractiveView<Note> v = (InteractiveView<Note>) view;
      this.view = v;
      this.setMouseActions();
      this.setKeyboardActions();
      this.view.addActionListener(this);
      this.view.addMouseListener(this.mouseClickListener);
      this.view.addKeyListener(this.keyboardListener);
      v.play(this);
    } catch (Exception e) {
      super.play(view);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
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
        Note addedNote = new Note(p, octave, duration, startBeat);
        this.addNote(addedNote);
        this.selectedNote = addedNote;
        this.view.resetFocus();
        this.view.play(this);
        this.view.togglePause();
        break;
      case "Edit":
        this.removeNote(this.selectedNote);
        Note changedNote = new Note(p, octave, duration, startBeat);
        this.addNote(changedNote);
        this.selectedNote = changedNote;
        this.view.resetFocus();
        this.view.play(this);
        this.view.togglePause();
        break;
      case "Change":
        this.transpose(transpose);
        this.setTempo(tempo);
        this.view.resetFocus();
        this.view.play(this);
        this.view.togglePause();
        break;
      default:
        break;
    }
  }
}
