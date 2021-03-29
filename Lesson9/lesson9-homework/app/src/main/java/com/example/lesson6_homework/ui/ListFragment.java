package com.example.lesson6_homework.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson6_homework.CardData;
import com.example.lesson6_homework.CardsSource;
import com.example.lesson6_homework.CardsSourceImpl;
import com.example.lesson6_homework.ChangeNoteActivity;
import com.example.lesson6_homework.DataAccess;
import com.example.lesson6_homework.MainActivity;
import com.example.lesson6_homework.Note;
import com.example.lesson6_homework.NoteActivity;
import com.example.lesson6_homework.NoteFragment;
import com.example.lesson6_homework.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFragment extends Fragment {
    private CardsSource data;
    private ListAdapter adapter;
    private RecyclerView recyclerView;
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1 && requestCode!=2) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (requestCode==1) {
            if (data!=null) {
                this.data.addCardData(new CardData(data.getStringExtra("name"), data.getStringExtra("description"), data.getStringExtra("date")));
                adapter.notifyItemInserted(this.data.size() - 1);
                recyclerView.scrollToPosition(this.data.size() - 1);
            }
        }
        if (requestCode==2) {
            if (data!=null) {
                this.data.updateCardData(data.getIntExtra("position", -1), new CardData(data.getStringExtra("name"), data.getStringExtra("description"), data.getStringExtra("date")));
                adapter.notifyItemChanged(data.getIntExtra("position", -1));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_list, container,
                false);
        try {
            data = new CardsSourceImpl(getResources()).init(getContext());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
// Получим источник данных для списка
        initRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (adapter==null) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent changeNote = new Intent(getActivity(),
                    ChangeNoteActivity.class);
            DataAccess da = new DataAccess(getContext());
            try {
                List<Note> l = da.getAllNotes();
                changeNote.putExtra("note", l.get(l.size() - 1).getNoteIndex()+1);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startActivityForResult(changeNote, 1);
        }
// Обработка выбора пункта меню приложения (активити)
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
// Здесь определяем меню приложения (активити)
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
// Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(data, this);
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
                DataAccess da = new DataAccess(getContext());
                try {
                    List<Note> l = da.getAllNotes();
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_PORTRAIT) {
                        Intent note = new Intent(getActivity(),
                                NoteActivity.class);
                        note.putExtra("note", l.get(position).getNoteIndex());
                        startActivity(note);
                    } else {
                        NoteFragment detail = NoteFragment.newInstance(l.get(position).getNoteIndex());
                        FragmentManager fragmentManager =
                                requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction =
                                fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.note_container, detail);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.commit();
                    }
                } catch (Exception e) {

                }

            }
        });

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (adapter==null) {
            return true;
        }
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                Intent changeNote = new Intent(getActivity(),
                        ChangeNoteActivity.class);
                DataAccess da = new DataAccess(getContext());
                try {
                    List<Note> l = da.getAllNotes();
                    changeNote.putExtra("note", l.get(position).getNoteIndex());
                    changeNote.putExtra("position",position);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivityForResult(changeNote, 2);
                return true;
            case R.id.action_delete:
                DataAccess da2 = new DataAccess(getContext());
                try {
                    List<Note> l = da2.getAllNotes();
                    da2.deleteNote(l.get(position).getNoteIndex());
                    data.deleteCardData(position);
                    adapter.notifyItemRemoved(position);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
        }
        return super.onContextItemSelected(item);
    }
}