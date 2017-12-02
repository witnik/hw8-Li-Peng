package cs3500.hw.provider.procontroller;

import java.util.List;

import cs3500.hw.model.AnimationModel;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.provider.promodel.IAction;
import cs3500.hw.provider.promodel.IAnimation;
import cs3500.hw.provider.promodel.IAnimationAdapter;
import cs3500.hw.provider.promodel.IShape;
import cs3500.hw.provider.proview.SvgView;

/**
 * Class for AnimationController that implements the IAnimationController interface.
 */
public class AnimationController implements IAnimationController {
  private IAnimation model;
  private double ticksPerSecond = 20;
  private List<IShape> shapes;
  private List<IAction> actions;

  /**
   * This is the constructor for AnimationController.
   *
   * @param model          the model that are being taken in
   * @param ticksPerSecond the speed of the animation
   */
  public AnimationController(IAnimation model, double ticksPerSecond) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    shapes = model.getShapes();
    actions = model.getActions();
  }

  /**
   * Method to toggle pause/play in animation.
   */
  @Override
  public void togglePause() {
    model.togglePaused();
  }

  /**
   * Method to restart animation.
   */
  @Override
  public void restartAnimation() {
    model.restart();
  }

  /**
   * Method to enable/disable looping in the animation.
   */
  @Override
  public void toggleLooping() {
    model.toggleLooping();
  }

  /**
   * Method to set ticksPerSecond.
   *
   * @param ticksPerSecond new speed of animation.
   */
  @Override
  public void setTicksPerSecond(double ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
  }

  /**
   * Method to export animation to SVG file.
   *
   * @param outputFile name of file to be outputted.
   */
  @Override
  public void exportAnimation(String outputFile) {
    IAnimationModel temp = new AnimationModel();
    IAnimation copy = new IAnimationAdapter(temp);
    for (IShape s : this.shapes) {
      copy.addShape(s);
    }
    for (IAction move : this.actions) {
      copy.addAction(move);
    }
    SvgView view = new SvgView(this.ticksPerSecond, copy, outputFile);
    view.outputAnimation();
  }
}


