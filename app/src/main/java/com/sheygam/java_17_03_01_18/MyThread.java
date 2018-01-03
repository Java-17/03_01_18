package com.sheygam.java_17_03_01_18;

import android.os.Handler;
import android.os.Message;

/**
 * Created by gregorysheygam on 03/01/2018.
 */

public class MyThread extends Thread {
    private Handler handler;

    public MyThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            handler.sendEmptyMessage(1);

            for (int i = 0; i < 100; i++) {
                Message msg = handler.obtainMessage(3,i+1,-1);
                handler.sendMessage(msg);
                sleep(50);
            }
            handler.sendEmptyMessage(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
