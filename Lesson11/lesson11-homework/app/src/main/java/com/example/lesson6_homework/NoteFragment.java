package com.example.lesson6_homework;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private int id;
    private int previousId;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(int id) {
        NoteFragment f = new NoteFragment();

// создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putInt("note", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        TextInputEditText ti = getView().findViewById(R.id.note_text);
        String text = ti.getText().toString();
        DataAccess da=new DataAccess(getContext());
            da.updateNote(previousId,text);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("note");
        }
    }

    @Override
    public void onStop() {
        TextInputEditText ti = getView().findViewById(R.id.note_text);
        String text = ti.getText().toString();
        DataAccess da=new DataAccess(getContext());
            da.updateNote(previousId,text);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        previousId = id;
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        DataAccess da=new DataAccess(getContext());
        Note n= null;
            n = da.getNote(id);
            if (n!=null) {
                TextInputEditText ti = view.findViewById(R.id.note_text);
                ti.setText(n.getNote());
                TextView tv = view.findViewById(R.id.textView);
                tv.setText(n.getName());
            }
        return view;
    }
}