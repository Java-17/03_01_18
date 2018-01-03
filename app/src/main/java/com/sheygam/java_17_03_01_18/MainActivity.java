package com.sheygam.java_17_03_01_18;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {

    private MenuItem doneItem, editItem;
    private ProgressBar myProgress;
    private Handler handler;
    private TextView countTxt;
    private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(this);
        myProgress = findViewById(R.id.my_progress);
        countTxt = findViewById(R.id.count_txt);
        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        doneItem = menu.findItem(R.id.done_item);
        editItem = menu.findItem(R.id.edit_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.done_item){
            doneItem.setVisible(false);
            editItem.setVisible(true);
            new MyThread(handler).start();
        }else if(item.getItemId() == R.id.edit_item){
            doneItem.setVisible(true);
            editItem.setVisible(false);
        }else if(item.getItemId() == R.id.setting_item){
            Toast.makeText(this, "Was clicked settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                myProgress.setVisibility(View.VISIBLE);
                break;
            case 2:
                myProgress.setVisibility(View.INVISIBLE);
                break;
            case 3:
                countTxt.setText(String.valueOf(msg.arg1));
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
            new Thread(new Runnable() {
                int i;
                @Override
                public void run() {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myProgress.setVisibility(View.VISIBLE);
                        }
                    });


                    for (i = 0; i < 100; i++) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                               countTxt.setText(String.valueOf(i+1));
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
    }
}
