package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Arrays;

public class MyEventsActivity extends AppCompatActivity {
    String item = "";
    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        FileInputStream fin = null;
        try {
            fin = openFileInput("events");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String(bytes);
        } catch (IOException ex) {
            Toast.makeText(this, R.string.no_existing_events, Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        String[] names = text.split("\\n");
        ArrayList<String> nameList = new ArrayList<String>(Arrays.asList(names));
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                item = (String)parent.getItemAtPosition(0);
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public void onClickSelect(View v) {
        if(!text.equals("")) {
            Intent intent = new Intent(MyEventsActivity.this, CurrentEventActivity.class);
            FileOutputStream fos = null;
            try {
                fos = openFileOutput("tmp.txt", MODE_PRIVATE);
                fos.write(item.getBytes());
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.no_existing_events, Toast.LENGTH_SHORT).show();
        }
    }
}