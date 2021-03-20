package com.example.lesson6_homework;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_NOTE = "note";
    private static Note currentNote;
    private static Note previousNote;
    public static DataAccess da;
    public NoteFragment() {
        // Required empty public constructor
    }
    public static NoteFragment newInstance (Note currentNote) {
        NoteFragment f = new NoteFragment();

// создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, currentNote);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        TextInputEditText ti = getView().findViewById(R.id.note_text);
        String text=ti.getText().toString();
        da.saveNote(currentNote.getNoteIndex(),text);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }
@Override
public void onStop() {
            TextInputEditText ti = getView().findViewById(R.id.note_text);
            String text = ti.getText().toString();
            da.saveNote(previousNote.getNoteIndex(), text);
    super.onStop();
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        previousNote=currentNote;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextView noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(currentNote.getName());
        da=new DataAccess(getContext());
        try {
            String text = da.getNote(currentNote.getNoteIndex());
            TextInputEditText ti = view.findViewById(R.id.note_text);
            ti.setText(text);
        }
        catch(RuntimeException e){

        }
        return view;
    }
}