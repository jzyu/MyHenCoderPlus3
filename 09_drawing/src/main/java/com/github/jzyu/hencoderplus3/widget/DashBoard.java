package com.github.jzyu.hencoderplus3.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.jzyu.hencoderplus3.utils.Utils;

/**
 * Date  : 2019/4/10.
 */
public class DashBoard extends View {
    public static final float RADIUS = Utils.dp2px(150);
    public static final float ANGLE = 120;
    public static final float DASH_THICKNESS = Utils.dp2px(2);
    public static final float DASH_LENGTH = Utils.dp2px(10);
    public static final float MARK_LENGTH = Utils.dp2px(100);
    public static final float START_ANGLE = 90 + ANGLE / 2;
    public static final float SWEEP_ANGLE = 360 - ANGLE;
    public static final RectF arcRect = new RectF();

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    Path path = new Path();
    PathEffect pathEffect;

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(3));
        dash.addRect(0, 0, DASH_THICKNESS, DASH_LENGTH, Path.Direction.CCW);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        calcArcRect();

        path.addArc(arcRect, START_ANGLE, SWEEP_ANGLE);
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
        drawMark(canvas, 5);
        drawMark(canvas, 0);
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

    private void calcArcRect() {
        arcRect.left = getWidth() / 2f - RADIUS;
        arcRect.right = getWidth() / 2f + RADIUS;
        arcRect.top = getHeight() / 2f - RADIUS;
        arcRect.bottom = getHeight() / 2f + RADIUS;
    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(arcRect, START_ANGLE, SWEEP_ANGLE, false, paint);
    }
}
