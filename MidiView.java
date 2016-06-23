package cs3500.music.view;

/**
 * Addition to the View interface that allows for fetching of beats
 */
public interface MidiView<Note> extends IMusicView<Note> {
  long getBeats();
}
