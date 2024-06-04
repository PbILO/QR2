package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CurrentEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event);

        FileInputStream fin = null;
        TextView textView = findViewById(R.id.TextViewEventN);
        try {
            fin = openFileInput("tmp.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(getString(R.string.event) + " " + text);
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onClickCreate(View view) {
        Intent intent = new Intent(CurrentEventActivity.this, CreateTicketActivity.class);
        startActivity(intent);
    }
    public void onClickScan(View view) {
        Intent intent = new Intent(CurrentEventActivity.this, ScanActivity.class);
        startActivity(intent);
    }
}