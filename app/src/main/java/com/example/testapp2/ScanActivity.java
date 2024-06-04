package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileInputStream;
import java.io.IOException;

public class ScanActivity extends AppCompatActivity{
    String res = "nodata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }
    public void onClickScan(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scan a QR");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String txt = "";
        String event = "";
        if(intentResult != null){
            String contents = intentResult.getContents();
            if (contents != null) {
                FileInputStream fin = null;
                try {
                    fin = openFileInput("tmp.txt");
                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    event = new String (bytes);
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


                res = contents;
                fin = null;
                try {
                    fin = openFileInput("event" + event);
                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    txt = new String(bytes);
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
                String[] tickets = txt.split("\\n");
                for (int i = 0; i < tickets.length; i++) {
                    if (tickets[i].equals(res)) {
                        TextView t = findViewById(R.id.textView);
                        String[] text = res.split("\\/");
                        t.setText("Билет найден!\n" + "Event: " + text[0] + "\n" + "Ticket: " + text[1] + "\n" + "Name: " + text[2] + "\n");
                        break;
                    }
                    if (i == tickets.length - 1){
                        TextView t = findViewById(R.id.textView);
                        t.setText("Ticket is not found");
                    }
                }
            }
            else{
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
