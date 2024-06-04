package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);
    }
    public void onClickCurrent(View view) {
        Intent intent = new Intent(CreateActivity2.this, CurrentEventActivity.class);
        EditText mEdit1 = (EditText)findViewById(R.id.editTextTextEventName);
        EditText mEdit2 = (EditText)findViewById(R.id.editTextDate);
        if ((!mEdit1.getText().toString().equals("")) && (!mEdit2.getText().toString().equals(""))) {
            boolean t = false;
            FileOutputStream fos = null;
            try {
                File file = new File("event" + mEdit1.getText().toString());
                if (!file.exists()) {
                    fos = openFileOutput("event" + mEdit1.getText().toString(), MODE_PRIVATE);
                    fos.write(mEdit2.getText().toString().getBytes());
                    Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
                    t = true;
                }
                else{
                    Toast.makeText(this, R.string.event_already_exists, Toast.LENGTH_SHORT).show();
                }
            }
            catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    if(fos!=null)
                        fos.close();
                }
                catch(IOException ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            try {
                fos = openFileOutput("tmp.txt", MODE_PRIVATE);
                fos.write(mEdit1.getText().toString().getBytes());
            }
            catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if(t){
                startActivity(intent);
            }


            try {
                fos = openFileOutput("events", MODE_APPEND);
                fos.write((mEdit1.getText().toString() + "\n").getBytes());
            }
            catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    if(fos!=null)
                        fos.close();
                }
                catch(IOException ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast toast = Toast.makeText(this, R.string.err1, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}