
package cs3500.hw.controller;

import java.io.IOException;
import java.io.PrintWriter;

import cs3500.hw.view.SVGView;

/**
 * this represent a controller for SVGview.
 */
public class SVGController implements AnimationController {
  private SVGView view;
  private String outputFile = "System.out";


  /**
   * constructor for controller for SVG view.
   *
   * @param view  SVG view which renders an image of anime's textual representation in svg format.
   */
  public SVGController(SVGView view) {
    this.view = view;
  }

  /**
   * activate the controller. svg representation of this anime will be exported to the file
   * specified by the user or System.out
   */
  @Override
  public void execute() {
    this.view.makeVisible();
    try {
      PrintWriter writer = new PrintWriter(outputFile);
      writer.println(this.view.getSVG());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method allows users to specify where the svg formated text will be exported to. svg will
   * be exported to System.out if file name is not specified by the user.
   *
   * @param name name of the file where the exported svg text will be included.
   */
  public void setOutputFile(String name) {
    this.outputFile = name;
  }
}