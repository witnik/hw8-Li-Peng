package cs3500.hw.provider.promodel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import cs3500.hw.model.AnimationType;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Oval;
import cs3500.hw.model.Rectangle;
import cs3500.hw.model.Shape;
import cs3500.hw.model.ShapeType;

public class IAnimationAdapter implements IAnimation {

  private boolean loop;
  private boolean pause;
  private IAnimationModel copy;
  private IAnimationModel model;
  private int t;

  public IAnimationAdapter(IAnimationModel model) {
    this.model = model;
    t = 0;
    loop = false;
    pause = false;
    copy = model.copy();
  }

  @Override
  public String printDescription(double ticksPerSecond) {
   return modifyResult(model.toString(), (float)ticksPerSecond);
  }

  /**
   * This is the method that modify the string output into the required format.
   *
   * @param str the string object that are being outputed
   * @return the formatted string object that are being outputed
   */
  private String modifyResult(String str, float tickPerSecond) {
    String[] all = str.split("t=");
    for (int i = 0; i < all.length; i++) {
      String s = all[i];
      int length = 0;
      while (length < s.length() && Character.isDigit(s.charAt(length))) {
        length += 1;
      }
      if (length != 0) {
        int time = Integer.parseInt(s.substring(0, length));
        double replace = (double) time / tickPerSecond;
        String rest = s.substring(length, s.length());
        all[i] = String.format("t=%.1fs", replace) + rest;
      }
    }
    String result = "";
    for (String str2 : all) {
      result += str2;
    }
    Scanner scan = new Scanner(result);
    String st = "";
    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      st += line + "<br>";
    }
    return "<html>" + st + "</html>";
  }

  @Override
  public boolean animationOver() {
    int end = 0;
    ArrayList<Shape> shapes = model.copy().getShapes();
    shapes.sort(new EndComparator());
    if (!shapes.isEmpty()) {
      end = shapes.get(shapes.size() - 1).getDisappears();
    }

    return t >= end;
  }

  @Override
  public void stepForward() {
    if(!this.pause) {
      ArrayList<Shape> shapes = model.copy().getShapes();
      ArrayList<cs3500.hw.model.IAnimation> moves = model.copy().getAnimation();
      for (int i = 0; i < shapes.size(); i++) {
        Shape s = shapes.get(i);
        float x = s.getX();
        float y = s.getY();
        float width = s.getWidth();
        float height = s.getHeight();
        float red = s.getRed();
        float green = s.getGreen();
        float blue = s.getBlue();
        for (cs3500.hw.model.IAnimation a : moves) {
          Shape temp = a.getShape();
          if (s.getName().equals(temp.getName())
                  && t >= a.getStart()
                  && t <= a.getEnd()) {
            if (a.getType().equals(AnimationType.MOVE)) {
              x = s.getX() + (a.getInfo()[0] - s.getX()) / (float) (a.getEnd() - t + 1);
              y = s.getY() + (a.getInfo()[1] - s.getY()) / (float) (a.getEnd() - t + 1);
            } else if (a.getType().equals(AnimationType.SCALE)) {
              width = s.getWidth() + (a.getInfo()[0] - s.getWidth())
                      / (float) (a.getEnd() - t + 1);
              height = s.getHeight() + (a.getInfo()[1] - s.getHeight())
                      / (float) (a.getEnd() - t + 1);
            } else {
              red = s.getRed() + (a.getInfo()[0] - s.getRed())
                      / (float) (a.getEnd() - t + 1);
              green = s.getGreen() + (a.getInfo()[1] - s.getGreen())
                      / (float) (a.getEnd() - t + 1);
              blue = s.getBlue() + (a.getInfo()[2] - s.getBlue())
                      / (float) (a.getEnd() - t + 1);
            }
          }
        }
        if (t <= s.getDisappears()
                && t >= s.getAppears()
                && s.getType().equals(cs3500.hw.model.ShapeType.RECTANGLE)) {
          shapes.set(i, s.setShape(s.getName(), x, y, width,
                  height, red, green, blue, s.getAppears(), s.getDisappears()));
        }
        if (t <= s.getDisappears()
                && t >= s.getAppears()
                && s.getType().equals(ShapeType.OVAL)) {
          shapes.set(i, s.setShape(s.getName(), x, y, width,
                  height, red, green, blue, s.getAppears(), s.getDisappears()));
        }
      }

      model.setShapes(shapes);
      t++;
      if(this.animationOver() && this.loop) {
        model = copy.copy();
        t = 0;
      }
    }
  }

  /**
   * This is a EndComparator class that implements Comparator interface. This method can help
   * to sort the list with the object end in last placed in last.
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

  @Override
  public void addShape(IShape shape) throws IllegalArgumentException {
    float[] all = new float[3];
    shape.getColor().getColorComponents(all);
    if(shape.getType().equals(cs3500.hw.provider.promodel.ShapeType.RECTANGLE)) {
      model.addShape(new Rectangle(shape.getName(), (float)shape.getPosition().getX(),
              (float)shape.getPosition().getY(), (float)shape.getWidth(), (float)shape.getHeight(),
              all[0], all[1], all[2], shape.getAppear(), shape.getDisappear()));
    }
    else {
      model.addShape(new Oval(shape.getName(), (float)shape.getPosition().getX(),
              (float)shape.getPosition().getY(), (float)shape.getWidth(), (float)shape.getHeight(),
              all[0], all[1], all[2], shape.getAppear(), shape.getDisappear()));
    }
  }

  @Override
  public void addAction(IAction action) throws IllegalArgumentException {

    if(action.getType().equals(ActionType.WIDTH)) {
      model.scale(model.getShape(action.getShapeName()), action.getNum(), 0,
              action.getStart(), action.getEnd());
    }
    else if(action.getType().equals(ActionType.HEIGHT)) {
      model.scale(model.getShape(action.getShapeName()), 0, action.getNum(),
              action.getStart(), action.getEnd());
    }
    else if(action.getType().equals(ActionType.POSITION)) {
      model.move(model.getShape(action.getShapeName()), (float)action.getPosition().getX(),
              (float)action.getPosition().getY(), action.getStart(), action.getEnd());
    }
    else {
      float[]all = new float[3];
      action.getColor().getColorComponents(all);
      model.changeColor(model.getShape(action.getShapeName()), (float)all[0], (float)all[2],
              (float)all[1], action.getStart(), action.getEnd());
    }
  }

  @Override
  public List<IShape> getShapes() {
    List<IShape> result = new ArrayList<>();
    for(Shape s: model.getShapes()) {
      result.add(new ShapeAdapter(s));
    }
    return result;
  }

  @Override
  public List<IAction> getActions() {
    List<IAction> result = new ArrayList<>();
    for(cs3500.hw.model.IAnimation a: model.getAnimation()) {
      result.add(new ActionAdapter(a));
    }
    return result;
  }

  @Override
  public List<IAction> getShapeActions(String name, List<IAction> actions) {
    ArrayList<IAction> result = new ArrayList<>();
    for(IAction a: actions) {
      if(a.getShapeName().equals(name)){
        result.add(a);
      }
    }
    return result;
  }

  @Override
  public void toggleLooping() {
    this.loop = !loop;
  }

  @Override
  public boolean isLooping() {
    return this.loop;
  }

  @Override
  public void togglePaused() {
    this.pause = !pause;
  }

  @Override
  public void restart() {
    model = copy.copy();
    t = 0;
  }
}
