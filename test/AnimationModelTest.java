import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cs3500.hw.model.AnimationModel;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Oval;
import cs3500.hw.model.Rectangle;
import cs3500.hw.model.Shape;

/**
 * This is the test for AnimationModel class. This test checks if animation model can output the
 * right output and can throw illegal argument when the animation can not take place for the shape.
 */
public class AnimationModelTest {
  private IAnimationModel model = new AnimationModel();
  private Shape rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
          6.0f,3.0f, 1.9f,2.3f,10,40);
  private Shape oval = new Oval("o", 30.0f,50.0f,2.0f,3.0f,
          1.0f,2.3f,5.6f,7,220);

  /**
   * This test if the AnimationModel outputs the right String.
   */
  @Test
  public void animation() {
    model.addShape(rectangle);
    model.addShape(oval);
    model.move(rectangle,12.0f,43.0f,10,15);
    model.move(oval, 50.0f,250.0f,200,220);
    model.scale(rectangle,12.0f,50.0f,12,20);
    model.changeColor(oval,2.1f,2.2f,2.3f,11,21);
    model.scale(oval,30.2f,13.0f,8,17);
    model.animate();
    assertEquals("Shapes:\n" +
            "Name: o\n" +
            "Type: oval\n" +
            "Lower-left corner: (30.0,50.0), xRadius: 2.0, yRadius: 3.0, Color: (1.0,5.6,2.3)\n" +
            "Appears at t=7\n" +
            "Disappears at t=220\n" +
            "\n" +
            "\n" +
            "Name: r\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (10.0,10.0), Width: 5.0, Height: 6.0, Color: (3.0,2.3,1.9)\n" +
            "Appears at t=10\n" +
            "Disappears at t=40\n" +
            "\n" +
            "\n" +
            "Shape o scales from Width: 2.0, Height: 3.0 to Width: " +
            "30.2, Height: 13.0 from t=8 to t=17\n" +
            "Shape r moves from (10.0,10.0) to (12.0,43.0) from time t=10 to t=15\n" +
            "Shape o changes color from (1.0,5.6,2.3) to (2.1,2.2,2.3) from t=11 to t=21\n" +
            "Shape r scales from Width: 5.0, Height: 6.0 to Width: 12.0, " +
            "Height: 50.0 from t=12 to t=20\n" +
            "Shape o moves from (30.0,50.0) to (50.0,250.0) from time t=200 to t=220\n",
            model.toString());
  }

  /**
   * This test if the AnimationModel throws exception when the animation can not take place for
   * the Shape object.
   * @throws Exception if the Animation is invalid for the Shape object
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidAnimation1() throws Exception {
    rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
            6.0f,3.0f, 1.9f,2.3f,10,40);
    model.addShape(rectangle);
    model.move(rectangle,12.0f,43.0f,1,12);
  }

  /**
   * This test if the AnimationModel throws exception when the animation can not take place for
   * the Shape object.
   * @throws Exception if the Animation is invalid for the Shape obejct
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidAnimation2() throws Exception {
    rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
            6.0f,3.0f, 1.9f,2.3f,10,40);
    model.addShape(rectangle);
    model.move(rectangle,12.0f,43.0f,10,13);
    model.move(rectangle,33.0f,4.0f,11,16);
  }

  /**
   * This test if the AnimationModel throws exception when the animation can not take place for
   * the Shape object.
   * @throws Exception if the Animation is invalid for the Shape obejct
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidAnimation3() throws Exception {
    oval = new Oval("o", 30.0f,50.0f,2.0f,3.0f,
            1.0f,2.3f,5.6f,7,220);
    model.addShape(oval);
    model.changeColor(oval,12.0f,43.0f, 3.0f,11,13);
    model.changeColor(oval,33.0f,4.0f, 5.0f,10,16);
  }

  /**
   * This test if the AnimationModel throws exception when the animation can not take place for
   * the Shape object.
   * @throws Exception if the Animation is invalid for the Shape obejct
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidAnimation4() throws Exception {
    rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
            6.0f,3.0f, 1.9f,2.3f,10,40);
    model.addShape(rectangle);
    model.scale(rectangle, 12.0f, 43.0f, 20, 25);
    model.scale(rectangle, 33.0f, 4.0f, 19, 22);
  }

  /**
   * This test if the AnimationModel throws exception when the animation is trying to act on a
   * Shape object that is not in the AnimationModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeDoesNotExis1() throws Exception {
    rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
            6.0f,3.0f, 1.9f,2.3f,10,40);
    model.addShape(rectangle);
    model.scale(oval,23.0f,60.2f,30,100);
  }

  /**
   * This test if the AnimationModel throws exception when the animation is trying to act on a
   * Shape object that is not in the AnimationModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeDoesNotExist2() throws Exception {
    rectangle = new Rectangle("r", 10.0f,10.0f,5.0f,
            6.0f,3.0f, 1.9f,2.3f,10,40);
    model.addShape(rectangle);
    model.changeColor(oval,23.0f,60.2f,40.3f,30,100);
  }

  /**
   * This test if the AnimationModel throws exception when the animation is trying to act on a
   * Shape object that is not in the AnimationModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shapeDoesNotExist3() throws Exception {
    oval = new Oval("o", 30.0f,50.0f,2.0f,3.0f,
            1.0f,2.3f,5.6f,7,220);
    model.addShape(oval);
    model.move(rectangle,23.0f,60.2f,30,100);
  }
}