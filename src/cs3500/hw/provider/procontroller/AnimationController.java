package cs3500.hw.provider.procontroller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cs3500.hw.model.AnimationModel;
import cs3500.hw.model.IAnimationModel;
import cs3500.hw.provider.promodel.IAction;
import cs3500.hw.provider.promodel.IAnimation;
import cs3500.hw.provider.promodel.IAnimationAdapter;
import cs3500.hw.provider.promodel.IShape;
import cs3500.hw.provider.proview.HybridView;
import cs3500.hw.provider.proview.IView;
import cs3500.hw.provider.proview.SvgView;
import cs3500.hw.view.TextualView;

public class AnimationController implements IAnimationController {
  private IAnimation model;
  private double ticksPerSecond = 20;
  private IAnimation copy;
  private List<IShape> shapes;
  private List<IAction> actions;

  public AnimationController(IAnimation model, double ticksPerSecond) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    shapes = model.getShapes();
    actions = model.getActions();
  }


  @Override
  public void togglePause() {
    model.togglePaused();
  }

  @Override
  public void restartAnimation() {
    model.restart();
  }

  @Override
  public void toggleLooping() {
    model.toggleLooping();
  }

  @Override
  public void setTicksPerSecond(double ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
  }

  @Override
  public void exportAnimation(String outputFile) {
    IAnimationModel temp = new AnimationModel();
    copy = new IAnimationAdapter(temp);
    for (IShape s : this.shapes) {
      copy.addShape(s);
    }
    for (IAction move:this.actions) {
      copy.addAction(move);
    }
    SvgView view = new SvgView(this.ticksPerSecond, copy, outputFile);
    view.outputAnimation();
  }
}


