package com.github.jzyu.a10_text_and_transformation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SportsView extends View {
    public static final String TEXT = "ABAB";

    final protected RectF circleRect = new RectF();
    public static final int COLOR_HIGHLIGHT = Color.parseColor("#f2357A");
    protected float radius;
    protected float strokeWidth;
    int textOffset;
    final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "font/CourierNew.ttf"));
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private float getClientWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private float getClientHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        strokeWidth = Math.min(getClientWidth(), getClientHeight()) * 0.1f;
        radius = Math.min(getClientWidth(), getClientHeight()) / 2f -  strokeWidth / 2f;

        circleRect.left = getClientWidth() / 2f - radius + getPaddingLeft();
        circleRect.top = getClientHeight() / 2f - radius + getPaddingTop();
        circleRect.right = circleRect.left + radius * 2f;
        circleRect.bottom = circleRect.top + radius * 2f;

        paint.setStrokeWidth(strokeWidth);
        paint.setTextSize(radius * 0.6f);

        //计算textOffset，以便y方向居中
        final Rect textBounds = new Rect();
        paint.getTextBounds(TEXT, 0, TEXT.length(), textBounds);
        textOffset = (textBounds.top + textBounds.bottom) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawLine(0,0, 600, 0, paint);

        // 绘制圆环
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, paint);

        // 绘制进度条
        paint.setColor(COLOR_HIGHLIGHT);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(circleRect, -90f, 225f, false, paint);
        paint.setStrokeCap(Paint.Cap.BUTT);

        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(TEXT, getWidth() / 2f, getHeight() / 2f - textOffset, paint);
    }
}
