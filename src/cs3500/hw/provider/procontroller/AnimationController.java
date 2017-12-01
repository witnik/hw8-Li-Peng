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

  public AnimationController(IAnimation model, double ticksPerSecond) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    List<IShape> shapes = model.getShapes();
    List<IAction> actions = model.getActions();
    IAnimationModel temp = new AnimationModel();
    copy = new IAnimationAdapter(temp);
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
    this.model.restart();
    SvgView temp = new SvgView(this.ticksPerSecond, this.model, outputFile);
    temp.outputAnimation();
  }
}


