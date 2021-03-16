package com.example.lesson5_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    private static final String THEME="Theme";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button btnReturn = findViewById(R.id.button);
        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
                if (rb1.isChecked()) {
                    Intent intentResult = new Intent();
                    intentResult.putExtra(THEME, "Light");
                    setResult(RESULT_OK, intentResult);
                    finish();
                }
                if (rb2.isChecked()) {
                    Intent intentResult = new Intent();
                    intentResult.putExtra(THEME, "Dark");
                    setResult(RESULT_OK, intentResult);
                    finish();
                }
            }
        });
        
    }

}
