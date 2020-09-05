package com.samples.flironecamera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    private Rect rect = new Rect();
    private Paint paint = new Paint();

    public MyView(Context context) {
        this(context, null, 0, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("MyView", "onDraw: ");

        canvas.drawRect(rect, paint);
    }


    public void setRect(Rect rect) {
        this.rect = rect;
        invalidate();
    }
}
