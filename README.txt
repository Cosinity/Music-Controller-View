The two largest additions are those of the InteractiveMusicController and the InteractiveView interface (and its implementation).

InteractiveView is a new view interface that extends the old interface, designed for the new interactive-composite view. Many of its
new methods are to get the text from the text boxes on the view, to be passed to the constructor. It also has methods to add mouse-
key- and actionlisteners, and methods for some keyboard functions.

InteractiveMusicController extends the old music controller, and adds additional functionality related to the new interactive view.
It must be used with the new composite view because it passes listeners and calls other methods exclusive to InteractiveView.
It acts as the action listener for the buttons on the view.

The implementation of InteractiveView, CompositeView, functions much like the old GUI view with a MIDI view added on top. It has two
panels: the music and the editing tools. The music panel is of a new type, CompositeMusicPanel which extends the previous MusicPanel.
It provides two new functionalities: drawing a red line at the current beat, and settigThe musical staff scrolls automatically in 
time with the music. The editing panel provides five text fields and three buttons: the top two buttons are used to add a note 
with the given parameters, or edit the selected note to have the given ones. Changes to a note are not saved until the edit button 
is pressed. The bottom button changes the tempo of the song and transposes it up or down according to the parameters in the text fields.

Program arguments should be given in the following format: {file name} {view type}
	e.g. mary-little-lamb.txt visual
The following keywords correspond to the following views:
	"visual" creates a GUI view
	"midi" creates a MIDI view
	"console" creates a console view
	"composite" creates a composite view

Key bindings:
	"space" or "p" to paus and unpause the piece
	"home" to go to the beginning of the piece
	"end" to go to the end of the piece

Mouse controls:
	Left-click a note on the staff to select it for editing
	Right-click a note on the staff to remove it

Changes from previous assignment:
	Pitch class was cleaned up and a create(String) method was added
	In MusicPanel:
		height field was added as an optimization
		NOTE_SIZE was made public and static to be accessed from other classes
		lastNote fields and getWidth() method were added for scrolling purposes
		piece, lowNote, and highNote fields and staffHeight() method were made protected
	setTempo() method was added to IMusicController
	views were refactored to take a list of notes rather than a Controller