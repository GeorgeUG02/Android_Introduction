package com.example.lesson6_homework;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int noteIndex;
    private String name;
    private String description;
    private String dateOfCreation;

    public Note(int noteIndex, String name, String description, String dateOfCreation) {
        this.noteIndex = noteIndex;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
    }

    protected Note(Parcel in) {
        noteIndex=in.readInt();
        name=in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public int getNoteIndex() {
        return noteIndex;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel (Parcel in) {
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
          dest.writeString(name);
    }
}
