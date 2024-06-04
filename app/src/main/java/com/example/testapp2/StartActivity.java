package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
public class StartActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void onClickCreate(View view) {
        Intent intent = new Intent(StartActivity.this, CreateActivity2.class);
        startActivity(intent);
    }
    public void onClickExisting(View view) {
        Intent intent = new Intent(StartActivity.this, MyEventsActivity.class);
        startActivity(intent);
    }
}
