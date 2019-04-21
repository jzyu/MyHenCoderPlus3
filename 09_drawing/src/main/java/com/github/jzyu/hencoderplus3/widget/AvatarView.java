package com.github.jzyu.hencoderplus3.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.jzyu.hencoderplus3.R;
import com.github.jzyu.hencoderplus3.utils.Utils;

public class AvatarView extends View {
    public static final float WIDTH = Utils.dp2px(180);
    public static final float PADDING = Utils.dp2px(30);
    public static final float BORDER_WIDTH = Utils.dp2px(12);

    private Bitmap avatar = getAvatar((int)WIDTH);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private RectF cut = new RectF();
    private RectF border = new RectF();

    {
        paint.setColor(Color.CYAN);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.gameboy, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.gameboy, options);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cut.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
        border.set(PADDING - BORDER_WIDTH, PADDING - BORDER_WIDTH,
                PADDING + WIDTH + BORDER_WIDTH, PADDING + WIDTH + BORDER_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(border, paint);

        int saveCount = canvas.saveLayer(cut, paint);
        canvas.drawOval(cut, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(avatar, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }
}
