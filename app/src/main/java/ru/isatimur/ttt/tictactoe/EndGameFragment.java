package ru.isatimur.ttt.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by souldragon on 13.04.15.
 */
public class EndGameFragment extends DialogFragment {

    private TextView mResultText;
    private Button mNewGame;
    private Button mExitGame;
    private EndGameListener mListener;
    private String mText;

    public EndGameFragment() {
    }

    @SuppressLint("ValidFragment")
    public EndGameFragment(String text) {
        this.mText = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_endgame, container,
                false);
        getDialog().setTitle("WHO is the winner?");
        mResultText = (TextView) rootView.findViewById(R.id.textResult);
        mResultText.setText(mText);
        mNewGame = (Button) rootView.findViewById(R.id.newGame);
        mExitGame = (Button) rootView.findViewById(R.id.exitGame);
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onNewGame();
                }

            }
        });

        mExitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onExitGame();
                }
            }
        });

        return rootView;

    }

    public EndGameListener getListener() {
        return mListener;
    }

    public void setListener(EndGameListener mListener) {
        this.mListener = mListener;
    }

    public interface EndGameListener {
        void onNewGame();

        void onExitGame();
    }
}
