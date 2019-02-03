package com.example.samuli.kayttoliittyma;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.samuli.kayttoliittyma.log";
    public static final String LOG_FILE_NAME = "log.txt";
    public String writeText;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    // Luodaan kaikille tarvittaville vieweille muuttuja.
    EditText editTextSum1;
    EditText editTextSum2;
    EditText editTextDiff1;
    EditText editTextDiff2;
    EditText editTextMultiply1;
    EditText editTextMultiply2;
    EditText editTextDivide1;
    EditText editTextDivide2;
    TextView textViewSum;
    TextView textViewDiff;
    TextView textViewMultiply;
    TextView textViewDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Määritellään komponentit käyttöä varten
        editTextSum1 = findViewById(R.id.editTextSum1);
        editTextSum2 = findViewById(R.id.editTextSum2);
        editTextDiff1 = findViewById(R.id.editTextDiff1);
        editTextDiff2 = findViewById(R.id.editTextDiff2);
        editTextMultiply1 = findViewById(R.id.editTextMultiply1);
        editTextMultiply2 = findViewById(R.id.editTextMultiply2);
        editTextDivide1 = findViewById(R.id.editTextDivide1);
        editTextDivide2 = findViewById(R.id.editTextDivide2);
        textViewSum = findViewById(R.id.textViewSum);
        textViewDiff = findViewById(R.id.textViewDiff);
        textViewMultiply = findViewById(R.id.textViewMultiply);
        textViewDivide = findViewById(R.id.textViewDivide);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE:
                //Jos pyyntö hylätään
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    saveTxt(writeText);
                }
                else {
                    // Pyyntö hylätty
                    Toast.makeText(this, "Permission req", Toast.LENGTH_SHORT).show();
                }
        }
    }

    // Luodaan funktio, jota kutsutaan kun painetaan "calculate nappia"
    // Jotta funktiota voidaan kutsua sen tarvitsee olla 1. Public, 2. void, 3. vain View parametrina
    public void calculateResult(View v) {


        switch(v.getId()){
            case R.id.buttonSum:
                try {
                    int num1 = Integer.parseInt(editTextSum1.getText().toString());
                    int num2 = Integer.parseInt(editTextSum2.getText().toString());
                    int res = num1 + num2;

                    textViewSum.setText(String.valueOf(num1 + num2));
                    SaveFile(String.valueOf(num1) + " + " + String.valueOf(num2) + " = " + String.valueOf(res));
                }
                catch (Exception e){
                }
                break;

            case R.id.buttonDiff:
                try {
                    int num1 = Integer.parseInt(editTextDiff1.getText().toString());
                    int num2 = Integer.parseInt(editTextDiff2.getText().toString());
                    int res = num1 - num2;

                    SaveFile(String.valueOf(num1) + " - " + String.valueOf(num2) + " = " + String.valueOf(res));
                    textViewDiff.setText(String.valueOf(num1 - num2));
                }
                catch (Exception e){
                }
                break;

            case R.id.buttonMultiply:
                try {
                    int num1 = Integer.parseInt(editTextMultiply1.getText().toString());
                    int num2 = Integer.parseInt(editTextMultiply2.getText().toString());
                    int res = num1 * num2;

                    SaveFile(String.valueOf(num1) + " + " + String.valueOf(num2) + " = " + String.valueOf(res));
                    textViewMultiply.setText(String.valueOf(num1 * num2));
                }
                catch (Exception e){
                }
                break;

            case R.id.buttonDivide:
                try {
                    int num1 = Integer.parseInt(editTextDivide1.getText().toString());
                    int num2 = Integer.parseInt(editTextDivide2.getText().toString());
                    int res = num1 / num2;

                    SaveFile(String.valueOf(num1) + " / " + String.valueOf(num2) + " = " + String.valueOf(res));
                    textViewDivide.setText(String.valueOf(num1 / num2));
                }
                catch (Exception e){
                }
                break;
        }
    }

    // Näytetään lokiaktiviteetti
    public void showLog(View v) {
        Intent intent = new Intent(this, DisplayLogActivity.class);
        startActivity(intent);
    }

    public void emptyAll(View v){

        //Tyhjennetään kaikki editText viewit
        editTextSum1.setText("");
        editTextSum2.setText("");
        editTextDiff1.setText("");
        editTextDiff2.setText("");
        editTextMultiply1.setText("");
        editTextMultiply2.setText("");
        editTextDivide1.setText("");
        editTextDivide2.setText("");

        // tyhjennetään loki
        SaveFile("tyhj");
    }

    //Tallennetaan joka lasku tiedostoon
    public void SaveFile(String logData) {

        String trimmedText = logData.trim();
        writeText = trimmedText;
        final File logFile = new File(Environment.getExternalStorageDirectory().getPath(), LOG_FILE_NAME);

        // Jos ei ole mitään mitä laskea
        if(trimmedText.isEmpty()) {
            Toast.makeText(this, "No input", Toast.LENGTH_SHORT).show();
        } else {
            // Jos käyttäjällä on Android Marsmallow tai uudempi kysytään lupa
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    // Kysytään käyttäjältä popup ikkunalla lupa
                    requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
                }
                else {
                    // Lupa kunnossa
                    saveTxt(writeText);
                }
            }
        }
    }

    private void saveTxt(String text) {
        try {
            File path = Environment.getExternalStorageDirectory();
            String fileName = LOG_FILE_NAME;

            File file = new File(path, fileName);

            // Käytetään samaa saveTxt funktiota myös lokin tyhjentämiseen. Annetaan
            // Tyhjennä kaikki napille SaveFile("tyhj") parametri jolloin tyhjennetään
            FileWriter fWriter;
            BufferedWriter bWriter;

            if(text == "tyhj"){
                text = "";
                fWriter = new FileWriter(file.getAbsoluteFile());
                bWriter = new BufferedWriter(fWriter);
                bWriter.write(text);
                Toast.makeText(this, "Kaikki tyhjennetty", Toast.LENGTH_SHORT).show();
            }
            else{
                fWriter = new FileWriter(file.getAbsoluteFile(), true);
                bWriter = new BufferedWriter(fWriter);
                bWriter.append(text);
                bWriter.newLine();
            }

            bWriter.flush();
            bWriter.close();

            Toast.makeText(this, fileName+ " tallennettu:\n"+ path, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
