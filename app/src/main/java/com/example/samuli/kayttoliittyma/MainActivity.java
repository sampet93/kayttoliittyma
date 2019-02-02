package com.example.samuli.kayttoliittyma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Luodaan funktio, jota kutsutaan kun painetaan "calculate nappia"
    // Jotta funktiota voidaan kutsua sen tarvitsee olla 1. Public, 2. void, 3. vain View parametrina
    public void calculateSum(View view) {
        EditText editTextSum1 = (EditText)findViewById(R.id.editTextSum1);
        EditText editTextSum2 = (EditText)findViewById(R.id.editTextSum2);
        TextView textViewSum = (TextView)findViewById(R.id.textViewSum);

        //textViewSum.setText(editTextSum1.getText().toString() + editTextSum2.getText().toString());
    }

    public void showLog(View view) {

    }
}
