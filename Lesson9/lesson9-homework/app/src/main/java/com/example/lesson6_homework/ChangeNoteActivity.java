package com.example.lesson6_homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ChangeNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_note);

        EditText et1 = (EditText) findViewById(R.id.editTextName);
        EditText et2 = (EditText) findViewById(R.id.editTextDescription);
        DataAccess da = new DataAccess(getApplicationContext());
        try {
            if (da.getNote(getIntent().getIntExtra("note", -1)) != null) {
                et1.setText(da.getNote(getIntent().getIntExtra("note", -1)).getName());
                et2.setText(da.getNote(getIntent().getIntExtra("note", -1)).getDescription());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button button = findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = et1.getText().toString();
                        String description = et2.getText().toString();
                        try {
                            if (da.getNote(getIntent().getIntExtra("note", -1)) != null) {
                                da.updateNote(getIntent().getIntExtra("note", -1), name, description);
                                Intent intentResult = new Intent();
                                intentResult.putExtra("date",da.getNote(getIntent().getIntExtra("note", -1)).getDateOfCreation());
                                intentResult.putExtra("name",name);
                                intentResult.putExtra("description",description);
                                intentResult.putExtra("id",getIntent().getIntExtra("note", -1));
                                intentResult.putExtra("position",getIntent().getIntExtra("position",-1));
                                setResult(RESULT_OK, intentResult);
                            } else {
                                da.addNote(getIntent().getIntExtra("note", -1), name, description);
                                Intent intentResult = new Intent();
                                intentResult.putExtra("date",new Date().toString());
                                intentResult.putExtra("name",name);
                                intentResult.putExtra("description",description);
                                intentResult.putExtra("id",getIntent().getIntExtra("note", -1));
                                setResult(RESULT_OK, intentResult);
                            }
                        } catch (Exception e) {

                        }

                        finish();
                    }
                }
        );
    }
}
