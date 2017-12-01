package cs3500.hw.provider.promodel;

import java.awt.*;

import cs3500.hw.model.Shape;

public class ShapeAdapter implements IShape{
  private Shape shape;

  public ShapeAdapter(Shape shape) {
    this.shape = shape;
  }

  /**
   * printShape method.
   * @param ticksPerSecond ticks per second. If this is equal to or less than zero,
   *                       return a description only including ticks.
   * @return a string description of the shape and its properties.
   */
  @Override
  public String printShape(double ticksPerSecond) {
    return shape.toString();
  }

  /**
   * Method to get name of shape.
   * @return name of shape.
   */
  @Override
  public String getName() {
    return shape.getName();
  }

  /**
   * Method to get height of shape.
   * @return height of shape.
   */
  @Override
  public int getHeight() {
    return Math.round(shape.getHeight());
  }

  /**
   * Method to get width of shape.
   * @return width of shape.
   */
  @Override
  public int getWidth() {
    return Math.round(shape.getWidth());
  }

  /**
   * Method to get color of shape.
   * @return color of shape.
   */
  @Override
  public Color getColor() {
    return new Color(shape.getRed(), shape.getGreen(), shape.getBlue());
  }

  /**
   * Method to get ShapeType of shape.
   * @return ShapeType of shape.
   */
  @Override
  public ShapeType getType() {
    if(shape.getType().equals(cs3500.hw.model.ShapeType.OVAL)) {
      return ShapeType.OVAL;
    }
    else {
      return ShapeType.RECTANGLE;
    }
  }

  /**
   * Method to get appear time.
   * @return time at which shape will appear.
   */
  @Override
  public int getAppear() {
    return shape.getAppears();
  }

  /**
   * Method to get disappear time.
   * @return time at which shape will disappear.
   */
  @Override
  public int getDisappear() {
    return shape.getDisappears();
  }

  /**
   * Method to get position of shape.
   * @return position of shape.
   */
  @Override
  public Point getPosition() {
    return new Point(Math.round(shape.getX()), Math.round(shape.getY()));
  }

  /**
   * Method to set height of shape.
   * @param height new height for shape.
   */
  @Override
  public void setHeight(int height) {
    shape.setShape(shape.getName(), shape.getX(), shape.getY(),
            shape.getWidth(), (float)height, shape.getRed(),
            shape.getGreen(), shape.getBlue(), shape.getAppears(), shape.getDisappears());
  }

  /**
   * Method to set width of shape.
   * @param width new width for shape.
   */
  @Override
  public void setWidth(int width) {
    shape.setShape(shape.getName(), shape.getX(), shape.getY(),
            (float)width, shape.getHeight(), shape.getRed(),
            shape.getGreen(), shape.getBlue(), shape.getAppears(), shape.getDisappears());
  }

  /**
   * Method to set color of shape.
   * @param color new color of shape.
   */
  @Override
  public void setColor(Color color) {
    float[] all = new float[3];
    color.getColorComponents(all);
    shape.setShape(shape.getName(), shape.getX(), shape.getY(),
            shape.getWidth(), shape.getHeight(), all[0],
            all[1], all[2], shape.getAppears(), shape.getDisappears());
  }

  /**
   * Method to set position of shape.
   * @param position new position of shape.
   */
  @Override
  public void setPosition(Point position) {
    shape.setShape(shape.getName(), (float)position.getX(), (float)position.getY(),
            shape.getWidth(), shape.getHeight(), shape.getRed(),
            shape.getGreen(), shape.getBlue(), shape.getAppears(), shape.getDisappears());
  }
}
