package cs3500.hw.provider.promodel;

import java.awt.Color;
import java.awt.Point;

/**
 * Created by Evan on 11/27/2017. IShape interface. Represents shapes to be used in animations.
 */
public interface IShape {
  /**
   * toString method, implemented for the sake of using a JList.
   *
   * @return the associated name as a String
   */
  String toString();

  /**
   * printShape method.
   *
   * @param ticksPerSecond ticks per second. If this is equal to or less than zero, return a
   *                       description only including ticks.
   * @return a string description of the shape and its properties.
   */
  String printShape(double ticksPerSecond);

  // GETTERS

  /**
   * Method to get name of shape.
   *
   * @return name of shape.
   */
  String getName();

  /**
   * Method to get height of shape.
   *
   * @return height of shape.
   */
  int getHeight();

  /**
   * Method to get width of shape.
   *
   * @return width of shape.
   */
  int getWidth();

  /**
   * Method to get color of shape.
   *
   * @return color of shape.
   */
  Color getColor();

  /**
   * Method to get ShapeType of shape.
   *
   * @return ShapeType of shape.
   */
  ShapeType getType();

  /**
   * Method to get appear time.
   *
   * @return time at which shape will appear.
   */
  int getAppear();

  /**
   * Method to get disappear time.
   *
   * @return time at which shape will disappear.
   */
  int getDisappear();

  /**
   * Method to get position of shape.
   *
   * @return position of shape.
   */
  Point getPosition();

  // SETTERS

  /**
   * Method to set height of shape.
   *
   * @param height new height for shape.
   */
  void setHeight(int height);

  /**
   * Method to set width of shape.
   *
   * @param width new width for shape.
   */
  void setWidth(int width);

  /**
   * Method to set color of shape.
   *
   * @param color new color of shape.
   */
  void setColor(Color color);

  /**
   * Method to set position of shape.
   *
   * @param position new position of shape.
   */
  void setPosition(Point position);
}
