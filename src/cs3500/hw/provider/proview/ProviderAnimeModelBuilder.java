package cs3500.hw.provider.proview;

import cs3500.hw.model.AnimationModel;
import cs3500.hw.model.Shape;
import cs3500.hw.view.AnimeModelBuilder;
import cs3500.hw.view.TweenModelBuilder;

/**
 * The ProviderAnimeModelBuilder class that extends AnimeModelBuilder class. A model builder for
 * providers' code.
 */
public class ProviderAnimeModelBuilder extends AnimeModelBuilder {
  private AnimationModel model;

  /**
   * This is the constructor for ProviderAnimeModelBuilder.
   */
  public ProviderAnimeModelBuilder() {
    super();
    this.model = new AnimationModel();
  }

  /**
   * This is the alternate constructor for ProviderAnimeModelBuilder. This constructor takes in a
   * model.
   *
   * @param model model that will be construct
   */
  ProviderAnimeModelBuilder(AnimationModel model) {
    super();
    this.model = model;
  }

  /**
   * Change the x and y extents of this shape from the specified extents to the specified target
   * extents. What these extents actually mean depends on the shape, but these are roughly the
   * extents of the box enclosing the shape.
   *
   * @param name      name of the shape whose scale is about to be change
   * @param fromSx    the old width
   * @param fromSy    the old height
   * @param toSx      the new width
   * @param toSy      the new height
   * @param startTime the stating time
   * @param endTime   the ending time
   * @return a modelbuilder that consist the model with this after-changed shape
   */
  @Override
  public TweenModelBuilder<AnimationModel> addScaleToChange(String name, float fromSx,
                                                            float fromSy, float toSx, float toSy,
                                                            int startTime, int endTime) {
    Shape s = this.model.getShape(name);
    if (toSx != 0 && toSy != 0) {
      this.model.scale(s, toSx, 0.0f, startTime, endTime);
      this.model.scale(s, 0.0f, toSy, startTime, endTime);
    }
    return this;
  }
}
