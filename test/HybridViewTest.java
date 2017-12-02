import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;


import cs3500.hw.controller.AnimationController;
import cs3500.hw.controller.InteractiveController;
import cs3500.hw.model.AnimationModel;
import cs3500.hw.model.Rectangle;
import cs3500.hw.view.HybridView;

import static org.junit.Assert.assertEquals;

public class HybridViewTest {
  private HybridView view;
  private AnimationModel model = new AnimationModel();
  private AnimationController control;
  private Rectangle r = new Rectangle("r", 1, 1, 10, 10, 1, 1,
          1, 0, 3);

  /**
   * This test checks if the export button can export the right svg file.
   */
  @Test
  public void testExport() {
    model.addShape(r);
    model.move(r, 10, 10, 0, 1);
    view = new HybridView(model, 1);
    String actual = view.getSVG(false);
    control = new InteractiveController(model, view);
    ((InteractiveController) control).setOutPutFile("out.txt");
    control.execute();
    view.getButton("start").doClick();
    view.getButton("export").doClick();
    try {
      Scanner scan = new Scanner(new FileInputStream("out.txt"));
      String s = "";
      while (scan.hasNextLine()) {
        s += scan.nextLine();
        if (scan.hasNextLine()) {
          s += "\n";
        }
      }
      File f = new File("out.txt");
      try {
        Files.delete(f.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(s, actual);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This test check if the animation can be paused and resume by the button.
   */
  @Test
  public void testStartPauseResume() {
    model.addShape(r);
    view = new HybridView(model, 1);
    control = new InteractiveController(model, view);
    control.execute();
    assertEquals(false, this.view.getTimer().isRunning());
    view.getButton("start").doClick();
    assertEquals(true, this.view.getTimer().isRunning());
    view.getButton("pause").doClick();
    assertEquals(false, this.view.getTimer().isRunning());
    view.getButton("resume").doClick();
    assertEquals(true, this.view.getTimer().isRunning());
  }

  /**
   * This test checks whether the change speed can really change the speed of the animation or not.
   */
  @Test
  public void testChangeSpeed() {
    model.addShape(r);
    model.move(r, 10, 10, 0, 1);
    view = new HybridView(model, 1);
    HybridView view2 = new HybridView(model.copy(), 50);
    control = new InteractiveController(model, view);
    control.execute();
    view.getButton("start").doClick();
    view.getSpeed().setText("50");
    view.getButton("changespeed").doClick();
    ((InteractiveController) control).setOutPutFile("out.txt");
    view.getButton("export").doClick();
    try {
      Scanner scan = new Scanner(new FileInputStream("out.txt"));
      String s = "";
      while (scan.hasNextLine()) {
        s += scan.nextLine();
        if (scan.hasNextLine()) {
          s += "\n";
        }
      }
      File f = new File("out.txt");
      try {
        Files.delete(f.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(s, view2.getSVG(false));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * check if animation is set to be looped by checking if its svg output is looped. Also test if
   * the export can export the right looped svg file.
   */
  @Test
  public void testSetLoop() {
    model.addShape(r);
    model.move(r, 10, 10, 0, 1);
    view = new HybridView(model, 1);
    String actual = view.getSVG(true);
    control = new InteractiveController(model, view);
    ((InteractiveController) control).setOutPutFile("out1.txt");
    control.execute();
    view.getButton("loopanimation").doClick();
    view.getButton("export").doClick();
    try {
      Scanner scan = new Scanner(new FileInputStream("out1.txt"));
      String s = "";
      while (scan.hasNextLine()) {
        s += scan.nextLine();
        if (scan.hasNextLine()) {
          s += "\n";
        }
      }
      File f = new File("out1.txt");
      try {
        Files.delete(f.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(s, actual);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * check reset by comparing initial svg output and svg output after reset is called after speed
   * has been changed.
   */
  @Test
  public void testReset() {
    model.addShape(r);
    model.move(r, 10, 10, 0, 1);
    view = new HybridView(model, 1);
    control = new InteractiveController(model, view);
    String actual = view.getSVG(false);
    ((InteractiveController) control).setOutPutFile("out.txt");
    control.execute();

    view.getButton("start").doClick();
    view.getSpeed().setText("50");
    view.getButton("changespeed");
    view.getButton("reset").doClick();
    view.getButton("export").doClick();
    File f = new File("out.txt");
    try {
      Scanner scan = new Scanner(new FileInputStream(f));
      String s = "";
      while (scan.hasNextLine()) {
        s += scan.nextLine();
        if (scan.hasNextLine()) {
          s += "\n";
        }
      }
      try {
        Files.delete(f.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }

      assertEquals(actual, s);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
  }
}