package cs3500.music.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Class for handling keyboard actions
 */
public class KeyboardListener implements KeyListener {

  Map<Character, Runnable> typed;
  Map<Integer, Runnable> pressed, released;

  public KeyboardListener() { }

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
    if (this.typed.containsKey(e.getKeyChar())) {
      this.typed.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // Do nothing
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Do nothing
  }
}
