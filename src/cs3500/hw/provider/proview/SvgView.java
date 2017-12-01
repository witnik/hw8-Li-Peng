package cs3500.hw.provider.proview;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JOptionPane;

import cs3500.hw.provider.promodel.IAction;
import cs3500.hw.provider.promodel.IAnimation;
import cs3500.hw.provider.promodel.IShape;
import cs3500.hw.provider.promodel.ShapeType;

/**
 * Created by Evan on 10/25/2017.
 * SVG View implementation for the IView interface. This view will output an SVG file
 * describing the animation.
 */
public class SvgView implements IView {
  private IAnimation model;
  private double ticksPerSecond;
  private boolean loopBack;
  private String outputFile;

  /**
   * constructor for the SvgView class.
   * @param ticksPerSecond speed of the animation.
   * @param model model to be animated.
   */
  public SvgView(double ticksPerSecond, IAnimation model, String outputFile) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    this.loopBack = false;
    this.outputFile = outputFile;
  }

  /**
   * setter for loopBack boolean.
   * @param loopBack value to set loopBack to
   */
  public void setLoopBack(boolean loopBack) {
    this.loopBack = loopBack;
  }

  /**
   * outputAnimation function. outputs the Svg view of the animation.
   */
  public void outputAnimation() {
    String content = getContent(500, 500);
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
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * outputFile function.
   * @param width width of the window.
   * @param height height of the window.
   * @return a String containing the SVG version of the animation.
   */
  private String getContent(int width, int height) {
    StringBuilder sb = new StringBuilder();
    sb.append("<svg width=\"");
    sb.append(width);
    sb.append("\" height=\"");
    sb.append(height);
    sb.append("\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");

    List<IShape> shapes = model.getShapes();

    int latestTime = 0;
    if (loopBack) {
      for (IShape s : shapes) {
        for (IAction a : model.getShapeActions(s.getName(), model.getActions())) {
          if (a.getEnd() > latestTime) {
            latestTime = a.getEnd();
          }
        }
      }

      sb.append("<rect><animate id=\"base\" begin=\"0;base.end\" dur=\"");
      sb.append(latestTime / ticksPerSecond * 1000);
      sb.append("ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/></rect>\n");
    }

    for (IShape s : shapes) {
      switch (s.getType()) {
        case OVAL:
          sb.append("<ellipse id=\"");
          sb.append(s.getName());
          sb.append("\" cx=\"");
          sb.append(s.getPosition().getX());
          sb.append("\" cy=\"");
          sb.append(s.getPosition().getY());
          sb.append("\" rx=\"");
          sb.append(s.getWidth());
          sb.append("\" ry=\"");
          sb.append(s.getHeight());
          sb.append("\" fill=\"rgb(");
          sb.append(s.getColor().getRed());
          sb.append(",");
          sb.append(s.getColor().getGreen());
          sb.append(",");
          sb.append(s.getColor().getBlue());
          sb.append(")\" visibility=\"visible\" >\n");
          break;
        case RECTANGLE:
          sb.append("<rect id=\"");
          sb.append(s.getName());
          sb.append("\" x=\"");
          sb.append(s.getPosition().getX());
          sb.append("\" y=\"");
          sb.append(s.getPosition().getY());
          sb.append("\" width=\"");
          sb.append(s.getWidth());
          sb.append("\" height=\"");
          sb.append(s.getHeight());
          sb.append("\" fill=\"rgb(");
          sb.append(s.getColor().getRed());
          sb.append(",");
          sb.append(s.getColor().getGreen());
          sb.append(",");
          sb.append(s.getColor().getBlue());
          sb.append(")\" visibility=\"visible\" >\n");
          break;
        default:
          break;
      }

      List<IAction> actions = model.getShapeActions(s.getName(), model.getActions());

      for (IAction a : actions) {
        switch (a.getType()) {
          case POSITION:
            sb.append("\t<animate attributeType=\"xml\" begin=\"");
            if (loopBack) {
              sb.append("base.begin+");
            }
            sb.append(a.getStart() / ticksPerSecond * 1000);
            sb.append("ms\" dur=\"");
            sb.append((a.getEnd() - a.getStart()) / ticksPerSecond * 1000);
            sb.append("ms\" attributeName=\"");
            if (s.getType() == ShapeType.OVAL) {
              sb.append("c");
            }
            sb.append("x\" from=\"");
            sb.append(s.getPosition().getX());
            sb.append("\" to=\"");
            sb.append(a.getPosition().getX());
            sb.append("\" fill=\"remove\" />\n");

            sb.append("\t<animate attributeType=\"xml\" begin=\"");
            if (loopBack) {
              sb.append("base.begin+");
            }
            sb.append(a.getStart() / ticksPerSecond * 1000);
            sb.append("ms\" dur=\"");
            sb.append((a.getEnd() - a.getStart()) / ticksPerSecond * 1000);
            sb.append("ms\" attributeName=\"");
            if (s.getType() == ShapeType.OVAL) {
              sb.append("c");
            }
            sb.append("y\" from=\"");
            sb.append(s.getPosition().getY());
            sb.append("\" to=\"");
            sb.append(a.getPosition().getY());
            sb.append("\" fill=\"remove\" />\n");
            break;
          case WIDTH:
            sb.append("\t<animate attributeType=\"xml\" begin=\"");
            if (loopBack) {
              sb.append("base.begin+");
            }
            sb.append(a.getStart() / ticksPerSecond * 1000);
            sb.append("ms\" dur=\"");
            sb.append((a.getEnd() - a.getStart()) / ticksPerSecond * 1000);
            sb.append("ms\" attributeName=\"");
            if (s.getType() == ShapeType.OVAL) {
              sb.append("rx");
            } else if (s.getType() == ShapeType.RECTANGLE) {
              sb.append("width");
            }
            sb.append("\" from=\"");
            sb.append(s.getWidth());
            sb.append("\" to=\"");
            sb.append(a.getNum());
            sb.append("\" fill=\"remove\" />\n");
            break;
          case COLOR:
            sb.append("\t<animate attributeType=\"xml\" begin=\"");
            if (loopBack) {
              sb.append("base.begin");
            }
            sb.append(a.getStart() / ticksPerSecond * 1000);
            sb.append("ms\" dur=\"");
            sb.append((a.getEnd() - a.getStart()) / ticksPerSecond * 1000);
            sb.append("ms\" attributeName=\"fill\" from=\"rgb(");
            sb.append(s.getColor().getRed());
            sb.append(",");
            sb.append(s.getColor().getGreen());
            sb.append(",");
            sb.append(s.getColor().getBlue());
            sb.append(")\" to=\"rgb(");
            sb.append(a.getColor().getRed());
            sb.append(",");
            sb.append(a.getColor().getGreen());
            sb.append(",");
            sb.append(a.getColor().getBlue());
            sb.append("\" fill=\"remove\" />\n");
            break;
          case HEIGHT:
            sb.append("\t<animate attributeType=\"xml\" begin=\"");
            if (loopBack) {
              sb.append("base.begin+");
            }
            sb.append(a.getStart() / ticksPerSecond * 1000);
            sb.append("ms\" dur=\"");
            sb.append((a.getEnd() - a.getStart()) / ticksPerSecond * 1000);
            sb.append("ms\" attributeName=\"");
            if (s.getType() == ShapeType.OVAL) {
              sb.append("ry");
            } else if (s.getType() == ShapeType.RECTANGLE) {
              sb.append("height");
            }
            sb.append("\" from=\"");
            sb.append(s.getHeight());
            sb.append("\" to=\"");
            sb.append(a.getNum());
            sb.append("\" fill=\"remove\" />\n");
            break;
          default:
            break;
        }
      }

      if (loopBack) {
        if (s.getType() == ShapeType.OVAL) {
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" "
                  + "dur=\"1ms\" attributeName=\"cx\" to=\"");
          sb.append(s.getPosition().getX());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"cy\" to=\"");
          sb.append(s.getPosition().getY());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"rx\" to=\"");
          sb.append(s.getWidth());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"ry\" to=\"");
          sb.append(s.getHeight());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"fill\" to=\"rgb(");
          sb.append(s.getColor().getRed());
          sb.append(",");
          sb.append(s.getColor().getGreen());
          sb.append(",");
          sb.append(s.getColor().getBlue());
          sb.append(")\" fill=\"freeze\" />");
        } else if (s.getType() == ShapeType.RECTANGLE) {
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"x\" to=\"");
          sb.append(s.getPosition().getX());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"y\" to=\"");
          sb.append(s.getPosition().getY());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"width\" to=\"");
          sb.append(s.getWidth());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"height\" to=\"");
          sb.append(s.getHeight());
          sb.append("\" fill=\"freeze\" />");
          sb.append("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                  "attributeName=\"fill\" to=\"rgb(");
          sb.append(s.getColor().getRed());
          sb.append(",");
          sb.append(s.getColor().getGreen());
          sb.append(",");
          sb.append(s.getColor().getBlue());
          sb.append(")\" fill=\"freeze\" />");
        }
      }

      if (s.getType() == ShapeType.OVAL) {
        sb.append("</ellipse>\n");
      } else if (s.getType() == ShapeType.RECTANGLE) {
        sb.append("</rect>\n");
      }
    }

    sb.append("</svg>");

    return sb.toString();
  }
}
