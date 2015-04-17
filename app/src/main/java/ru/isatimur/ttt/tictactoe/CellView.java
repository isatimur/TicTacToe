package ru.isatimur.ttt.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by souldragon on 10.04.15.
 */
public class CellView extends ImageView {

    private static boolean isFP = true;
    private CellState state;
    private Context mContext;
    private int mXResource = R.drawable.x;
    private int mOResource = R.drawable.o;
    private int mNULLResource = R.drawable.mnull;
    private OnTouchListener touchListenDelegate = null;
    private CellState mState = CellState.MNULL;
    public CellView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.mContext = context.getApplicationContext();
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CellView, 0, 0);
        mXResource = typedArray.getResourceId(R.styleable.CellView_xImage, R.drawable.x_white);
        mOResource = typedArray.getResourceId(R.styleable.CellView_oImage, R.drawable.o_white);
        mNULLResource = typedArray.getResourceId(R.styleable.CellView_mNullImage, R.drawable.mnull_white);
         init();
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        if (state == CellState.X) {
            setImageResource(mXResource);
        } else if (state == CellState.O) {
            setImageResource(mOResource);
        } else if (state == CellState.MNULL) {
            setImageResource(mNULLResource);
            setEnabled(true);
        }
        this.state = state;
    }

    private void init() {
        setState(CellState.MNULL);
        this.setFocusable(true);
    }

    @Override
    public String toString() {
        return state.name();
    }

    public enum CellState {
        X, O, MNULL
    }
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();


        Bitmap roundBitmap =  getCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0,0, null);

    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
                sbmp.getWidth() / 2+0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);


        return output;
    }
}
