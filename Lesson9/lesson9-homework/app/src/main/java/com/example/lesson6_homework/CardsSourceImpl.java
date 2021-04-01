package com.example.lesson6_homework;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import androidx.room.Room;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CardsSourceImpl implements CardsSource{
    private List<CardData> dataSource;
    private Resources resources;
    public CardsSourceImpl(Resources resources) throws InterruptedException {
        this.resources = resources;
    }


    public CardsSourceImpl init(Context context) throws InterruptedException, ExecutionException {

        DataAccess da=new DataAccess(context);

            List<Note> notes = da.getAllNotes();
            dataSource = new ArrayList<>(notes.size());
                for (int i = 0; i < notes.size(); i++) {
                    dataSource.add(new CardData(notes.get(i).getName(), notes.get(i).getDescription(), notes.get(i).getDateOfCreation()));
            }
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

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
         dataSource.set(position,cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
         dataSource.add(cardData);
    }
}
