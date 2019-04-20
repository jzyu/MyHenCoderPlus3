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

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    Path path = new Path();
    PathMeasure pathMeasure;
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

        path.addArc(getWidth() / 2 - RADIUS, getWidth() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getWidth() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE);
        pathMeasure = new PathMeasure(path, false);
        pathEffect = new PathDashPathEffect(dash,
                (pathMeasure.getLength() - DASH_THICKNESS) / 20,
                0,
                PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画原图形
        canvas.drawArc(getWidth() / 2 - RADIUS,
                getWidth() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getWidth() / 2 + RADIUS,
                90 + ANGLE / 2,
                360 - ANGLE,
                false,
                paint);

        // 画刻度
        paint.setPathEffect(pathEffect);
        canvas.drawArc(getWidth() / 2 - RADIUS,
                getWidth() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getWidth() / 2 + RADIUS,
                90 + ANGLE / 2,
                360 - ANGLE,
                false,
                paint);
        paint.setPathEffect(null);
    }
}
