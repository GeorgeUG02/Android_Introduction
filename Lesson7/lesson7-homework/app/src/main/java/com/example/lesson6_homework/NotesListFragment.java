package com.example.lesson6_homework;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesListFragment} factory method to
 * create an instance of this fragment.
 */
public class NotesListFragment extends Fragment {
    public static final String CURRENT_NOTE = "CurrentNote";
    private static Note currentNote;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public NotesListFragment() {
        // Required empty public constructor
    }

    private boolean isLandscape;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        initPopupMenu(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        Note n = new Note(0, "Default note", "This is default note", new Date().toString());
        Note n2 = new Note(1, "Default note2", "This is default note2", new Date().toString());
        TextView tv = new TextView(getContext());
        tv.setText(n.getName() + "\n" + n.getDescription() + "\n" + n.getDateOfCreation());
        tv.setTextSize(30);
        layoutView.addView(tv);
        tv.setId(1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote = n;
                showNote(n);
            }
        });
        TextView tv2 = new TextView(getContext());
        tv2.setText(n2.getName() + " " + n2.getDescription() + " " + n2.getDateOfCreation());
        tv2.setTextSize(30);
        layoutView.addView(tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote = n2;
                showNote(n2);
            }
        });
        tv2.setId(2);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void showPortNote(Note currentNote) {
        Intent intent = new Intent(getActivity(), NoteActivity.class);
        intent.putExtra("note", currentNote);
        startActivity(intent);
    }

    private void showLandNote(Note currentNote) {
        NoteFragment detail = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager =
                requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
// Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, "Default note", "This is default note", new Date().toString());
        }
        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void initPopupMenu(View view) {
        for(int i=1;i<3;i++) {
            View v = view.findViewById(i);
            v.setOnLongClickListener
                    (new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Activity activity = requireActivity();
                            PopupMenu popupMenu = new PopupMenu(activity, v);
                            activity.getMenuInflater().inflate(R.menu.popup,
                                    popupMenu.getMenu());
                            Menu menu = popupMenu.getMenu();
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    int id = item.getItemId();
                                    switch (id) {
                                        case R.id.item1_popup:
                                            Toast.makeText(getContext(), "Chosen popup item 1", Toast.LENGTH_SHORT).show();
                                            return true;
                                        case R.id.item2_popup:
                                            Toast.makeText(getContext(), "Chosen popup item 2", Toast.LENGTH_SHORT).show();
                                            return true;
                                    }
                                    return true;
                                }
                            });
                            popupMenu.show();
                            ;
                            return false;
                        }
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        return view;
    }
}