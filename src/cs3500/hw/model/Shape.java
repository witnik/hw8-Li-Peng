package cs3500.hw.model;

/**
 * This is the interface for all Shape object. This class helps a Shape to animate and get its
 * parameters.
 */
public interface Shape {

  /**
   * This is a getter method that gets the name of the shape.
   *
   * @return name of the Shape object
   */
  String getName();

  /**
   * This is a getter method that gets the X parameter of the shape.
   *
   * @return X parameter of the Shape object
   */
  float getX();

  /**
   * This is a getter method that gets the Y parameter of the shape.
   *
   * @return X parameter of the Shape object
   */
  float getY();

  /**
   * This is a getter method that gets the width of the shape.
   *
   * @return width of the Shape object
   */
  float getWidth();

  /**
   * This is a getter method that gets the height of the shape.
   *
   * @return height of the Shape object
   */
  float getHeight();

  /**
   * This is a getter method that gets all the color data and put them into a String.
   *
   * @return a String that contains three parameters for the Color of the Shape object
   */
  String getColorSet();

  /**
   * This is a getter method that gets the appear time of the Shape object.
   *
   * @return the appear time of the Shape object
   */
  int getAppears();

  /**
   * This is a getter method that gets the disappear time of the Shape object.
   *
   * @return the disappear time of the Shape object
   */
  int getDisappears();

  /**
   * This method moves a Shape object from one posn to another.
   *
   * @param x1    x of the posn of the Shape object
   * @param y1    y of the posn of the Shape object
   * @param start when does the move animation starts
   * @param end   when does the move animation ends
   */
  void move(float x1, float y1, int start, int end);

  /**
   * This method changes the color of a Shape object.
   *
   * @param red   red color data of the color
   * @param blue  blue color data of the color
   * @param green green color data of the color
   * @param start when does the changeColor animation starts
   * @param end   when does the changeColor animation ends
   */
  void changeColor(float red, float blue, float green, int start, int end);

  /**
   * This method scales which means changes the width and height of the Shape object.
   *
   * @param width  the width of the Shape object
   * @param height the height of the Shape object
   * @param start  when does the scale animation starts
   * @param end    when does the scale animation ends
   */
  void scale(float width, float height, int start, int end);

  /**
   * This method checks if the animation is valid to proceed for this shape.
   *
   * @param a the animation that are passed into the Shape object
   * @return whether this animation is valid for the Shape object or not
   */
  boolean validAnimation(IAnimation a);

  /**
   * This method outputs a String that describes the Shape object.
   *
   * @return a String that describes the Shape object
   */
  String toString();

  /**
   * This is a getter method that gets the red color of the Shape object.
   *
   * @return the red color data a of the Shape object
   */
  float getRed();

  /**
   * This is a getter method that gets the green color of the Shape object.
   *
   * @return the green color data a of the Shape object
   */
  float getGreen();

  /**
   * This is a getter method that gets the blue color of the Shape object.
   *
   * @return the blue color data a of the Shape object
   */
  float getBlue();

  /**
   * To get another shape with the same type as the Shape object.
   *
   * @param name the name of the object
   * @param x x position of shape
   * @param y y position of shape
   * @param width width of shape
   * @param height height of shape
   * @param cRed red color data of the shape
   * @param cGreen green color data of the shape
   * @param cBlue blue color data of the shape
   * @param appears appearing time of the shape
   * @param disappears disappearing time of the shape
   * @return the new shape that is being constructed
   */
  Shape setShape(String name, float x, float y,
                float width, float height, float cRed,
                float cGreen, float cBlue, int appears, int disappears);

  /**
   * Get the type of the shape and return it in String.
   *
   * @return type of the shape in String format
   */
  String getType();
}
