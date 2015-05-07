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


}
