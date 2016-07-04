package com.ljl.planegame2;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
/**
 * Created by liujinliu on 2016/7/2.
 */
public class dotpoint extends View{

    private float currentX = 0;
    private float currentY = 0;
    private final float radius = 2;
    // 定义、并创建画笔
    Paint p = new Paint();
    public dotpoint(Context context)
    {
        super(context);
    }
    public dotpoint(Context context, float x, float y)
    {
        super(context);
        currentX = x;
        currentY = y;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 设置画笔的颜色
        p.setColor(Color.RED);
        // 绘制一个小圆（作为小球）
        canvas.drawCircle(currentX, currentY, radius, p);
    }
}
