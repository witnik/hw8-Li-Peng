package cs3500.hw.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.util.ArrayList;

import cs3500.hw.model.IAnimation;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Shape;

/**
 * This is the view class VisualView that outputs the graph of this model in to the visual output
 * window transfering data from the model.
 */
public class VisualView extends JFrame implements IView {
  private AnimationPanel panel;

  /**
   * Constructor of the VisualView class.
   *
   * @param model         the model that are being outputed visually
   * @param tickPerSecond the speed of the animation
   */
  VisualView(IAnimationModel model, int tickPerSecond) {
    super();
    this.setPreferredSize(new Dimension(600, 600));
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    IAnimationModel model1 = model.copy();
    ArrayList<Shape> shapes = model1.getShapes();
    ArrayList<IAnimation> animations = model1.getAnimation();
    this.panel = new AnimationPanel(shapes, animations, tickPerSecond);
    this.panel = panel;
    panel.setPreferredSize(new Dimension(600, 600));
    JScrollPane p = new JScrollPane(panel);
    this.add(p);
    //panel.draw();
    pack();
  }

  /**
   * Make the view visible. this should be called after a view is constructed.
   */
  @Override
  public void makeVisible() {
    panel.draw();
    this.setVisible(true);
  }

  /**
   * Show error message.
   *
   * @param error the error message to be displayed
   */
  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(this, error,
            "Error", JOptionPane.ERROR_MESSAGE);
  }
}