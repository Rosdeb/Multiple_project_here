package com.example.daraz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FliesSystem extends AppCompatActivity {

    TextInputEditText inputEditText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flies_system);



        inputEditText=findViewById(R.id.input_data);
        textView=findViewById(R.id.textview_input);

        String path=getFilesDir().getAbsolutePath();
        textView.setText(path);


    }
    public void create(View view){
        String data = inputEditText.getText().toString();
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = openFileOutput("rosdeb",MODE_APPEND);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            textView.setText("file written");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (fileOutputStream!=null){

                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}