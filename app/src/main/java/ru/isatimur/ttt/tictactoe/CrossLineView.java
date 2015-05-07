package ru.isatimur.ttt.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by souldragon on 10.04.15.
 */
public class CrossLineView extends View {
    private RectF mShadowBounds = new RectF();

    private float mPointerRadius = 2.0f;


    public CrossLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the shadow
        // Draw the label text
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(25f);
        // Draw the pointer
        canvas.drawLine(10f, 10f, 900f, 900f, paint);

    }
}
