package com.ljl.planegame2.models;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ljl.planegame2.R;

/**
 * Created by liujinliu on 2016/6/26.
 */
public class plane {
    public float currentX;
    public float currentY;
    Bitmap plane;
    public plane(Context context)
    {
        plane = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.plane);
    }

    public void onDraw(Canvas canvas, Paint p)
    {
        canvas.drawBitmap(plane, currentX, currentY, p);
    }

    public void set_position(float x, float y)
    {
        currentX = x;
        currentY = y;
    }
}
