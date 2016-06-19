When combining the models we realized that both of ours were fairly similar, so we chose to use Nick's with some minor modifications.

Our Controller in effect provides all the functionality of both the model and the view. It has a field to contain a model (the piece
to play); the method play() takes a View as an input. We chose to do it this way so that one piece could easily be displayed by any
view without having to change the controller itself.

The implementation of the controller, MusicController, simply delegates all relevant methods to its model (or view) as appropriate.

Our View interface, IMusicView, has one method, play(), that takes in a Controller and "displays" the piece in whatever method this view uses.

The console view, ConsoleView, is mostly similar to the method from the last assignment, but we have added an output field so that it can be
printed to the console or any other output stream should one desire.

The GUI view, GuiViewFrame, acts as mostly a frame for the MusicPanel. In the panel there are two public methods: paintComponent() and setPiece().
setPiece() is used to assign (or change) the music that the panel is used to display. paintComponent() draws the staff, notes, note names, and beat numbers.

The MIDI view, MidiViewImpl, {insert explanation here}

Within MusicModel we have added a builder class used when creating a piece from a file.

The class ViewFactory has one static method that takes in a String, which creates a View based on the input string.
	"g", "gui", or "GUI" creates a GUI view
	"m", "midi", or "MIDI" creates a MIDI view
	"c", "console", or "Console" creates a console view