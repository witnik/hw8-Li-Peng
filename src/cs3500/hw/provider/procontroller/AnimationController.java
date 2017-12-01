package cs3500.hw.provider.procontroller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cs3500.hw.model.AnimationModel;
import cs3500.hw.provider.promodel.IAnimation;
import cs3500.hw.provider.promodel.IAnimationAdapter;
import cs3500.hw.provider.proview.HybridView;
import cs3500.hw.provider.proview.IView;
import cs3500.hw.provider.proview.SvgView;
import cs3500.hw.view.TextualView;

public class AnimationController implements IAnimationController {
  private IAnimation model;
  private double ticksPerSecond;

  public AnimationController(IAnimation model, double ticksPerSecond) {
    this.model = model;
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
  public void exportAnimation(String outputFile) {
    SvgView temp = new SvgView(this.ticksPerSecond, this.model, outputFile);
    temp.outputAnimation();
  }
}


