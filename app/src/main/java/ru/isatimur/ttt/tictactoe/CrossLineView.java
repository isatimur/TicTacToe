package ru.isatimur.ttt.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by souldragon on 10.04.15.
 */
public class CrossLineView extends View {
    private int startPoint;
    private int endPoint;
    private Context mContext;
    private float widthView = 0.0f;
    private int[][] matrix = new int[3][3];

    public CrossLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context.getApplicationContext();
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = k;
                Log.i("ttt", "-- " + matrix[i][j] + " --");
                k++;
            }
        }

    }

    public void saveLinePoints(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(0, 188, 212));
        paint.setStrokeWidth(25f);
        float startPointI = 0f;
        float startPointJ = 0f;
        float endPointI = 0f;
        float endPointJ = 0f;
        widthView = 100f;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == startPoint) {
                    startPointI = (widthView * j + widthView / 2) * 3;
                    startPointJ = (widthView * i + widthView / 2) * 3;
                } else if (matrix[i][j] == endPoint) {
                    endPointI = (widthView * j + widthView / 2) * 3;
                    endPointJ = (widthView * i + widthView / 2) * 3;
                }
            }
        }
        canvas.drawLine(startPointI, startPointJ, endPointI, endPointJ, paint);
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }
}
