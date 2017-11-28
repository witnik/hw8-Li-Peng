We added another constructor to our AnimationModel so the builder can use this constructor.
 We also add two methods in the IAnimationModel interface: copy and setShapes.
Copy method can return a safe copy of the model so when we use the model in the other
places the model won’t be altered by accident. The setShapes method are used to achieve
 the selecting shapes to rerun functionality in the HybridView.
We also added some methods into the AnimationPanel class. We added a toLoopSvg class
 to output a SVG file that can loop back every time. We added a new method setNewPanel
 to set the panel to draw the given shapes and animations. We also added a method
 setLoop to make the panel loop, to draw again, each time one round ends. We changed
 the actionPerformed method so when the time count is equals or larger than the end
 time, instead of stopping the timer, we check whether we want to loop the animation
 and then decide whether to rerun the animations or to stop the timer. We added some
 private fields into the AnimationPanel to keep track of the shapes and animations
 that are being outputted in the panel.

The screenshot is in the resource folder

In our design we have the easyAnimator as the main class. In the main class we take
 the command line into parts and construct the corresponding model and view classes.
 Then we pass those to different controllers and the controller will execute those
 data and link the model with the view to output those data.
In our HybridView, the user starts the animation by clicking the start button. They
 can pause the game with the pause button and resume the game with the resume button.
 They can change the speed by entering the speed they want and click the buttons
 change speed. They can also restart the animation that has been playing in the
 screen by clicking the restart button. On the right side, there is a list of all
 the list in this view. The user can select any shapes they want. If they need
 multiple shapes, press command and then click on those shapes. Then they can
 click the select button and the view will be rerun with only the selected shapes.
 If they want to return to the original view, they can click the reset button
 which will set the animation to the original view with the original speed. If
 the user want to loop the animation, they can just click the loop button and
 the button has a text “loop:off” or “loop:on” which will signal the user whether
 this animation is looping or not. If the user want to export the file. They
 can click export button and they current animation with the selected or all
 the shapes and with whether to loop or not will be exported into the location
 in the command line as a SVG file.

———————————————————————————————————————————————

This code represents the model. It contains an IAnimationModel class that is
 the interface for the model, and an AnimationModel that represents the model
 class. Then there is an IAnimation interface that represents all the instructions
 that can happen in the model. For now only three classes implements this
 interface. They are Move, ChangeColor, and Scale class. Move class moves a
 shape from one place to another. ChangeColor class changes the color data of
 the shape. Scale class scales the shape to the given size. Then there is
 Shape interface that represents all the shape that will be created. An
 AbstractShape abstract class implements the Shape interface. This abstract
 class construct the basic shape and other shapes can just inherent this
 abstraction. For now there are two shape classes, Rectangle class and Oval
 class. When an instruction is passed into the model, the model first creates
 an IAnimation object corresponding to the instruction. All this IAnimation
 objects will be checked for validity and if it is valid, it will be added
 in to a list containing all the instructions. Also, all the Shape object
 need to be added into the list in a model. Then in the method animate, all
 the instructions will be executed. First the list containing all the Shape
 objects will be sorted by their appearing time, then the list containing
 all the IAnimation objects — the instructions, will be sorted by their
 starting time. After that, the model will execute those instruction one
 by one. The IAnimation object pass the instruction to the Shape object,
 and inside the Shape object, the parameters of the given shape will be
 changed. Then the IAnimation object will produce a String output that
 will be added to the output. In the end, all the String output of the
 Shape object will be output first following all the String output about
 all the instructions. Inside the model, the model will throw Illegal
 Argument Exceptions when the instruction is invalid or the Shape that
 are being instruct to animate is not in the model.

————————————————————————————————————————————————

We added getter methods in the model class, including getShapes(),
 getAnimation() that gets the list from model. So we can get the data
 from the model and use them in the view classes.
We moved sort from animate() method in model into addShape and addMove
 methods in model. This way every time a move or a shape is added into
 the list, the list will be sorted and whenever we extract the lists
 from the model, we will have a sorted list to use. Also in the animate()
 method, we call the toString() first and then act() all the animations
 so the textual view can have the correct output.
We added getter methods in all the animation classes including Move,
 Scale, and ChangeColor class. So in the view class we can get the most
 updated information and translate them to different views.
We created a new AbstractAnimation abstract class to abstract all 
the Animations to make the code easier to use and we can have less
 duplicated code.

Our main class checks the input of command line and then construct
 the correct view class.
Inside the view classes, they take in model and a tick time which
 are being checked in the main classes. The model are being
 constructed by the model builder. Then our view gets the most
 updated information of the model through getter methods and
 output them into textual, visual, or svg form of outputs.


———————————————————————————————— 
