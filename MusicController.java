package cs3500.music.controller;

import java.util.List;

import cs3500.music.model.GenericMusicPiece;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicView;

/**
 * Controller for a music model.
 */
public class MusicController implements IMusicController {

  private GenericMusicPiece model;

  public MusicController() {
    this.model = new MusicModel();
  }

  public MusicController(GenericMusicPiece piece) {
    this.model = piece;
  }

  @Override
  public void play(IMusicView view) {
    view.play(this);
  }

  @Override
  public void append(GenericMusicPiece m) {
    m.appendTo(this.model);
  }

  @Override
  public void mix(GenericMusicPiece m) {
    m.mixTo(this.model);
  }

  @Override
  public void addNote(Note n) {
    this.model.addNote(n);
  }

  @Override
  public void removeNote(Note n) {
    this.model.remove(n);
  }

  @Override
  public List<Note> getNotes() {
    return this.model.getNotes();
  }

  @Override
  public List<Note> getNotes(int b) {
    return this.model.getNotes(b);
  }

  @Override
  public void changePitch(Note n, int s) {
    this.model.changePitch(n, s);
  }

  @Override
  public void transpose(int s) {
    this.model.transpose(s);
  }

  @Override
  public void changeStart(Note n, int s) {
    this.model.changeStart(n, s);
  }

  @Override
  public void changeStart(int s) {
    this.model.changeStart(s);
  }

  @Override
  public void changeDuration(Note n, int s) {
    this.model.changeDuration(n, s);
  }

  @Override
  public boolean isFlat() {
    return this.model.isFlat();
  }
}