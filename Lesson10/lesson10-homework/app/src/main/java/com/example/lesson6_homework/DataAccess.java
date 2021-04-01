package com.example.lesson6_homework;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataAccess {
    private NoteDao noteDao;

    public DataAccess(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notes").allowMainThreadQueries().build();
        this.noteDao = db.noteDao();
    }

    public List<Note> getAllNotes() throws ExecutionException, InterruptedException {
        List<Note> l = noteDao.getAll();
        return l;
    }

    public Note getNote(int id) throws ExecutionException, InterruptedException {
        Note n = noteDao.getById(id);
        return n;
    }

    public void addNote(int id, String name, String description) throws ExecutionException, InterruptedException {
        noteDao.insert(new Note(id, name, description, new Date().toString(), ""));

    }

    public void updateNote(int id, String name, String description) throws ExecutionException, InterruptedException {
        Note n = noteDao.getById(id);
        noteDao.update(new Note(id, name, description, n.getDateOfCreation(), n.getNote()));
    }

    public void updateNote(int id, String note) throws ExecutionException, InterruptedException {
        Note n = noteDao.getById(id);
        noteDao.update(new Note(id, n.getName(), n.getDescription(), n.getDateOfCreation(), note));
    }

    public void deleteNote(int id) throws ExecutionException, InterruptedException {
        Note n = noteDao.getById(id);
        noteDao.delete(n);
    }
}
