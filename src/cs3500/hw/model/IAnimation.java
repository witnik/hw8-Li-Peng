package cs3500.hw.model;

/**
 * This is the interface for all the Animation objects. This interface helps an animation to act on
 * certain shapes, gets its parameters, and outputs the animation.
 */
public interface IAnimation {

  /**
   * This method proceed the animation on the Shape object.
   */
  void act();

  /**
   * This method is a getter that gets the start time of an animation.
   *
   * @return the start time of an animation
   */
  int getStart();

  /**
   * This method is a getter that gets the end time of an animation.
   *
   * @return the end time of an animation
   */
  int getEnd();

  /**
   * This method outputs what the animation does to an object.
   *
   * @return a String about what the animation does
   */
  String toString();

  /**
   * This method get the shape of this object.
   *
   * @return the shape of this object
   */
  Shape getShape();

  /**
   * Get the type of the animation in String format.
   *
   * @return the type of the animation in String format
   */
  String getType();

  /**
   * Get a list of float of information in the animation.
   * For Move class, get x in 0 position and y in 1 position.
   * For Scale class, get width in 0 position and height in 1 position.
   * For ChangeColor class, get red in 0 position, green in 1 position, and blue in 2 position.
   *
   * @return the list of float of information
   */
  float[] getInfo();
}
