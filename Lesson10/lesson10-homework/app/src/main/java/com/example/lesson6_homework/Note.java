package com.example.lesson6_homework;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Parcelable {
    @PrimaryKey
    private int noteIndex;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "dateOfCreation")
    private String dateOfCreation;
    @ColumnInfo(name = "note")
    private String note;

    public Note() {

    }

    public int getNoteIndex() {
        return noteIndex;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getNote() {
        return note;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Note(int id, String name, String description, String dateOfCreation, String note) {
        this.noteIndex = id;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.note = note;
    }

    protected Note(Parcel in) {
        noteIndex = in.readInt();
        String[] arr = new String[]{name, description, dateOfCreation, note};
        in.readStringArray(arr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteIndex);
        String[] arr = new String[]{name, description, dateOfCreation, note};
        dest.writeStringArray(arr);
    }
}
