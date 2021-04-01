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
    public DataAccess(Context context){
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notes").build();
        this.noteDao = db.noteDao();
    }
    public List<Note> getAllNotes() throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        Future f=es.submit(()-> noteDao.getAll());
        List<Note> l= (List<Note>) f.get();
        es.shutdown();
        return l;
    }
    public Note getNote(int id) throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        final Note[] n = new Note[1];
        Future f=es.submit(()->{
             n[0] = noteDao.getById(id);
        });
        f.get();
        es.shutdown();
        return n[0];
    }
    public void addNote(int id,String name,String description) throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        Future f=es.submit(()->{
           noteDao.insert(new Note(id,name,description,new Date().toString(),""));
        });
        f.get();
        es.shutdown();
    }
    public void updateNote(int id,String name,String description) throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        Future f=es.submit(()->{
            Note n=noteDao.getById(id);
            noteDao.update(new Note(id,name,description,n.getDateOfCreation(),n.getNote()));
        });
        f.get();
        es.shutdown();
    }
    public void updateNote(int id,String note) throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        Future f=es.submit(()->{
            Note n=noteDao.getById(id);
            noteDao.update(new Note(id,n.getName(),n.getDescription(),n.getDateOfCreation(),note));
        });
        f.get();
        es.shutdown();
    }
    public void deleteNote(int id) throws ExecutionException, InterruptedException {
        ExecutorService es= Executors.newSingleThreadExecutor();
        Future f=es.submit(()->{
            Note n=noteDao.getById(id);
            noteDao.delete(n);
        });
        f.get();
        es.shutdown();
    }
}
