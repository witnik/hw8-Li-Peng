package cs3500.hw.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.Timer;
import javax.swing.JPanel;

import cs3500.hw.model.IAnimation;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Shape;


/**
 * This is the view.AnimationPanel class which extends JPanel and implements the ActionListener
 * interface. This class represents the view.AnimationPanel object which are used to output a visual
 * and a SVG format output window. This panel takes in data from the model and transfer them to
 * outputs.
 */
public class AnimationPanel extends JPanel implements ActionListener {
  private ArrayList<Shape> shapes;
  private ArrayList<IAnimation> moves;
  private int tick = 1;
  Timer timer;
  private int currentTime = 0;
  private int end = 0;
  private boolean loop = false;
  private IAnimationModel model;

  /**
   * This is the constructor of the view.AnimationPanel class.
   *
   * @param shapes the list of shapes that are in the model
   * @param moves  the list of animation instructions that are in the model
   * @param tick   the speed of the animations
   */
  AnimationPanel(ArrayList<Shape> shapes, ArrayList<IAnimation> moves, int tick) {
    super();
    this.shapes = shapes;
    this.moves = moves;
    this.tick = tick;
    this.timer = new Timer(Math.round(1000 / tick), (ActionListener) this);
    this.setPreferredSize(new Dimension(600, 600));
    shapes.sort(new EndComparator());
    if (!shapes.isEmpty()) {
      end = shapes.get(shapes.size() - 1).getDisappears();
    }
  }

  /**
   * This method starts the timer for this panel and start drawing graphs in the visual output
   * window.
   */
  void draw() {
    timer.start();
    currentTime = 0;
  }

  /**
   * This method paints the all the graphs into the visual output window when being called at.
   *
   * @param g this is the graphic object that provides all the frameworks
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < shapes.size(); i++) {
      Shape s = shapes.get(i);
      float x = s.getX();
      float y = s.getY();
      float width = s.getWidth();
      float height = s.getHeight();
      float red = s.getRed();
      float green = s.getGreen();
      float blue = s.getBlue();
      for (IAnimation a : moves) {
        Shape temp = a.getShape();
        if (s.getName().equals(temp.getName())
                && currentTime >= a.getStart()
                && currentTime <= a.getEnd()) {
          if (a.getType().equals("move")) {
            x = s.getX() + (a.getInfo()[0] - s.getX()) / (float) (a.getEnd() - currentTime + 1);
            y = s.getY() + (a.getInfo()[1] - s.getY()) / (float) (a.getEnd() - currentTime + 1);
          } else if (a.getType().equals("scale")) {
            width = s.getWidth() + (a.getInfo()[0] - s.getWidth())
                    / (float) (a.getEnd() - currentTime + 1);
            height = s.getHeight() + (a.getInfo()[1] - s.getHeight())
                    / (float) (a.getEnd() - currentTime + 1);
          } else {
            red = s.getRed() + (a.getInfo()[0] - s.getRed())
                    / (float) (a.getEnd() - currentTime + 1);
            green = s.getGreen() + (a.getInfo()[1] - s.getGreen())
                    / (float) (a.getEnd() - currentTime + 1);
            blue = s.getBlue() + (a.getInfo()[2] - s.getBlue())
                    / (float) (a.getEnd() - currentTime + 1);
          }
        }
      }
      if (currentTime <= s.getDisappears()
              && currentTime >= s.getAppears()
              && s.getType().equals("rect")) {
        g.setColor(new Color(red, green, blue));
        g.fillRect(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
        shapes.set(i, s.setShape(s.getName(), x, y, width,
                height, red, green, blue, s.getAppears(), s.getDisappears()));
      }
      if (currentTime <= s.getDisappears()
              && currentTime >= s.getAppears()
              && s.getType().equals("oval")) {
        g.setColor(new Color(red, green, blue));
        g.fillOval(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
        shapes.set(i, s.setShape(s.getName(), x, y, width,
                height, red, green, blue, s.getAppears(), s.getDisappears()));
      }
    }
  }

  /**
   * This is the method that calls the paintComponent method after the time starts. This method
   * redraws all the shapes and creates animation.
   *
   * @param e ActionEvent object that listens to the action
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (currentTime < end) {
      currentTime++;
      repaint();
    } else {
      if (!loop) {
        timer.stop();
      } else {
        this.setNewPanel(model.copy().getShapes(), model.copy().getAnimation());
      }
    }
  }

  /**
   * This method sets a the panel to draw the given shape with the given moves. This method also
   * restarts the timer and sets the time count to zero.
   *
   * @param shapes list of shapes that are being drawn in the panel
   * @param moves  list of animations that are being drawn in the panel
   */
  void setNewPanel(ArrayList<Shape> shapes, ArrayList<IAnimation> moves) {
    this.currentTime = 0;
    this.shapes = shapes;
    this.moves = moves;
    timer.restart();
  }

  /**
   * This method sets this panel to loop the animation in the given model.
   *
   * @param model the model that are being looped
   * @param b     whether to change the loop status or not
   */
  void setLoop(IAnimationModel model, boolean b) {
    this.model = model;
    if (b) {
      loop = !loop;
    }
  }

  /**
   * This is a AnimationComparator class that implements Comparator interface. This method can help
   * to sort the list.
   */
  private class EndComparator implements Comparator<Shape> {
    /**
     * This method compares the disappearing time of two SHape object.
     *
     * @param s1 Shape object 1 that is being compared
     * @param s2 Shape object 2 that is being compared
     * @return the difference between end time of two Shape objects
     */
    public int compare(Shape s1, Shape s2) {
      return s1.getDisappears() - s2.getDisappears();
    }
  }

  /**
   * This is method output all the data into the SVG format. This method outputs a String that is in
   * SVG form.
   *
   * @return String of SVG form
   */
  String toSVG() {
    String result = "";
    for (Shape s : shapes) {
      String start;
      String type;
      String param;
      String xLen;
      String yLen;
      String tempResult = "";
      if (s.getType().equals("rect")) {
        start = String.format("<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\"" +
                        " fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n", s.getName(),
                Math.round(s.getX()), Math.round(s.getY()),
                Math.round(s.getWidth()), Math.round(s.getHeight()),
                Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                Math.round(s.getBlue() * 255));
        type = "rect";
        param = "";
        xLen = "width";
        yLen = "height";
      } else {
        start = String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\"" +
                        " fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n", s.getName(),
                Math.round(s.getX()), Math.round(s.getY()),
                Math.round(s.getWidth()), Math.round(s.getHeight()),
                Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                Math.round(s.getBlue() * 255));
        type = "ellipse";
        param = "c";
        xLen = "rx";
        yLen = "ry";
      }
      ArrayList<IAnimation> allInstructions = new ArrayList<IAnimation>();
      tempResult += start;
      for (IAnimation move : this.moves) {
        if (move.getShape().getName().equals(s.getName())) {
          allInstructions.add(move);
        }
      }
      tempResult += String.format("<set attributeName=\"visibility\"" +
              " attributeType=\"CSS\" to=\"visible\"" +
              " begin=\"%dms\"/>\n", s.getAppears() * 1000 / this.tick);
      tempResult += String.format("<set attributeName=\"visibility\"" +
              " attributeType=\"CSS\" to=\"" +
              "hidden\" begin=\"%dms\"/>\n", s.getDisappears() * 1000 / this.tick);

      for (IAnimation m : allInstructions) {
        if (m.getType().equals("move")) {
          int x = Math.round(m.getInfo()[0]);
          int y = Math.round(m.getInfo()[1]);
          tempResult += String.format("<animate attributeType=\"xml\" begin=\"%dms\" " +
                          "dur=\"%dms\" attributeName=\"%sx\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  m.getStart() * 1000 / this.tick, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  param, Math.round(s.getX()),
                  Math.round(x));
          tempResult += String.format("<animate attributeType=\"xml\" begin=\"%dms\" " +
                          "dur=\"%dms\" attributeName=\"%sy\" from=\"%d\"" +
                          " to=\"%d\" fill=\"freeze\" />\n",
                  m.getStart() * 1000 / this.tick, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  param, Math.round(s.getY()),
                  Math.round(y));
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), (float) x, (float) y, s.getWidth(), s.getHeight(),
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), (float) x, (float) y, s.getWidth(), s.getHeight(),
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          }
        } else if (m.getType().equals("changecolor")) {
          float red = m.getInfo()[0];
          float green = m.getInfo()[1];
          float blue = m.getInfo()[2];
          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"fill\"" +
                          " from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\"\n dur=\"%dms\"" +
                          " begin=\"%dms\" repeatCount=\"0\" fill=\"freeze\" />\n",
                  Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                  Math.round(s.getBlue() * 255), Math.round(red * 255),
                  Math.round(green * 255), Math.round(blue * 255),
                  (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), s.getX(), s.getY(), s.getWidth(), s.getHeight(),
                    red, green, blue, s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), s.getX(), s.getY(), s.getWidth(), s.getHeight(),
                    red, green, blue, s.getAppears(), s.getDisappears());
          }
        } else {
          int width = Math.round(m.getInfo()[0]);
          int height = Math.round(m.getInfo()[1]);
          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"%s\"" +
                          " from=\"%d\" to=\"%d\"\n dur=\"%dms\" begin=\"%dms\"" +
                          " repeatCount=\"0\" fill=\"freeze\" />\n", xLen,
                  Math.round(s.getWidth()), width, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);

          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"%s\"" +
                          " from=\"%d\" to=\"%d\"\n dur=\"%dms\" begin=\"%dms\"" +
                          " repeatCount=\"0\" fill=\"freeze\" />\n", yLen,
                  Math.round(s.getHeight()), height, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), s.getX(), s.getY(), (float) width, (float) height,
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), s.getX(), s.getY(), (float) width, (float) height,
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          }
        }
      }
      tempResult += "</" + type + ">\n";
      result += tempResult;
    }

    return "<svg width=\"1200\" height=\"1200\" version=\"1.1\"\n" +
            "    xmlns=\"http://www.w3.org/2000/svg\">\n" + result + "</svg>";
  }

  /**
   * This is method output all the data into the SVG format that loops. This method outputs a String
   * that is in SVG form.
   *
   * @return String of SVG form that can loop
   */
  String toLoopSVG() {
    String result = "";
    for (Shape s : shapes) {
      String start;
      String type;
      String param;
      String xLen;
      String yLen;
      String tempResult = "";
      if (s.getType().equals("rect")) {
        start = String.format("<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\"" +
                        " fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n", s.getName(),
                Math.round(s.getX()), Math.round(s.getY()),
                Math.round(s.getWidth()), Math.round(s.getHeight()),
                Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                Math.round(s.getBlue() * 255));
        type = "rect";
        param = "";
        xLen = "width";
        yLen = "height";
      } else {
        start = String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\"" +
                        " fill=\"rgb(%d,%d,%d)\" visibility=\"hidden\" >\n", s.getName(),
                Math.round(s.getX()), Math.round(s.getY()),
                Math.round(s.getWidth()), Math.round(s.getHeight()),
                Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                Math.round(s.getBlue() * 255));
        type = "ellipse";
        param = "c";
        xLen = "rx";
        yLen = "ry";
      }

      String restore = String.format("<animate attributeType=\"xml\" begin=\"base.end\"" +
                      " dur=\"1ms\" attributeName=\"%sx\" to=\"%d\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                      " attributeName=\"%sy\" to=\"%d\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                      " attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                      " attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                      " attributeName=\"fill\" to=\"rgb(%d,%d,%d)\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                      " attributeName=\"visibility\" to=\"hidden\" />\n",
              param, Math.round(s.getX()), param,
              Math.round(s.getY()), xLen, Math.round(s.getWidth()), yLen,
              Math.round(s.getHeight()), Math.round(s.getRed() * 255),
              Math.round(s.getGreen() * 255), Math.round(s.getBlue() * 255));

      ArrayList<IAnimation> allInstructions = new ArrayList<>();
      tempResult += start;
      for (IAnimation move : this.moves) {
        if (move.getShape().getName().equals(s.getName())) {
          allInstructions.add(move);
        }
      }
      tempResult += String.format("<set attributeName=\"visibility\"" +
              " attributeType=\"CSS\" to=\"visible\"" +
              " begin=\"base.begin+%dms\"/>\n", s.getAppears() * 1000 / this.tick);
      tempResult += String.format("<set attributeName=\"visibility\"" +
              " attributeType=\"CSS\" to=\"" +
              "hidden\" begin=\"base.begin+%dms\"/>\n", s.getDisappears() * 1000 / this.tick);

      for (IAnimation m : allInstructions) {
        if (m.getType().equals("move")) {
          int x = Math.round(m.getInfo()[0]);
          int y = Math.round(m.getInfo()[1]);
          tempResult += String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" " +
                          "dur=\"%dms\" attributeName=\"%sx\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  m.getStart() * 1000 / this.tick, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  param, Math.round(s.getX()),
                  Math.round(x));
          tempResult += String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" " +
                          "dur=\"%dms\" attributeName=\"%sy\" from=\"%d\"" +
                          " to=\"%d\" fill=\"freeze\" />\n",
                  m.getStart() * 1000 / this.tick, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  param, Math.round(s.getY()),
                  Math.round(y));
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), (float) x, (float) y, s.getWidth(), s.getHeight(),
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), (float) x, (float) y, s.getWidth(), s.getHeight(),
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          }
        } else if (m.getType().equals("changecolor")) {
          float red = m.getInfo()[0];
          float green = m.getInfo()[1];
          float blue = m.getInfo()[2];
          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"fill\"" +
                          " from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\"\n dur=\"%dms\"" +
                          " begin=\"base.begin+%dms\" repeatCount=\"0\" fill=\"freeze\" />\n",
                  Math.round(s.getRed() * 255), Math.round(s.getGreen() * 255),
                  Math.round(s.getBlue() * 255), Math.round(red * 255),
                  Math.round(green * 255), Math.round(blue * 255),
                  (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), s.getX(), s.getY(), s.getWidth(), s.getHeight(),
                    red, green, blue, s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), s.getX(), s.getY(), s.getWidth(), s.getHeight(),
                    red, green, blue, s.getAppears(), s.getDisappears());
          }
        } else {
          int width = Math.round(m.getInfo()[0]);
          int height = Math.round(m.getInfo()[1]);
          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"%s\"" +
                          " from=\"%d\" to=\"%d\"\n dur=\"%dms\" begin=\"base.begin+%dms\"" +
                          " repeatCount=\"0\" fill=\"freeze\" />\n", xLen,
                  Math.round(s.getWidth()), width, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);

          tempResult += String.format("<animate attributeType=\"XML\" attributeName=\"%s\"" +
                          " from=\"%d\" to=\"%d\"\n dur=\"%dms\" begin=\"base.begin+%dms\"" +
                          " repeatCount=\"0\" fill=\"freeze\" />\n", yLen,
                  Math.round(s.getHeight()), height, (m.getEnd() - m.getStart()) * 1000 / this.tick,
                  m.getStart() * 1000 / this.tick);
          if (type.equals("rect")) {
            s = s.setShape(s.getName(), s.getX(), s.getY(), (float) width, (float) height,
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          } else {
            s = s.setShape(s.getName(), s.getX(), s.getY(), (float) width, (float) height,
                    s.getRed(), s.getGreen(), s.getBlue(), s.getAppears(), s.getDisappears());
          }
        }
      }
      tempResult += restore + "</" + type + ">\n";
      result += tempResult;
    }

    return "<svg width=\"1200\" height=\"1200\" version=\"1.1\"\n" +
            "    xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect>\n" +
            "   <animate id=\"base\" begin=\"0;base.end\" dur=\"" + end * 1000 / this.tick +
            "ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
            "</rect>" + result + "</svg>";
  }
}