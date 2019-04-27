package com.github.jzyu.hencoderplus3.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.jzyu.common.utils.Utils;
import com.github.jzyu.common.widget.BaseCircleView;

/**
 * Date  : 2019/4/10.
 */
public class DashBoard extends BaseCircleView {
    private float MARK_LENGTH;

    public static final float ANGLE = 120;
    public static final float DASH_THICKNESS = Utils.dp2px(2);
    public static final float DASH_LENGTH = Utils.dp2px(10);
    public static final float START_ANGLE = 90 + ANGLE / 2;
    public static final float SWEEP_ANGLE = 360 - ANGLE;

    Path dash = new Path();
    Path path = new Path();
    PathEffect pathEffect;
    {
        paint.setStyle(Paint.Style.STROKE);
        dash.addRect(0, 0, DASH_THICKNESS, DASH_LENGTH, Path.Direction.CCW);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected float provideCircleBorderWidth(float shortBorderLength) {
        return shortBorderLength * 0.03f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        MARK_LENGTH = radius * 0.7f;

        path.addArc(circleRect, START_ANGLE, SWEEP_ANGLE);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathEffect = new PathDashPathEffect(dash,
                (pathMeasure.getLength() - DASH_THICKNESS) / 20,
                0,
                PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画圆弧
        drawArc(canvas);

        // 画刻度
        paint.setPathEffect(pathEffect);
        drawArc(canvas);
        paint.setPathEffect(null);

        // 画指针
        drawMark(canvas, 6);
        drawMark(canvas, 2);
    }

    private void drawMark(Canvas canvas, int mark) {
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f,
                getWidth() / 2f + (float) Math.cos(Math.toRadians(getAngleForMark(mark))) * MARK_LENGTH,
                getHeight() / 2f + (float) Math.sin(Math.toRadians(getAngleForMark(mark))) * MARK_LENGTH,
                paint);
    }

    private float getAngleForMark(int mark) {
        return START_ANGLE + (SWEEP_ANGLE) / 20 * mark;
    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(circleRect, START_ANGLE, SWEEP_ANGLE, false, paint);
    }
}
