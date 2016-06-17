package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cs3500.music.controller.IMusicController;
import cs3500.music.controller.MusicController;
import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Text-based representation of a musical composition
 */
public class ConsoleView implements IMusicView {


  List<Note> notes;
  int totalLength;
  IMusicController curController;

  @Override
  public void play(IMusicController controller) {
    this.curController = controller;
    this.notes = curController.getNotes().stream().map(Note::copy).collect(Collectors.toList());
    Note highest = this.getHighestNote();
    Note lowest = this.getLowestNote();
    int hiOct = highest.getOctave();
    int loOct = lowest.getOctave();
    Pitch lowestPitch = lowest.getPitch();
    Pitch highestPitch = highest.getPitch();

    this.totalLength = -1;

    for (Note n : this.notes) {
      if (n.getStartTime() + n.getDuration() - 1 > totalLength) {
        totalLength = n.getStartTime() + n.getDuration() - 1;
      }
    }

    if (this.notes.isEmpty()) {
      throw new IllegalStateException("No notes in composition!");
    }

    // Create a header
    StringBuilder output = new StringBuilder(makeHeader() + "\n");
    String[] rows = new String[this.totalLength + 1];

    // Sort list of notes
    Collections.sort(this.notes);

    // Split notes up by octave
    Queue<Note> firstOct = new LinkedList<Note>();
    Queue<Note> lastOct = new LinkedList<Note>();
    Map<Integer, Queue<Note>> midOcts = new HashMap<Integer, Queue<Note>>();

    this.notes.forEach(n -> {
      if (n.getOctave() == loOct) {
        firstOct.add(n);
      } else if (n.getOctave() == hiOct) {
        lastOct.add(n);
      } else {
        midOcts.putIfAbsent(n.getOctave(), new LinkedList<Note>());
        midOcts.get(n.getOctave()).add(n);
      }
    });

    // Add the sidebar
    for (int i = 0; i < rows.length; i++) {
      rows[i] = String.format("%" + ("" + this.totalLength).length() + "s", i);
    }

    this.addOctave(firstOct, rows, p -> p.compareTo(lowestPitch) >= 0);
    midOcts.values().forEach(o -> this.addOctave(o, rows, p -> p != null));
    this.addOctave(lastOct, rows, p -> p.compareTo(highestPitch) <= 0);

    Arrays.stream(rows).forEach(r -> output.append(r + "\n"));

    System.out.println(output.toString());
  }

  /**
   * Creates the header for the printing of the music piece
   *
   * @return the header for the music piece's print method
   */
  private String makeHeader() {
    List<Note> header = notesBetween();
    // if the display is meant to be flat, then make the header flat
    if (this.curController.isFlat()) {
      header.forEach(Note::toggleFlat);
    }
    String head = "";
    // whitespace padding at the start
    for (int i = 0; i < (this.totalLength + "").length(); i++) {
      head += " ";
    }
    // adds each note
    for (Note n : header) {
      head += formatHeaderNote(n);
    }
    return head;
  }

  /**
   * Formats the notes in the header to be 5 spaces wide; if an invalid note is passed, throws an
   * illegal argument exception
   *
   * @return one note formatted to be 5 spaces wide
   * @throws IllegalArgumentException if an invalid note is passed
   */
  private String formatHeaderNote(Note n) throws IllegalArgumentException {
    String note = n.toString();
    switch (note.length()) {
      case 2:
        return " " + note + "  ";
      case 3:
        return " " + note + " ";
      case 4:
        return " " + note;
      case 5:
        return note;
      default:
        throw new IllegalArgumentException("Malformed note: " + note);
    }
  }

  private void addOctave(Queue<Note> octave, String[] rows, Predicate<Pitch> pred) {
    if (octave.isEmpty()) {
      return;
    }
    Arrays.stream(Pitch.values()).filter(pred).forEach(p -> {
      List<Note> currentNotes = new ArrayList<Note>();
      for (int i = 0; i < rows.length; i++) {
        /* while (!octave.isEmpty() && octave.peek().getStartTime() == i
                && octave.peek().getPitch().equals(p)) {
          currentNotes.add(octave.poll());
        } */

        for (Note n : octave) {
          if (n.getStartTime() == i && n.getPitch().equals(p)) {
            currentNotes.add(n);
          }
        }

        if (currentNotes.isEmpty()) {
          rows[i] += "     ";
        } else if (currentNotes.get(currentNotes.size() - 1).getStartTime() == i) {
          rows[i] += "  X  ";
        } else {
          rows[i] += "  |  ";
        }
        final int curIdx = i;
        currentNotes.removeIf(n -> (n.getStartTime() + n.getDuration() - 1) <= curIdx);
      }
    });
  }

  /**
   * Returns a list of notes ranging from the piece's lowest note to its highest note
   *
   * @return a list of notes ranging from the piece's lowest note to its highest note
   */
  private List<Note> notesBetween() {
    ArrayList<Note> headerNotes = new ArrayList<Note>();
    Note low = this.getLowestNote();
    Note high = this.getHighestNote();

    // add all the relevant pitches for the lowest octave
    Arrays.stream(Pitch.values()).filter(p -> p.compareTo(low.getPitch()) >= 0)
            .forEach(p -> headerNotes.add(new Note(p, low.getOctave(), 1, 0)));

    if (low.getOctave() != high.getOctave()) {
      // add all pitches for every octave o > lowOctave && o < highOctave
      IntStream.range(low.getOctave() + 1, high.getOctave()).forEach(o ->
              Arrays.stream(Pitch.values()).forEach(p -> headerNotes.add(new Note(p, o, 1, 0))));

      // add all relevant pitches for the highest octave
      Arrays.stream(Pitch.values()).filter(p -> p.compareTo(high.getPitch()) <= 0)
              .forEach(p -> headerNotes.add(new Note(p, high.getOctave(), 1, 0)));
    }

    // account for the case where there's a single octave and the streams
    // generate stuff greater than max and less than min
    headerNotes.removeIf(n -> n.compareTo(getHighestNote()) > 0);

    return headerNotes;
  }

  private Note getLowestNote() {
    // initialize as highest note
    Note low = this.notes.get(0);

    // if a lower note is found, replace lowest note
    for (Note n : this.notes) {
      if (low == null || n.compareTo(low) < 0) {
        low = n;
      }
    }

    return low;
  }

  private Note getHighestNote() {
    // initialize as lowest possible note
    Note high = this.notes.get(this.notes.size() - 1);

    // if a higher note is found, replace highest note
    for (Note n : this.notes) {
      if (high == null || n.compareTo(high) > 0) {
        high = n;
      }
    }

    return high;
  }
}