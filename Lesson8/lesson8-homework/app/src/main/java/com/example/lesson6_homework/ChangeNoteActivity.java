package com.example.lesson6_homework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Date;

public class ChangeNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_note);
        Button button=findViewById(R.id.button);
        TextView tv1=findViewById(R.id.textView2);
        TextView tv2=findViewById(R.id.textView3);
        String name=tv1.getText().toString();
        String description=tv2.getText().toString();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "notes").build();
                        NoteDao noteDao = db.noteDao();
                        Thread t=new Thread(()->{
                            if (noteDao.getById(getIntent().getIntExtra("note", -1)) != null) {

                                    Note n = noteDao.getById(getIntent().getIntExtra("note", -1));
                                    n.setName(name);
                                    n.setDescription(description);
                                    noteDao.update(n);

                            } else {
                                    Note n = new Note(getIntent().getIntExtra("note", -1), name, description, new Date().toString(), "");
                                    noteDao.insert(n);
                            }
                        });
                        t.start();
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }
        );
    }
}
