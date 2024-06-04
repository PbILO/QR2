package com.example.testapp2;

import android.os.Bundle;

import android.view.View;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CreateTicketActivity extends AppCompatActivity {
    Bitmap bitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
    }
    public void onClickCreateTicket(View v) {
        FileInputStream fin = null;
        String text = "";
        try {
            fin = openFileInput("tmp.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String(bytes);
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



        EditText mEdit = (EditText)findViewById(R.id.editTextTextHumanName);
        ImageView mImg = (ImageView)findViewById(R.id.imageViewQR);
        if (TextUtils.isEmpty(mEdit.getText().toString())) {
            Toast.makeText(CreateTicketActivity.this, R.string.err2, Toast.LENGTH_SHORT).show();
        }
        else {
            /*WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;*/
            int dimen = mImg.getHeight();
            String event_id = text;
            int ticket_id = ((int) Math.random() * 10000000);
            String str = "event_" + event_id + "/ticket_" + String.valueOf(ticket_id) + "/name_" + mEdit.getText().toString();
            QRGEncoder qrgEncoder = new QRGEncoder(str, null, QRGContents.Type.TEXT, dimen);
            int white = qrgEncoder.getColorWhite();
            qrgEncoder.setColorWhite(qrgEncoder.getColorBlack());
            qrgEncoder.setColorBlack(white);
            try {
                bitmap = qrgEncoder.getBitmap();
                mImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("Tag", e.toString());
            }
            TextView txt = (TextView) findViewById(R.id.textViewName);
            txt.setText(getString(R.string.name) + ": " + mEdit.getText().toString());



            FileOutputStream fos = null;
            try {
                fos = openFileOutput("event" + text, MODE_APPEND);
                fos.write(("\n" + str).getBytes());
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
        }
    }
    public void onClickSave(View v) {
        EditText mEdit = (EditText)findViewById(R.id.editTextTextHumanName);
        QRGSaver qrgsaver = new QRGSaver();
        for (int i = 0; i < 10000000; ++i){
            try {
                FileInputStream f = new FileInputStream("/storage/emulated/0/DCIM/Camera/ticket_" + mEdit.getText().toString() + String.valueOf(i) + ".png");
            }
            catch(FileNotFoundException e){
                if (qrgsaver.save("/storage/emulated/0/DCIM/Camera/", "ticket_" + mEdit.getText().toString() + String.valueOf(i), bitmap)) {
                    Toast.makeText(CreateTicketActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateTicketActivity.this, R.string.success_no, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            catch(Exception e){

            }
        }
    }
}