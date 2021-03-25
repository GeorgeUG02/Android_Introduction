package com.example.lesson6_homework;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import androidx.room.Room;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class CardsSourceImpl implements CardsSource{
    private List<CardData> dataSource;
    private Resources resources;
    public CardsSourceImpl(Resources resources, Context context) throws InterruptedException {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notes").build();
        NoteDao noteDao = db.noteDao();
        Thread t=new Thread(()->{
            List<Note> notes = noteDao.getAll();
            dataSource = new ArrayList<>(notes.size());
            System.out.println(notes.size());
        }
        );
        t.start();
        t.join();
        this.resources = resources;
    }

    static int getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }
    public CardsSourceImpl init(Context context) throws InterruptedException {

        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notes").build();
        NoteDao noteDao = db.noteDao();
        Thread t=new Thread(()-> {
            List<Note> notes = noteDao.getAll();
            try {
                for (int i = 0; i < getCapacity((ArrayList<CardData>) dataSource); i++) {
                    dataSource.add(new CardData(notes.get(i).getName(), notes.get(i).getDescription(), notes.get(i).getDateOfCreation()));
                }
            }catch(Exception e){

            }
        });
           t.start();
        t.join();
        return this;
    }
    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
