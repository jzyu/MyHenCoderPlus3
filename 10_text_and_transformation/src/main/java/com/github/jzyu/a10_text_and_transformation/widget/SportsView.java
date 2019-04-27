package com.github.jzyu.a10_text_and_transformation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.jzyu.common.widget.BaseCircleView;

public class SportsView extends BaseCircleView {
    public static final String TEXT = "ABAB";
    public static final int COLOR_HIGHLIGHT = Color.parseColor("#f2357A");

    private int textOffset;
    {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "font/CourierNew.ttf"));
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected float provideCircleBorderWidth(float shortBorderLength) {
        return shortBorderLength * 0.08f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        paint.setTextSize(radius * 0.6f);

        //计算textOffset，以便y方向居中
        final Rect textBounds = new Rect();
        paint.getTextBounds(TEXT, 0, TEXT.length(), textBounds);
        textOffset = (textBounds.top + textBounds.bottom) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆环
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx(), cy(), radius, paint);

        // 绘制进度条
        paint.setColor(COLOR_HIGHLIGHT);
        canvas.drawArc(circleRect, -90f, 225f, false, paint);

        // 绘制文字
        paint.setStyle(Paint.Style.FILL); // 绘制文字必须用FILL
        canvas.drawText(TEXT, cx(), cy() - textOffset, paint);
    }
}
