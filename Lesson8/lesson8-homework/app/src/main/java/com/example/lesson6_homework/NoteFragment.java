package com.example.lesson6_homework;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private static int id;
    private static int previous_id;
    public NoteFragment() {
        // Required empty public constructor
    }
    public static NoteFragment newInstance (int id) {
        NoteFragment f = new NoteFragment();

// создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putInt("note",id);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        TextInputEditText ti = getView().findViewById(R.id.note_text);
        String text=ti.getText().toString();
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "notes").build();
        NoteDao noteDao = db.noteDao();
        Thread t =new Thread(()-> {
            Note n = noteDao.getById(previous_id);
            n.setNote(text);
            noteDao.update(n);

        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            id = getArguments().getInt("note");
        }
    }
@Override
public void onStop() {
    AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
            AppDatabase.class, "notes").build();
    NoteDao noteDao = db.noteDao();
    Thread t =new Thread(()-> {
        Note n = noteDao.getById(previous_id);
        TextInputEditText ti = getView().findViewById(R.id.note_text);
        n.setNote(ti.getText().toString());
        noteDao.update(n);
    });
    t.start();
    try {
        t.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    super.onStop();
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        previous_id=id;
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "notes").build();
        NoteDao noteDao = db.noteDao();
        final Note[] n = new Note[1];
        Thread t =new Thread(()-> {
            n[0] =noteDao.getById(id);
            
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextInputEditText ti = view.findViewById(R.id.note_text);
        ti.setText(n[0].getNote());
        return view;
    }
}