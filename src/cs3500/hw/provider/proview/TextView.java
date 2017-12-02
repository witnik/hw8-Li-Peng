package cs3500.hw.provider.proview;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import cs3500.hw.provider.promodel.IAnimation;

/**
 * Created by Evan on 10/25/2017. Text view implementation of the IView interface. This class will
 * output a text file describing the animation in words to the user.
 */
public class TextView implements IView {
  private IAnimation model;
  private double ticksPerSecond;
  private String outputFile;

  /**
   * TextView constructor.
   *
   * @param model animation model to be rendered.
   */
  public TextView(double ticksPerSecond, IAnimation model, String outputFile) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    this.outputFile = outputFile;
  }

  /**
   * outputAnimation function. Outputs the text view of the animation.
   */
  public void outputAnimation() {
    String content = getContent();
    if (outputFile.equals("out")) {
      System.out.print(content);
      System.out.flush();
      return;
    }
    try {
      PrintWriter file = new PrintWriter(outputFile);
      file.print(content);
      file.close();
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * outputFile function.
   *
   * @return a String containing a text description of the animation model.
   */
  private String getContent() {
    // We would have this view do all of the printing, but the code was asked for in the first
    // assignment to have the model be able to print a text description.
    // There is no reason not to reuse that function.
    return this.model.printDescription(this.ticksPerSecond);
  }
}

