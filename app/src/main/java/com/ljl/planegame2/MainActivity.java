package com.ljl.planegame2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private PlaneView planeView;
    private FrameLayout planefield;
    private FrameLayout textfiled;
    private Button root_btn_ok;
    private Button root_btn_cancle;
    private boolean gamestart = false;
    private ArrayList potXlist = new ArrayList();
    private ArrayList potYlist = new ArrayList();
    DisplayMetrics metrics = new DisplayMetrics();
    private int ENEMY_GEN_PREIOD = 1000;
    private int CRASH_CHECK_PREIOD = 500;
    private int MSG_CODE_ENEMY_GEN = 0;
    private int MSG_CODE_CRASH_CHECK = 1;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            gameTimerMsgHandler(msg.what);
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        planefield = (FrameLayout) findViewById(R.id.root_frame1);
        textfiled = (FrameLayout) findViewById(R.id.root_frame0);
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        // 获得屏幕宽和高
        display.getMetrics(metrics);
        plane_init();
        planefield.addView(planeView);
        textfield_init();
        btn_init();
        gameLogicTimer();
    }

    public void textfield_init()
    {
        textfiled.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(planeView.crash_get())
                {
                    return true;
                }
                float x = (float)motionEvent.getX();
                float y = (float)motionEvent.getY();
                dotpoint dot = new dotpoint(view.getContext(), x, y);
                textfiled.addView(dot);
                potXlist.add(x);
                potYlist.add(y);
                return true;
            }
        });
    }

    public void btn_init()
    {
        root_btn_ok = (Button) findViewById(R.id.btn_ok);
        root_btn_cancle = (Button) findViewById(R.id.btn_cancel);
        root_btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                potXlist.clear();
                potYlist.clear();
                textfiled.removeAllViews();
                gamestart = false;
            }
        });
        root_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textfiled.removeAllViews();
                gamestart = true;
            }
        });
    }

    public void gameLogicTimer()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_CODE_ENEMY_GEN);
            }
        }, 0, ENEMY_GEN_PREIOD);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_CODE_CRASH_CHECK);
            }
        }, 0, CRASH_CHECK_PREIOD);
    }

    public void enemy_pos_change()
    {
        int max=(int) potXlist.size();
        int min=0;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        float x = (float) potXlist.get(s);
        float y = (float) potYlist.get(s);
        dotpoint dot = new dotpoint(this, x, y);
        textfiled.addView(dot);
        potXlist.remove(s);
        potYlist.remove(s);
        EnemyView eny = new EnemyView(this);
        eny.user_plane = planeView;
        eny.changePos(metrics);
        eny.addTimer();
        planefield.addView(eny);
    }

    public void crash_check()
    {
        if(planeView.crash_get())
        {
            gamestart = false;
            Toast.makeText(MainActivity.this, "YOU LOSE!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void gameTimerMsgHandler(int msg_code)
    {
        if(msg_code == MSG_CODE_ENEMY_GEN && gamestart && potXlist.size() > 0)
        {
            enemy_pos_change();
        }
        if(msg_code == MSG_CODE_CRASH_CHECK)
        {
            crash_check();
        }
    }

    public void plane_init()
    {
        planeView = new PlaneView(this);
        planeView.onInit(metrics);
    }
}
