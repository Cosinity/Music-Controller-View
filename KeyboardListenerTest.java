package cs3500.music.util;

import com.sun.org.apache.xpath.internal.SourceTree;

import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Tests for keyboard event handling
 */
public class KeyboardListenerTest {

  private KeyboardListener listener;
  private Map<Character, Runnable> typed;
  private Map<Integer, Runnable> pressed;
  private StringBuilder log;

  private void init() {
    this.listener = new KeyboardListener();
    this.typed = new HashMap<Character, Runnable>();
    this.pressed = new HashMap<Integer, Runnable>();
    this.log = new StringBuilder();
    this.typed.put(' ', () -> this.log.append("This would pause/unpause playback."));
    this.pressed.put(KeyEvent.VK_HOME, () -> this.log.append("This would move the" +
            " display to start."));
    this.pressed.put(KeyEvent.VK_END, () -> this.log.append("This would move the " +
            "display to end."));
  }

  private void initMaps() {
    this.listener.setTypedMap(this.typed);
    this.listener.setPressedMap(this.pressed);
  }

  @Test
  public void setTypedMap() throws Exception {
    init();
    KeyEvent ke = new KeyEvent(new JFrame(),
            KeyEvent.VK_SPACE, 0, 0, KeyEvent.VK_SPACE, ' ', 0);
    this.listener.keyTyped(ke);
    assertTrue(log.length() == 0);
    initMaps();
    this.listener.keyTyped(ke);
    assertTrue(log.length() != 0);
    assertEquals(log.toString(), "This would pause/unpause playback.");
  }

  @Test
  public void setPressedMap() throws Exception {
    init();
    this.listener.keyPressed(new KeyEvent(new JFrame(),
            KeyEvent.VK_HOME, 0, 0, KeyEvent.VK_HOME, 'h', 0));
    this.listener.keyPressed(new KeyEvent(new JFrame(),
            KeyEvent.VK_END, 0, 0, KeyEvent.VK_HOME, 'e', 0));
    assertTrue(log.length() == 0);
    initMaps();
    this.listener.keyPressed(new KeyEvent(new JFrame(),
            KeyEvent.VK_HOME, 0, 0, KeyEvent.VK_HOME, 'h', 0));
    assertTrue(log.length() != 0);
    assertEquals(log.toString(), "This would move the display to start.");
    this.log = new StringBuilder();
    assertTrue(log.length() == 0);
    this.listener.keyPressed(new KeyEvent(new JFrame(),
            KeyEvent.VK_END, 0, 0, KeyEvent.VK_END, 'e', 0));
    assertTrue(log.length() != 0);
    assertEquals(log.toString(), "This would move the display to end.");
  }

}