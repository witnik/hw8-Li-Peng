package cs3500.hw.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.hw.model.IAnimationModel;
import cs3500.hw.model.Shape;

/**
 * This is the class of HybridView. This class represents the view that can take in user's
 * instruction and start, pause, resume, restart, export the visual view into an SVG file, and etc.
 * This class combines visual view's and svg view's functionality while can be easily controlled by
 * users.
 */
public class HybridView extends JFrame implements IView {
  private AnimationPanel panel;
  private JButton start;
  private JButton pause;
  private JButton resume;
  private JButton restart;
  private JButton export;
  private JButton changeSpeed;
  private JButton loopAnimation;
  private JButton select;
  private JButton reset;
  private JTextField speed;
  private IAnimationModel model;
  private IAnimationModel model1;
  private JList list;
  private ArrayList<Shape> selected = new ArrayList<>();
  private int tick = 1;

  /**
   * Constructor of the HybridView class.
   *
   * @param model         the model that are being outputed
   * @param tickPerSecond the speed of the animation
   */
  public HybridView(IAnimationModel model, int tickPerSecond) {
    this.model = model.copy();
    this.model1 = this.model.copy();
    selected = new ArrayList<>();
    String[] names = model.getNames();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.tick = tickPerSecond;
    this.start = new JButton("start");
    this.pause = new JButton("pause");
    this.resume = new JButton("resume");
    this.restart = new JButton("restart");
    this.export = new JButton("export");
    this.changeSpeed = new JButton("changeSpeed");
    this.loopAnimation = new JButton("loop: off");
    this.select = new JButton("select");
    this.reset = new JButton("reset");
    this.panel = new AnimationPanel(model.getShapes(), model.getAnimation(), tickPerSecond);
    panel.setPreferredSize(new Dimension(1200, 1200));
    this.setLayout(new BorderLayout());

    list = new JList(names);
    list.setVisibleRowCount(names.length);
    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.addListSelectionListener(
        new ListSelectionListener() {
        @Override
          public void valueChanged(ListSelectionEvent e) {
            int[] temp = list.getSelectedIndices();
            selected.clear();
            for (int i : temp) {
              selected.add(model1.getShapes().get(i));
            }
        }
      }
    );

    JScrollPane top = new JScrollPane(panel);
    top.setPreferredSize(new Dimension(600, 600));
    top.setAutoscrolls(true);

    JPanel selectPanel = new JPanel();
    selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
    selectPanel.add(new JScrollPane(list));
    selectPanel.add(select);
    JPanel mid = new JPanel();
    mid.setLayout(new BoxLayout(mid, BoxLayout.X_AXIS));
    mid.add(top);
    mid.add(selectPanel);

    this.add(mid);

    JPanel buttons = new JPanel();
    buttons.setLayout(new GridLayout(3, 3));
    buttons.add(start);
    buttons.add(restart);
    buttons.add(pause);
    buttons.add(resume);
    buttons.add(export);
    buttons.add(reset);

    this.speed = new JTextField(10);
    this.speed.setText(Integer.toString(tickPerSecond));
    this.speed.setEditable(true);
    JPanel speedP = new JPanel();
    speedP.setLayout(new FlowLayout());
    speedP.add(this.changeSpeed);
    speedP.add(this.speed);
    speedP.add(this.loopAnimation);

    this.add(speedP, BorderLayout.BEFORE_FIRST_LINE);
    this.add(buttons, BorderLayout.AFTER_LAST_LINE);
    pack();
  }

  /**
   * This method translate the visual part of the HybridView into a loop or non-loop SVG file.
   *
   * @param b whether this svg file is capable of looping
   * @return a svg file that represent the visual of the HybridView
   */
  public String getSVG(boolean b) {
    if (!b) {
      return this.panel.toSVG();
    } else {
      return this.panel.toLoopSVG();
    }
  }

  /**
   * This method pauses the visual representation.
   */
  public void pause() {
    this.panel.timer.stop();
  }

  /**
   * This method resumes the visual representation.
   */
  public void resume() {
    this.panel.timer.restart();
  }

  /**
   * This method changes the speed of the view while passes out the new speed.
   *
   * @return the new speed of the view
   */
  public int changeSpeed() {
    int newTick = Integer.parseInt(this.speed.getText());
    this.panel.timer.setDelay(1000 / newTick);
    return newTick;
  }

  /**
   * This method checks which button are being pressed and passes out the correct button. If none of
   * the existing button is correlate to the String, this method throw an exception.
   *
   * @param s the string of the button that are being used
   * @return the button that correlates to the String
   */
  public JButton getButton(String s) {
    if (s.compareToIgnoreCase("start") == 0) {
      return this.start;
    }
    if (s.compareToIgnoreCase("restart") == 0) {
      return this.restart;
    }
    if (s.compareToIgnoreCase("pause") == 0) {
      return this.pause;
    }
    if (s.compareToIgnoreCase("resume") == 0) {
      return this.resume;
    }
    if (s.compareToIgnoreCase("changespeed") == 0) {
      return this.changeSpeed;
    }
    if (s.compareToIgnoreCase("export") == 0) {
      return this.export;
    }
    if (s.compareToIgnoreCase("loopanimation") == 0) {
      return this.loopAnimation;
    }
    if (s.compareToIgnoreCase("select") == 0) {
      return this.select;
    }
    if (s.compareToIgnoreCase("reset") == 0) {
      return this.reset;
    } else {
      this.showError("no button of such type is found");
      throw new IllegalArgumentException("no such button is found");
    }
  }

  /**
   * This method makes the visual view in this class visible to users.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * This method shows the error to the users.
   *
   * @param error the error message to be displayed.
   */
  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(this, error,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This method starts the animation and starts presenting stuff in the panel.
   */
  public void startAnimation() {
    this.panel.draw();
  }

  /**
   * This method restarts the code with the selected set of shapes by users in the program.
   *
   * @return the list of the shapes that are being selected by users
   */
  public ArrayList<Shape> selectRestart() {
    ArrayList<Shape> temp = new ArrayList<>(selected);
    this.panel.setNewPanel(temp, model1.copy().getAnimation());
    model.setShapes(new ArrayList<>(selected));
    this.panel.setLoop(model.copy(), false);
    return temp;
  }

  /**
   * This method makes the animation loops again after finish each time.
   */
  public void loop() {
    if (this.loopAnimation.getText().compareToIgnoreCase("loop: off") == 0) {
      this.loopAnimation.setText("loop: on");
    } else {
      this.loopAnimation.setText("loop: off");
    }
    this.panel.setLoop(model.copy(), true);
  }

  /**
   * This method makes the visual animation restarts with what is currently being displayed shapes.
   */
  public void setRestart() {
    this.panel.setNewPanel(model.copy().getShapes(), model1.copy().getAnimation());
    this.panel.setLoop(model, false);
  }

  /**
   * This method sets the visual presenting to the original status.
   */
  public void reset() {
    this.panel.timer.setDelay(1000 / this.tick);
    this.panel.setNewPanel(model1.copy().getShapes(), model1.copy().getAnimation());
    this.panel.setLoop(model1, false);
    model = model1.copy();
  }

  /**
   * This is a getter method to get the timer in the AnimationPanel for testing purposes.
   * @return the timer of the AnimationPanel
   */
  public Timer getTimer() {
    return this.panel.timer;
  }

  /**
   * This is a getter method to get the JTextField speed of the HybridView. This method is solely
   * for test purposes and are not used in any other places.
   *
   * @return the JTextField speed of the HybridView
   */
  public JTextField getSpeed() {
    return this.speed;
  }
}
