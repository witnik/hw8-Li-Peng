package cs3500.hw.provider;

import java.util.List;

/**
 * Created by Evan on 10/19/2017.
 * IAnimation interface. Represents the model for an animation object.
 */
public interface IAnimation {
  /**
   * printDescription method.
   * @param ticksPerSecond ticks per second. If this is equal to or less than zero,
   *                       return a description only including ticks.
   * @return a string describing the shapes and actions contained in the animation.
   */
  String printDescription(double ticksPerSecond);

  /**
   * animationOver method.
   * @return true if the animation has reached its end (no more actions).
   */
  boolean animationOver();

  /**
   * stepForward method.
   * Increment t by one and perform all necessary actions.
   */
  void stepForward();

  /**
   * addShape function.
   * Change from original interface/model-- This change was something that came up in the self-eval
   * and will make the model more flexible in general
   * @param shape shape to be added to AnimationModel instance.
   */
  void addShape(IShape shape) throws IllegalArgumentException;

  /**
   * addAction function.
   * Change from original interface/model-- This change was something that came up in the self-eval
   * and will make the model more flexible in general
   * @param action action to be added to AnimationModel instance.
   */
  void addAction(IAction action) throws IllegalArgumentException;

  /**
   * getShapes method.
   * @return list of shapes in animation
   */
  List<IShape> getShapes();

  /**
   * getActions method.
   * @return list of actions in animation
   */
  List<IAction> getActions();

  /**
   * findActionsForShape method.
   * @param name name of shape to find actions for.
   * @return list of actions associated with given shape name.
   */
  List<IAction> getShapeActions(String name, List<IAction> actions);

  /**
   * method to toggle if the animation loops.
   */
  void toggleLooping();

  /**
   * getter for the looping boolean.
   * @return this.looping
   */
  boolean isLooping();

  /**
   * method to toggle if the animation is paused.
   */
  void togglePaused();

  /**
   * method to restart the animation at any time.
   */
  void restart();
}
