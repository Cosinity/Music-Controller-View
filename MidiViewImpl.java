package cs3500.music.view;

import java.util.List;

import javax.sound.midi.*;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements MidiView<Note> {

  private final Sequencer sequencer;
  private Sequence sequence;

  public MidiViewImpl() {
    Sequencer seq = null;

    try {
      seq = MidiSystem.getSequencer();
      this.sequence = new Sequence(Sequence.PPQ, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.sequencer = seq;
  }

  public MidiViewImpl(Sequence sequence) {
    this();
    this.sequence = sequence;
  }

  @Override
  public void play(IMusicController<Note> piece) {
    try {
      this.sequencer.setTempoInMPQ(piece.getTempo());
      Track track = sequence.createTrack();
      this.sequencer.open();
      List<Note> notes = piece.getNotes();
      notes.forEach(n -> {
        try {
          int key = (n.getOctave() * Pitch.values().length) + n.getPitch().ordinal();
          ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON,
                  n.getInstrument(), key, n.getVolume());
          MidiEvent onEvent = new MidiEvent(on, n.getStartTime());
          track.add(onEvent);

          ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF,
                  n.getInstrument(), key, n.getVolume());
          MidiEvent offEvent = new MidiEvent(off,
                  (n.getStartTime() + n.getDuration()));
          track.add(offEvent);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      this.sequencer.setSequence(sequence);
      this.sequencer.start();

      int totalLength = -1;
      for (Note n : notes) {
        if (this.sequencer.getMicrosecondLength() == this.sequencer.getMicrosecondPosition()) {
          this.sequencer.stop();
          this.sequencer.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public long getBeat() {
    return this.sequencer.getTickPosition();
  }

  @Override
  public void pause() {
    this.sequencer.stop();
  }

  @Override
  public void unpause() {
    float temp = this.sequencer.getTempoInMPQ();
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(temp);
  }

  @Override
  public void restart() {
    this.sequencer.setTickPosition(0);
  }
}