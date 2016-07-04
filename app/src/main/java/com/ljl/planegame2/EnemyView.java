package com.ljl.planegame2;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.DisplayMetrics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by liujinliu on 2016/6/26.
 */
public class EnemyView extends View{

    private float currentX = 20;
    private float currentY = 30;
    private float maxY = 100;
    private final float radius = 10;
    private final float step = 5;
    private int MSG_CODE_ENEMY_GODOWN = 0;
    public PlaneView user_plane;
    // 定义、并创建画笔
    Paint p = new Paint();
    final String[] colors = new String[] {"#6699ff", "#99cc33", "#FFCC00",
                               "#003399", "#00CC00", "#FF6600",
                               "#CC0000"};
    private int color_index = 0;
    public EnemyView(Context context)
    {
        super(context);
        int color_num = colors.length;
        color_index = _get_random(color_num, 0);
        if (color_index >= color_num)
        {
            color_index = 0;
        }
    }

    private int _get_random(int max, int min)
    {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 设置画笔的颜色
        p.setColor(Color.parseColor(colors[color_index]));
        // 绘制一个小圆（作为小球）
        canvas.drawCircle(currentX, currentY, radius, p);
    }

    public boolean changePos(DisplayMetrics metrics)
    {
        int maxX=(int) metrics.widthPixels;
        maxY=(int) metrics.heightPixels;
        currentX = (float) _get_random(maxX, 0);
        invalidate();
        return true;
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            enemyMsgHandler(msg.what);
            super.handleMessage(msg);
        }
    };

    public void enemyMsgHandler(int msg_code)
    {
        if(msg_code == MSG_CODE_ENEMY_GODOWN && (currentY+radius) < maxY)
        {
            if (currentY + step + radius >= maxY)
            {
                setVisibility(View.GONE);
                return;
            }
            currentY += step;
            user_plane.crash_set(currentX, currentY, radius);
            invalidate();
        }
    }

    public void addTimer()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!user_plane.crash_get()) {
                    handler.sendEmptyMessage(MSG_CODE_ENEMY_GODOWN);
                }
                return;
            }
        }, 0, 100);
    }
}
