
package cs3500.hw.controller;

import cs3500.hw.view.VisualView;

/**
 * this represent a controller for visual view.
 */
public class VisualController implements AnimationController {
  private VisualView view;

  /**
   * constructor of controller for visual view.
   *
   * @param view visual view, which shows the anime vially.
   */
  public VisualController(VisualView view) {
    this.view = view;
  }

  /**
   * activate the controller, start the anime, make anime visible.
   */
  @Override
  public void execute() {
    view.makeVisible();
  }
}