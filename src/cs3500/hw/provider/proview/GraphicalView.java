package cs3500.hw.provider.proview;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.Timer;

import cs3500.hw.provider.promodel.IAnimation;
import cs3500.hw.provider.promodel.IShape;
import cs3500.hw.provider.promodel.ShapeType;

/**
 * Created by Evan on 10/25/2017.
 * Class for the graphical view implementation of the IView interface. This class will display an
 * animation to the user.
 */
public class GraphicalView extends JPanel implements IView {
  private double ticksPerSecond;
  private IAnimation model;

  /**
   * Constructor for the GraphicalView class.
   * @param ticksPerSecond ticks per second.
   * @param model AnimationModel to be drawn.
   */
  public GraphicalView(double ticksPerSecond, IAnimation model) {
    super();
    this.ticksPerSecond = ticksPerSecond;
    this.model = model;
    JFrame frame = new JFrame();

    JScrollPane scroll = new JScrollPane(this);
    frame.add(scroll);
    frame.setSize(500, 500);
    frame.setResizable(false);
    this.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawShapes(this.model.getShapes(), (Graphics2D)g);
    this.model.stepForward();
    this.revalidate();
  }

  /**
   * outputAnimation function. Creates a window and draws the animation based on the AnimationModel.
   */
  public void outputAnimation() {
    Timer t = new Timer((int) (1000 / ticksPerSecond), new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    });
    t.start();
    while (true) {
      if (this.model.animationOver()) {
        t.stop();
        break;
      }
    }
  }

  /**
   * drawShapes function. Iterates through a given list of shapes and draws each one.
   * @param shapes List of shapes to be drawn.
   */
  private void drawShapes(List<IShape> shapes, Graphics2D g) {
    for (IShape s : shapes) {
      g.setColor(s.getColor());
      if (s.getType() == ShapeType.RECTANGLE) {
        g.fillRect((int)s.getPosition().getX(), (int)s.getPosition().getY(),
                s.getWidth(), s.getHeight());
      }
      if (s.getType() == ShapeType.OVAL) {
        g.fillOval((int)s.getPosition().getX(), (int)s.getPosition().getY(),
                s.getWidth(), s.getHeight());
      }
      if ((s.getPosition().getX() + s.getWidth()) > this.getPreferredSize().getWidth()) {
        this.setPreferredSize(new Dimension((int)s.getPosition().getX() + s.getWidth(),
                (int)this.getPreferredSize().getHeight()));
      }
      if ((s.getPosition().getY() + s.getHeight()) > this.getPreferredSize().getHeight()) {
        this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),
                (int)s.getPosition().getY() + s.getHeight()));
      }
      this.revalidate();
    }
  }
}
