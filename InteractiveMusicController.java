package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.util.KeyboardListener;
import cs3500.music.util.MouseClickListener;
import cs3500.music.view.CompositeView;
import cs3500.music.view.InteractiveView;

/**
 * Interactive Music Controller for the Composite view; retains backwards compatibility with other
 * views.
 */
public class InteractiveMusicController extends MusicController {

  private GenericMusicPiece<Note> piece;
  private InteractiveView<Note> view;
  private MouseClickListener mouseClickListener;
  private KeyboardListener keyboardListener;

  public InteractiveMusicController() {
    super();
    this.init();
    this.piece = new MusicModel();
  }

  public InteractiveMusicController(GenericMusicPiece<Note> piece) {
    super(piece);
    this.init();
  }

  private void init() {
    this.mouseClickListener = new MouseClickListener();
    this.view = new CompositeView();
    this.setKeyboardActions();
    this.setMouseActions();
    this.view.setKeyboardListener(this.keyboardListener);
    this.view.setMouseListener(this.mouseClickListener);
  }

  private void setMouseActions() {
    this.mouseClickListener.setLeftAction((int x, int y) -> {
      Note toEdit = this.view.noteAtPos(x, y);
    });
  }

  private void setKeyboardActions() {
    Map<Character, Runnable> typed = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> pressed = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> released = new HashMap<Integer, Runnable>();

    typed.put(' ', () -> this.view.togglePause(); // pause/unpause with spacebar
    pressed.put(KeyEvent.VK_HOME, () -> this.view.goToStart());
    pressed.put(KeyEvent.VK_END, () -> this.view.goToEnd());

    this.keyboardListener.setTypedMap(typed);
    this.keyboardListener.setPressedMap(pressed);
    this.keyboardListener.setReleasedMap(released);
  }
}
