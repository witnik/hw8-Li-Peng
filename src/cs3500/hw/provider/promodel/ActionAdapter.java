package cs3500.hw.provider.promodel;

import java.awt.*;

import javax.swing.*;

import cs3500.hw.model.AnimationType;

public class ActionAdapter implements IAction {

  private cs3500.hw.model.IAnimation action;

  public ActionAdapter(cs3500.hw.model.IAnimation action){
    this.action = action;
  }

  @Override
  public boolean overlaps(IAction other) {
    if(this.getType().equals(other.getType())) {
      return false;
    }
    if(this.getEnd() < other.getStart()) {
      return false;
    }
    if(this.getStart() > other.getEnd()) {
      return false;
    }

    return true;
  }

  @Override
  public int getStart() {
    return action.getStart();
  }

  @Override
  public ActionType getType() {
    if(action.getType().equals(AnimationType.CHANGECOLOR)) {
      return ActionType.COLOR;
    }
    else if(action.getType().equals(AnimationType.MOVE)) {
      return ActionType.POSITION;
    }
    else {
      if(action.getInfo()[0] == 0) {
        return ActionType.WIDTH;
      }
      else {
        return ActionType.HEIGHT;
      }
    }
  }

  @Override
  public Color getColor() {
    if(this.getType().equals(ActionType.COLOR)) {
      return new Color(action.getInfo()[0], action.getInfo()[1], action.getInfo()[2]);
    }
    else {
      return new Color(action.getShape().getRed(), action.getShape().getGreen(),
              action.getShape().getBlue());
    }
  }

  @Override
  public int getEnd() {
    return action.getEnd();
  }

  @Override
  public int getNum() {
    if(this.getType().equals(ActionType.WIDTH)) {
      return Math.round(action.getInfo()[0]);
    }
    else if(this.getType().equals(ActionType.HEIGHT)) {
      return Math.round(action.getInfo()[1]);
    }
    else {
      return 0;
    }
  }

  @Override
  public Point getPosition() {
    if(this.getType().equals(ActionType.POSITION)) {
      return new Point(Math.round(action.getInfo()[0]), Math.round(action.getInfo()[1]));

    }
    else {
      return new Point(Math.round(action.getShape().getX()),
              Math.round(action.getShape().getY()));
    }
  }

  @Override
  public String getShapeName() {
    return action.getShape().getName();
  }
}
