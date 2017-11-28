package cs3500.hw.controller;

import cs3500.hw.view.TextualView;

/**
 * this represents controller for textual view.
 */
public class TextController implements AnimationController {
  private TextualView view;

  /**
   * Constructor of the TextController.
   *
   * @param view textual view which render an image of this anime's texual representation.
   */
  public TextController(TextualView view) {
    this.view = view;
  }

  /**
   * activate the controller and make view visible.
   */
  @Override
  public void execute() {
    this.view.makeVisible();
  }
}