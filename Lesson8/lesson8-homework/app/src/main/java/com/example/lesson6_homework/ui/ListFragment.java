package com.example.lesson6_homework.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.lesson6_homework.AppDatabase;
import com.example.lesson6_homework.CardsSource;
import com.example.lesson6_homework.CardsSourceImpl;
import com.example.lesson6_homework.Note;
import com.example.lesson6_homework.NoteActivity;
import com.example.lesson6_homework.NoteDao;
import com.example.lesson6_homework.NoteFragment;
import com.example.lesson6_homework.R;

import java.util.List;

public class ListFragment extends Fragment {
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container,
                false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "notes").build();
        CardsSource data = null;
        try {
            data = new CardsSourceImpl(getResources(),getContext()).init(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initRecyclerView(recyclerView, data);
        return view;
    }


    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        recyclerView.setHasFixedSize(true);
// Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);
        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        recyclerView.addItemDecoration(itemDecoration);
        // Установим слушателя
        adapter.SetOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_PORTRAIT) {
                    Intent note = new Intent(getActivity(),
                            NoteActivity.class);
                    note.putExtra("note",position);
                    startActivity(note);
                }
                else{
                    NoteFragment detail = NoteFragment.newInstance(position);
                    FragmentManager fragmentManager =
                            requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction =
                            fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.note_container, detail);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
            }
        });

    }
}