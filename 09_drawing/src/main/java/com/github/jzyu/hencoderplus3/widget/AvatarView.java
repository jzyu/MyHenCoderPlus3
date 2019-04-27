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

public class AvatarView extends View {
    private float BORDER_WIDTH;
    private float SIZE;
    private Bitmap avatar;

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
        BitmapFactory.decodeResource(getResources(), R.mipmap.gameboy, options);
        options.inJustDecodeBounds = false;
        options.inDensity = Math.min(options.outWidth, options.outHeight);
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.gameboy, options);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        SIZE = Math.min(
                getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom());
        avatar = getAvatar((int)(SIZE - BORDER_WIDTH*2));
        BORDER_WIDTH = SIZE / 12;

        border.left = (getWidth() - getPaddingLeft() - getPaddingRight() - SIZE) / 2 + getPaddingLeft();
        border.top = (getHeight() - getPaddingTop() - getPaddingBottom() - SIZE) / 2 + getPaddingTop();
        border.right = border.left + SIZE;
        border.bottom = border.top + SIZE;

        cut.set(border.left + BORDER_WIDTH, border.top + BORDER_WIDTH,
                border.right - BORDER_WIDTH, border.bottom - BORDER_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(border, paint);

        int saveCount = canvas.saveLayer(cut, paint);
        canvas.drawOval(cut, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(avatar, cut.left, cut.top, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }
}
