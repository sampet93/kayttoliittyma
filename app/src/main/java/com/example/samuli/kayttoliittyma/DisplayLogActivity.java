package com.example.samuli.kayttoliittyma;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DisplayLogActivity extends AppCompatActivity {

    TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_log);

        logText= findViewById(R.id.textViewLog);
        logText.setMovementMethod(new ScrollingMovementMethod());
        LoadFile();
    }

    public void LoadFile() {

        FileInputStream fInp = null;

        try {

            File logFile = new File(Environment.getExternalStorageDirectory(), MainActivity.LOG_FILE_NAME);
            fInp = new FileInputStream(logFile);
            InputStreamReader inpReader = new InputStreamReader(fInp);
            BufferedReader bReader = new BufferedReader(inpReader);
            StringBuilder sBuilder = new StringBuilder();
            String text = "";

            while ((text = bReader.readLine()) != null) {
                sBuilder.append(text).append("\n");
            }

            logText.setText(sBuilder.toString());

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fInp != null) {

            }
        }
    }

//    public void readFile(){
//
//        StringBuilder sb= new StringBuilder();
//
//        try {
//            File logFile = new File(Environment.getExternalStorageDirectory(), MainActivity.LOG_FILE_NAME);
//            FileInputStream fis = new FileInputStream(logFile);
//        }
//
//    }

}
