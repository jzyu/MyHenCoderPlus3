package com.github.jzyu.hencoderplus3.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.jzyu.hencoderplus3.utils.Utils;

public class PieChart extends View {
    public static final float RADIUS = Utils.dp2px(150);
    private final RectF bounds = new RectF();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int[] COLORS = {Color.DKGRAY, Color.BLUE, Color.RED, Color.YELLOW};
    private final int[] ANGLES = {50, 100, 80, 130};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // set arc bounds
        bounds.set(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS,
                getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngle = 0;
        for (int i = 0; i < COLORS.length; i++) {
            paint.setColor(COLORS[i]);
            canvas.drawArc(bounds, currentAngle, ANGLES[i], true, paint);
            currentAngle += ANGLES[i];
        }
    }
}
