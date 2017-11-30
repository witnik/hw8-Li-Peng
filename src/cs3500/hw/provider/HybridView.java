package cs3500.animator.view;

import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.AnimationController;
import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;

/**
 * Created by Evan on 11/8/2017.
 * HybridView class for the animation. Displays a window containing a VisualView
 * and a panel with options to control the animation.
 */
public class HybridView extends JFrame implements IView {
  private double ticksPerSecond = 1;
  private List<String> disabledShapes = new ArrayList<>();
  private JPanel animationView;
  private IAnimationController controller;

  /**
   * Constructor for a HybridView.
   * @param ticksPerSecond speed of animation
   * @param model IAnimation to be animated
   * @param outputFile name of file to export SVG to
   */
  public HybridView(double ticksPerSecond, IAnimation model, String outputFile) {
    super();
    this.ticksPerSecond = ticksPerSecond;
    this.controller = new AnimationController(model, this.ticksPerSecond);

    this.setLayout(new BorderLayout());

    // Animation panel
    animationView = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShapes(model.getShapes(), (Graphics2D)g);
        model.stepForward();
        this.revalidate();
      }
    };
    JScrollPane animationScroll = new JScrollPane(animationView);
    animationScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    animationScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Control panel
    JPanel panel = new JPanel();
    JToggleButton pausePlay = new JToggleButton("► / ❚❚");
    pausePlay.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.togglePause();
      }
    });
    JButton restart = new JButton("Restart ↺");
    restart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        {
          controller.restartAnimation();
        }
      }
    });
    JCheckBox loop = new JCheckBox("Loop Animation");
    loop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.toggleLooping();
      }
    });
    JLabel speedLabel = new JLabel("Speed:");
    JTextField speed = new JTextField(Integer.toString((int)this.ticksPerSecond), 5);
    speed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          if (Integer.parseInt(speed.getText()) < 0) {
            throw new Exception();
          }
          setTicksPerSecond(Integer.parseInt(speed.getText()));
        } catch (Exception x) {
          JOptionPane.showMessageDialog(null, "Only numbers >= 0 allowed!",
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    JButton export = new JButton("Export to SVG");
    export.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.exportAnimation(outputFile);
      }
    });
    panel.add(pausePlay);
    panel.add(loop);
    panel.add(restart);
    panel.add(speedLabel);
    panel.add(speed);
    panel.add(export);

    // Shape List
    JList disabledShapesView = new JList(model.getShapes().toArray());
    disabledShapesView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    disabledShapesView.setLayoutOrientation(JList.VERTICAL_WRAP);
    disabledShapesView.setPreferredSize(new Dimension(30, 200));
    JScrollPane disabledShapesScroll = new JScrollPane();
    disabledShapesScroll.setViewportView(disabledShapesView);
    disabledShapesScroll.getViewport().add(disabledShapesView);
    disabledShapesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants
            .HORIZONTAL_SCROLLBAR_ALWAYS);
    disabledShapesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    disabledShapesView.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        for (int i = 0; i < disabledShapesView.getLastVisibleIndex() + 1; i++) {
          if (disabledShapesView.isSelectedIndex(i)) {
            disableShape(model.getShapes().get(i).getName());
          } else {
            enableShape(model.getShapes().get(i).getName());
          }
        }
      }
    });

    this.add(animationScroll, BorderLayout.CENTER);
    this.add(panel, BorderLayout.PAGE_END);
    this.add(disabledShapesScroll, BorderLayout.PAGE_START);
    this.setSize(800, 800);
    this.setResizable(false);
    this.revalidate();
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  /**
   * outputAnimation function. Creates a window and draws the animation based on the AnimationModel.
   */
  public void outputAnimation() {
    Timer t = new Timer((int) (1000 / this.ticksPerSecond), new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    });
    t.start();
    while (true) {
      t.setDelay((int)(1000 / this.ticksPerSecond));
    }
  }

  /**
   * drawShapes function. Iterates through a given list of shapes and draws each one.
   * @param shapes List of shapes to be drawn.
   */
  private void drawShapes(List<IShape> shapes, Graphics2D g) {
    for (IShape s : shapes) {
      if (!disabledShapes.contains(s.getName())) {
        g.setColor(s.getColor());
        if (s.getType() == ShapeType.RECTANGLE) {
          g.fillRect((int) s.getPosition().getX(), (int) s.getPosition().getY(),
                  s.getWidth(), s.getHeight());
        }
        if (s.getType() == ShapeType.OVAL) {
          g.fillOval((int) s.getPosition().getX(), (int) s.getPosition().getY(),
                  s.getWidth(), s.getHeight());
        }
        if ((s.getPosition().getX() + s.getWidth()) > animationView.getPreferredSize().getWidth()) {
          animationView.setPreferredSize(new Dimension((int) s.getPosition().getX() + s.getWidth(),
                  (int) animationView.getPreferredSize().getHeight()));
        }
        if ((s.getPosition().getY()
                + s.getHeight()) > animationView.getPreferredSize().getHeight()) {
          animationView.setPreferredSize(new Dimension((int) animationView
                  .getPreferredSize().getWidth(),
                  (int) s.getPosition().getY() + s.getHeight()));
        }
        this.revalidate();
      }
    }
  }

  /**
   * setter for ticksPerSecond.
   * @param ticks value to set ticksPerSecond to
   */
  public void setTicksPerSecond(double ticks) {
    this.ticksPerSecond = ticks;
  }

  /**
   * method to disable a selected shape in the animation.
   * @param name name of the shape to be disabled
   */
  private void disableShape(String name) {
    if (!this.disabledShapes.contains(name)) {
      this.disabledShapes.add(name);
    }
  }

  /**
   * method to enable a selected shape in the animation.
   * @param name name of the shape to be enabled
   */
  private void enableShape(String name) {
    for (int i = 0; i < this.disabledShapes.size(); i++) {
      if (this.disabledShapes.get(i).equals(name)) {
        this.disabledShapes.remove(i);
        return;
      }
    }
  }
}

