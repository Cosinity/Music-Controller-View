package cs3500.music.tests.mocks;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Overrides the sequence used by a sequencer to also have an event getter
 */
public class SequenceMock extends Sequence {
  private ArrayList<MidiEvent> events;
  public static StringBuilder log = new StringBuilder();

  public SequenceMock(float divisionType, int resolution) throws InvalidMidiDataException {
    super(divisionType, resolution);
    this.events = new ArrayList<MidiEvent>();
  }

  /**
   * Records the played notes to a human-readable log output.
   */
  public void logEvents() {
    for (Track track : this.tracks) {
      for (int i = 0; i < track.size(); i++) {
        this.events.add(track.get(i));
      }
    }

    for (MidiEvent event : this.events) {
      MidiMessage curMessage = event.getMessage();
      if (curMessage instanceof ShortMessage) {
        ShortMessage message = (ShortMessage) curMessage;
        String temp = "";
        temp += "Beat: " + event.getTick() + ". ";
        if (message.getCommand() == ShortMessage.NOTE_OFF) {
          temp += "Note Off. ";
        } else {
          temp += "Note On. ";
        }
        temp += "Instrument: " + message.getChannel() + ". ";
        temp += "Key: " + message.getData1() + ". ";
        temp += "Volume: " + message.getData2() + ".";
        log.append(temp + "\n");
      }
    }
  }

  @Override
  public long getTickLength() {
    this.logEvents();
    return 0;
  }
}
