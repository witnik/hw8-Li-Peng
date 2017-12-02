package cs3500.hw.provider.promodel;

import java.awt.Color;
import java.awt.Point;

import cs3500.hw.model.AnimationType;

/**
 * The ActionAdapter class that implements the IAction class. This is an adapter class that adapts
 * our IAnimation object to their IAction obejct.
 */
public class ActionAdapter implements IAction {

  private cs3500.hw.model.IAnimation action;

  /**
   * The constructor for ActionAdapter class.
   *
   * @param action the IAnimation action from our code that acts as the delegate
   */
  ActionAdapter(cs3500.hw.model.IAnimation action) {
    this.action = action;
  }

  /**
   * Method to determine whether two actions overlap.
   *
   * @param other Action to be checked against.
   * @return Whether both actions are of the same ActionType and overlap in time interval.
   */
  @Override
  public boolean overlaps(IAction other) {
    return !this.getType().equals(other.getType())
            && this.getEnd() >= other.getStart()
            && this.getStart() <= other.getEnd();
  }

  /**
   * Getter method to return start time.
   *
   * @return start time.
   */
  @Override
  public int getStart() {
    return action.getStart();
  }

  /**
   * Getter method to return ActionType.
   *
   * @return type of Action.
   */
  @Override
  public ActionType getType() {
    if (action.getType().equals(AnimationType.CHANGECOLOR)) {
      return ActionType.COLOR;
    } else if (action.getType().equals(AnimationType.MOVE)) {
      return ActionType.POSITION;
    } else {
      if (action.getInfo()[0] == 0) {
        return ActionType.WIDTH;
      } else {
        return ActionType.HEIGHT;
      }
    }
  }

  /**
   * Getter method to return Color.
   *
   * @return Color shape will change to.
   */
  @Override
  public Color getColor() {
    if (this.getType().equals(ActionType.COLOR)) {
      return new Color(action.getInfo()[0], action.getInfo()[1], action.getInfo()[2]);
    } else {
      return new Color(action.getShape().getRed(), action.getShape().getGreen(),
              action.getShape().getBlue());
    }
  }

  /**
   * Method to return end time.
   *
   * @return end time.
   */
  @Override
  public int getEnd() {
    return action.getEnd();
  }

  /**
   * Method to return width/height.
   *
   * @return width/height shape will change to.
   */
  @Override
  public int getNum() {
    if (this.getType().equals(ActionType.WIDTH)) {
      return Math.round(action.getInfo()[0]);
    } else if (this.getType().equals(ActionType.HEIGHT)) {
      return Math.round(action.getInfo()[1]);
    } else {
      return 0;
    }
  }

  /**
   * Method to return position.
   *
   * @return position shape will move to.
   */
  @Override
  public Point getPosition() {
    if (this.getType().equals(ActionType.POSITION)) {
      return new Point(Math.round(action.getInfo()[0]), Math.round(action.getInfo()[1]));

    } else {
      return new Point(Math.round(action.getShape().getX()),
              Math.round(action.getShape().getY()));
    }
  }

  /**
   * Method to return shape name.
   *
   * @return name of shape action is associated with.
   */
  @Override
  public String getShapeName() {
    return action.getShape().getName();
  }
}
