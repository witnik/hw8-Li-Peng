package cs3500.hw.provider.promodel;

import java.awt.Color;
import java.awt.Point;

/**
 * Created by Evan on 11/27/2017.
 * IAction interface. An action represents an change in a shape in the
 * animation. This can consist of a change in position, color, width, or height.
 */
public interface IAction {
  /**
   * Method to determine whether two actions overlap.
   * @param other Action to be checked against.
   * @return Whether both actions are of the same ActionType
   * and overlap in time interval.
   */
  boolean overlaps(IAction other);

  /**
   * Getter method to return start time.
   * @return start time.
   */
  int getStart();

  /**
   * Getter method to return ActionType.
   * @return type of Action.
   */
  ActionType getType();

  /**
   * Getter method to return Color.
   * @return Color shape will change to.
   */
  Color getColor();

  /**
   * Method to return end time.
   * @return end time.
   */
  int getEnd();

  /**
   * Method to return width/height.
   * @return width/height shape will change to.
   */
  int getNum();

  /**
   * Method to return position.
   * @return position shape will move to.
   */
  Point getPosition();

  /**
   * Method to return shape name.
   * @return name of shape action is associated with.
   */
  String getShapeName();
}
