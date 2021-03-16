package com.example.lesson5_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
public class CalculatorActivity extends AppCompatActivity {
    private static final String THEME = "Theme";
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;
    private TextView resultField; // текстовое поле для вывода результата
    private EditText numberField;   // поле для ввода числа
    private TextView operationField;    // текстовое поле для вывода знака операции
    private Double operand = null;  // операнд операции
    private String lastOperation = "="; // последняя операция
    private static int theme = 0;
    private static Intent i;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != REQUEST_CODE_SETTING_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (data.getStringExtra(THEME).equals("Light")) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            theme = sp.getInt("THEME", R.style.LightTheme);
            getIntent().putExtra("result", resultField.getText().toString());
            getIntent().putExtra("number", numberField.getText().toString());
            getIntent().putExtra("operation", operationField.getText().toString());
            i = getIntent();
            finish();
            startActivity(getIntent());
        }
        if (data.getStringExtra(THEME).equals("Dark")) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            theme = sp.getInt("THEME", R.style.DarkTheme);
            getIntent().putExtra("result", resultField.getText().toString());
            getIntent().putExtra("number", numberField.getText().toString());
            getIntent().putExtra("operation", operationField.getText().toString());
            i = getIntent();
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent runSettings = new Intent(CalculatorActivity.this, SettingsActivity.class);
        startActivityForResult(runSettings, REQUEST_CODE_SETTING_ACTIVITY);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (theme != 0) {
            setTheme(theme);
        }
        setContentView(R.layout.activity_main);
        // получаем все поля по id из activity_main.xml
        resultField = (TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
        if (i != null) {
            resultField.setText(i.getStringExtra("result"));
            numberField.setText(i.getStringExtra("number"));
            operationField.setText(i.getStringExtra("operation"));
            lastOperation = operationField.getText().toString();
            try {
                String s = resultField.getText().toString();
                operand = Double.valueOf(s.replace(',', '.'));
            } catch (NumberFormatException e) {

            }
            i = null;
        }

    }

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null) {
            outState.putDouble("OPERAND", operand);
        }
        super.onSaveInstanceState(outState);
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    // обработка нажатия на числовую кнопку
    public void onNumberClick(View view) {

        MaterialButton button = (MaterialButton) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    // обработка нажатия на кнопку операции
    public void onOperationClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String op = button.getText().toString();
        if (op.equals("C")) {
            numberField.setText("");
            lastOperation = "=";
            operationField.setText("=");
            return;
        }
        if (op.equals("+/-")) {
            try {
                resultField.setText("" + -Integer.parseInt(numberField.getText().toString()));
                numberField.setText("" + -Integer.parseInt(numberField.getText().toString()));
            } catch (NumberFormatException e) {
                numberField.setText("");
            }
            return;
        }
        if (op.equals("%")) {
            operationField.setText("%");
            try {
                resultField.setText("" + 0.01 * Integer.parseInt(numberField.getText().toString()));
                numberField.setText("" + 0.01 * Integer.parseInt(numberField.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return;
        }
        String number = numberField.getText().toString();
        // если введенно что-нибудь
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "^":
                    operand = Math.pow(operand, number);
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}