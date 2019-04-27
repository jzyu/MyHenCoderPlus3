package com.github.jzyu.common.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseCircleView extends View {

    final protected RectF circleRect = new RectF();
    final protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected float radius;

    abstract protected float provideCircleBorderWidth(float shortBorderLength);

    public BaseCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    final protected float getClientWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    final protected float getClientHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    final protected float cx() {
        return getWidth() / 2f;
    }

    final protected float cy() {
        return getHeight() / 2f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float shortBorderLength = Math.min(getClientWidth(), getClientHeight());
        float strokeWidth = provideCircleBorderWidth(shortBorderLength);

        radius = Math.min(getClientWidth(), getClientHeight()) / 2f -  strokeWidth / 2f;

        circleRect.left = getClientWidth() / 2f - radius + getPaddingLeft();
        circleRect.top = getClientHeight() / 2f - radius + getPaddingTop();
        circleRect.right = circleRect.left + radius * 2f;
        circleRect.bottom = circleRect.top + radius * 2f;

        paint.setStrokeWidth(strokeWidth);
    }
}
