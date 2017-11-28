package cs3500.hw.model;

/**
 * This is the ChangeColor class that implements IAnimation interface. This class represent the
 * animation of color changing.
 */
public class ChangeColor extends AbstractAnimation {

  private float red;
  private float blue;
  private float green;

  /**
   * This is the contructor of the ChangeColor class.
   *
   * @param s     the Shape obejct this animation will take place on
   * @param red   the red data of the color this animation will change to
   * @param blue  the blue data of the color this animation will change to
   * @param green the green data of the color this animation will change to
   * @param start when does the animation start
   * @param end   when does the animation end
   */
  ChangeColor(Shape s, float red, float blue, float green, int start, int end) {
    super(s, start, end);
    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  /**
   * This method proceed the ChangeColor animation on the Shape object.
   */
  @Override
  public void act() {
    s.changeColor(red, blue, green, start, end);
  }

  /**
   * This method outputs what the ChangeColor animation does to an object.
   *
   * @return a String about what the ChangeColor animation does
   */
  public String toString() {
    return "Shape " + s.getName() + " changes color from " + s.getColorSet() +
            " to (" + this.red + "," + this.blue + "," + this.green + ") from t=" + this.start +
            " to t=" + this.end;
  }

  /**
   * Get the type of the animation in String format.
   *
   * @return the type of the animation in String format
   */
  @Override
  public String getType() {
    return "changecolor";
  }

  /**
   * Get a list of float of information in the animation.
   *
   * @return the list of float of information
   */
  @Override
  public float[] getInfo() {
    float[] info = new float[3];
    info[0] = this.red;
    info[1] = this.green;
    info[2] = this.blue;
    return info;
  }

  /**
   * This method gets the red data of the color.
   *
   * @return red data of the color
   */
  public float getRed() {
    return this.red;
  }

  /**
   * This method gets the green data of the color.
   *
   * @return green data of the color
   */
  public float getGreen() {
    return this.green;
  }

  /**
   * This method gets the blue data of the color.
   *
   * @return blue data of the color
   */
  public float getBlue() {
    return this.blue;
  }
}
