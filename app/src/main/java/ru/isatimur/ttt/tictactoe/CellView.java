package ru.isatimur.ttt.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.jar.Attributes;

/**
 * Created by souldragon on 10.04.15.
 */
public class CellView extends ImageView implements View.OnTouchListener {

    private CellState state;
    private Context mContext;
    private static boolean isFP =true;

    public void setState(CellState state) {
        if(state == CellState.X){
            setImageResource(mXResource);
        } else if(state == CellState.O){
            setImageResource(mOResource);
        } else if(state == CellState.MNULL) {
            setImageResource(mNULLResource);
        }
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public enum CellState {
        X, O, MNULL
    }

    private int mXResource = R.drawable.x;
    private int mOResource = R.drawable.o;
    private int mNULLResource = R.drawable.mnull;
    private OnTouchListener touchListenDelegate = null;


    private CellState mState = CellState.MNULL;

    public CellView(Context context,AttributeSet attributes) {
        super(context, attributes);
        this.mContext = context.getApplicationContext();
        TypedArray typedArray = context.obtainStyledAttributes(attributes,R.styleable.CellView,0,0);
        mXResource = typedArray.getResourceId(R.styleable.CellView_xImage,R.drawable.x);
        mOResource = typedArray.getResourceId(R.styleable.CellView_oImage,R.drawable.o);
        mNULLResource = typedArray.getResourceId(R.styleable.CellView_mNullImage,R.drawable.mnull);

        init();
    }

    private void init(){
        setState(CellState.MNULL);
        this.setFocusable(true);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mState == CellState.MNULL) {
            if (getTouchListenDelegate() != null){
                touchListenDelegate.onTouch(v, event);

            }
        }
        return false;
    }

    private OnTouchListener getTouchListenDelegate() {
        return touchListenDelegate;
    }


    public void setTouchListenDelegate(OnTouchListener l) {
        this.touchListenDelegate = l;
    }

    @Override
    public String toString() {
        return state.name();
    }
}
