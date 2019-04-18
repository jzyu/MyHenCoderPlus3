package com.github.jzyu.hencoderplus3.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
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

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(3));
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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

        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CCW);
        paint.setPathEffect(new PathDashPathEffect(dash, 50, 0, PathDashPathEffect.Style.ROTATE));
    }
}
