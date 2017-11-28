package cs3500.hw.model;

/**
 * This is an abstract class that implements the IAnimation interface.
 */
public abstract class AbstractAnimation implements IAnimation {
  Shape s;
  int start;
  int end;

  /**
   * This is the constructor for the abstract class AbstractAnimation.
   *
   * @param s     the Shape object this animation is acting on
   * @param start the starting time of this animation
   * @param end   the ending time of this animation
   */
  AbstractAnimation(Shape s, int start, int end) {
    this.s = s;
    this.start = start;
    this.end = end;
  }

  /**
   * This method is a getter that gets the start time of the Scale animation.
   *
   * @return the start time of the Scale animation
   */

  public int getStart() {
    return this.start;
  }

  /**
   * This method is a getter that gets the end time of the Scale animation.
   *
   * @return the end time of the Scale animation
   */

  public int getEnd() {
    return this.end;
  }

  /**
   * This method get the shape of this object.
   *
   * @return the shape of this object
   */
  public Shape getShape() {
    return this.s;
  }

  /**
   * Get the type of the animation in String format.
   *
   * @return the type of the animation in String format
   */
  public abstract String getType();

  /**
   * Get a list of float of information in the animation.
   *
   * @return the list of float of information
   */
  public abstract float[] getInfo();
}
