package cs3500.hw.provider;

/**
 * Created by Evan on 11/27/2017.
 * Interface for the Animator Controller
 */
public interface IAnimationController {
  /**
   * Method to toggle pause/play in animation.
   */
  void togglePause();

  /**
   * Method to restart animation.
   */
  void restartAnimation();

  /**
   * Method to enable/disable looping in the animation.
   */
  void toggleLooping();

  /**
   * Method to export animation to SVG file.
   * @param outputFile name of file to be outputted.
   */
  void exportAnimation(String outputFile);
}
