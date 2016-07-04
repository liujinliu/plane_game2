package com.ljl.planegame2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.ljl.planegame2.models.plane;
/**
 * Created by liujinliu on 2016/6/26.
 */
public class PlaneView extends View{
    public plane MyPlane;
    public float Focus_distance = 2000;
    private boolean Crash_state = false;
    // 定义、并创建画笔
    Paint p = new Paint();
    public PlaneView(Context context)
    {
        super(context);
        MyPlane = new plane(context);
        setFocusable(true);
    }

    public boolean crash_get()
    {
        return Crash_state;
    }

    public void crash_set(float x, float y, float dis)
    {
        float distance = (x - MyPlane.currentX)*(x - MyPlane.currentX) +
                (y - MyPlane.currentY)*(y - MyPlane.currentY);
        if(!Crash_state) {
            Crash_state = ((dis * dis * 3) >= distance);
        }
        return;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 绘制飞机
        MyPlane.onDraw(canvas, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touch_x = event.getX();
        float touch_y = event.getY();
        float distance = (touch_x - MyPlane.currentX)*(touch_x - MyPlane.currentX) +
                (touch_y - MyPlane.currentY)*(touch_y - MyPlane.currentY);
        //System.out.println(distance);
        if (!Crash_state && distance < Focus_distance)
        {
            MyPlane.set_position(event.getX(), event.getY());
            invalidate();
        }
        return true;
    }

    public void onInit(DisplayMetrics metrics)
    {
        //this.setBackgroundResource(R.drawable.back);
        // 设置飞机的初始位置
        MyPlane.set_position(metrics.widthPixels / 2,
                metrics.heightPixels / 2);
    }

    public boolean is_crash(float x, float y)
    {
        float distance = (x - MyPlane.currentX)*(x - MyPlane.currentX) +
            (y - MyPlane.currentY)*(y - MyPlane.currentY);
        return distance < 2000;
    }
}
