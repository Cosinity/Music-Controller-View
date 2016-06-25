package cs3500.music.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling keyboard actions
 */
public class KeyboardListener implements KeyListener {

  Map<Character, Runnable> typed;
  Map<Integer, Runnable> pressed, released;

  public KeyboardListener() {
    this.typed = new HashMap<Character, Runnable>();
    this.pressed = new HashMap<Integer, Runnable>();
    this.released = new HashMap<Integer, Runnable>();
  }

  public void setTypedMap(Map<Character, Runnable> toSet) {
    this.typed = toSet;
  }

  public void setPressedMap(Map<Integer, Runnable> toSet) {
    this.pressed = toSet;
  }

  public void setReleasedMap(Map<Integer, Runnable> toSet) {
    this.released = toSet;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    Runnable action = this.typed.get(e.getKeyChar());
    if (action != null) {
      action.run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    Runnable action = this.pressed.get(e.getKeyCode());
    if (action != null) {
      action.run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    Runnable action = this.released.get(e.getKeyCode());
    if (action != null) {
      action.run();
    }
  }
}