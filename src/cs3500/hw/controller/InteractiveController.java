package cs3500.hw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Shape;
import cs3500.hw.view.HybridView;

/**
 * this represents controller for hybridview.
 */
public class InteractiveController implements AnimationController {
  private IAnimationModel model;
  private HybridView view;
  private IAnimationModel copy;
  private int tick = 1;
  private ArrayList<Shape> selected = new ArrayList<>();
  private boolean selectStatus = false;
  private boolean loop = false;
  private String outputFile;

  /**
   * The constructor of the InteractiveController.
   *
   * @param model animation model used to run thsi anime.
   * @param view  Hybridview which renders a visual representation of this anime and contains
   *              interactive buttons for user to interact with the program.
   */
  public InteractiveController(IAnimationModel model, HybridView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * activate controller, add actionlisteners to buttons in the hybrid view.
   */
  @Override
  public void execute() {
    this.copy = model.copy();
    this.pause();
    this.resume();
    this.start();
    this.restart();
    this.changeSpeed();
    this.select();
    this.setLoop();
    this.reset();
    this.export();
    this.view.makeVisible();
  }

  /**
   * add actionlistener to start button in view. Anime will be started after start button is
   * pressed.
   */
  private void start() {
    this.view.getButton("start").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.startAnimation();
      }
    });
  }

  /**
   * add actionlistener to restart button in view. Anime will be restarted with same speed after
   * restart button is pressed.
   */
  private void restart() {
    view.getButton("restart").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.setRestart();
      }
    });
  }

  /**
   * add actionlistener to pause button in view. Anime will be paused after pause button is
   * pressed.
   */
  private void pause() {
    view.getButton("pause").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.pause();
      }
    });
  }

  /**
   * add actionlistener to resume button in view. a paused anime will continue after resume button
   * is pressed.
   */
  private void resume() {
    view.getButton("resume").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.resume();
      }
    });
  }

  /**
   * add actionlistener to loop button in view. this button allows users to determine whether this
   * anime will be restarted automatically after it ends.
   */
  private void setLoop() {
    this.view.getButton("loopAnimation").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        loop = !loop;
        view.loop();
      }
    });
  }

  /**
   * add actionlistener to changespeed button in view. this allows users to specify speed of this
   * anime (in tick per second).
   */
  private void changeSpeed() {
    this.view.getButton("changeSpeed").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tick = view.changeSpeed();
      }
    });
  }

  /**
   * add actionlistener to select button in view. select allows users to select shapes from the
   * anime and play anime with selected shapes only.
   */
  private void select() {
    this.view.getButton("select").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectStatus = true;
        selected = view.selectRestart();
      }
    });
  }

  /**
   * add actionlistener to reset button in view. reset all of the settings to default value.
   */
  private void reset() {
    this.view.getButton("reset").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectStatus = false;
        view.reset();
      }
    });
  }

  /**
   * add actionlistener to export button in view. export the animation in svg format.
   */
  private void export() {
    this.view.getButton("export").addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String result = "";
        if (selectStatus) {
          IAnimationModel temp = copy.copy();
          temp.setShapes(selected);
          result = new HybridView(temp, tick).getSVG(loop);
        } else {
          result = new HybridView(copy.copy(), tick).getSVG(loop);
        }
        try {
          PrintWriter writer = new PrintWriter(outputFile);
          writer.println(result);
          writer.close();
        } catch (FileNotFoundException f) {
          f.printStackTrace();
        }
      }
    });
  }

  /**
   * Set the output file to the name given.
   *
   * @param name name of the file which will include exported svg. this is used in easy animator, it
   *             allows users to specify which file exported svg will be included.
   */
  public void setOutPutFile(String name) {
    this.outputFile = name;
  }
}