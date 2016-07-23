package com.javaclass.anima.android06progress;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button progress1,progress2;
    private ProgressDialog pDailog1,pDialog2;
    private UiHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UiHandler();
        progress1 = (Button) findViewById(R.id.progress1);
        progress2 = (Button) findViewById(R.id.progress2);

        pDailog1 = new ProgressDialog(this,ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        pDailog1.setMessage(" 執行中...");
        pDailog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDailog1.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pDailog1.dismiss();
            }
        });

        pDialog2 = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);
        pDialog2.setMessage("下載中...");
        pDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowProgress1();
            }
        });

        progress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowProgress2();
            }
        });
    }

    private void ShowProgress1(){
        new MyTask1().start();
    }

    private void ShowProgress2(){
        new MyTask2().start();
    }

    private class MyTask1 extends  Thread{

        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(1);
            try {
                Thread.sleep(5000);

            }catch (Exception e){
                e.printStackTrace();
            }
            handler.sendEmptyMessage(2);
        }
    }

    public class MyTask2 extends  Thread{
        @Override
        public void run() {
            super.run();

            handler.sendEmptyMessage(3);
            for (int i = 0; i < 100; i++) {
                try {
                    Message message = new Message();
                    message.what = 5;
                    Bundle bundle = new Bundle();
                    bundle.putInt("progress", i);
                    message.setData(bundle);
                    handler.sendMessage(message);

                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            handler.sendEmptyMessage(4);
        }
    }

    private class UiHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    pDailog1.show();
                    break;
                case 2:
                    pDailog1.dismiss();
                    break;
                case 3:
                    pDialog2.setMax(100);
                    pDialog2.show();
                    break;
                case 4:
                    if (pDialog2.isShowing()) {
                        pDialog2.dismiss();
                    }
                    break;
                case 5:
                    if (pDialog2.isShowing()) {
                        pDialog2.setProgress(msg.getData().getInt("progress"));
                    }
                    break;
            }
        }
    }

}
