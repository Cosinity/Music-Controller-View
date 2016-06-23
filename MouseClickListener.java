package cs3500.music.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * Class for handling mouse actions
 */
public class MouseClickListener implements MouseListener {

  private ClickRunnable leftAction, rightAction;

  /**
   * Default constructor to instantiate a mouse click listener
   */
  public MouseClickListener() {

  }

  /**
   * Sets the left click button's action
   * @param toDo the action to set the left click to
   */
  public void setLeftAction(ClickRunnable toDo) {
    this.leftAction = toDo;
  }

  /**
   * Sets the right click button's action
   * @param toDo the action to set the right click to
   */
  public void setRightAction(ClickRunnable toDo) {
    this.rightAction = toDo;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        this.leftAction.run(e.getX(), e.getY());
        break;
      case MouseEvent.BUTTON3:
        this.rightAction.run(e.getX(), e.getY());
        break;
      default:
        break;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // This does nothing because we don't want to make it depend on mouse presses
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // This does nothing because we don't want to make it depend on mouse releases
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // This does nothing because we don't really care about where the mouse is
    // unless it's clicking
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // This does nothing because we don't really care about where the mouse is
    // unless it's clicking
  }
}
