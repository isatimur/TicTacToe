package ru.isatimur.ttt.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class TicTacToeActivity extends AppCompatActivity {

    private static final int length = 9;
    private static final int N = 3;
    private static final String WINNER_X = "XXX";
    private static final String WINNER_O = "OOO";
    View viewContainer;
    private CellView[] matrix = new CellView[length];
    private String winner;
    private CrossLineView mCrossLineView;
    private boolean isFP = true;
    private final View.OnClickListener clickOnCell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CellView cellView = (CellView) v;
            if (cellView.getState() == CellView.CellState.MNULL) {
                if (isFP) {
                    cellView.setState(CellView.CellState.X);
                } else {
                    cellView.setState(CellView.CellState.O);
                }
                v.setEnabled(false);
                isFP = !isFP;
            }
            if (hasWin()) {
                String text = "The winner is " + winner;
                gameIsOver(text);
            } else if (isBoardFull()) {
                String text = "Game is over with draw!";
                gameIsOver(text);
            }
        }
    };
    private int[] lineMatrix = new int[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        getWindow().clearFlags(Window.FEATURE_ACTION_BAR);
//        FragmentTransaction ft = this
//                .getSupportFragmentManager().beginTransaction();
//        ft.replace(android.R.id.content, new GameFragment()).commit();


        viewContainer = (RelativeLayout) findViewById(R.id.container);
        matrix = new CellView[length];
        init(viewContainer);
        newGame();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, SettingsActivity.class);
            startActivity(intent);
            return false;
        }


        return super.onOptionsItemSelected(item);
    }


    private void init(View v) {
        for (int i = 0; i < length; i++) {
            int id = getResources().getIdentifier("ttt_cell" + i, "id", this.getPackageName());
            matrix[i] = (CellView) v.findViewById(id);
            matrix[i].setOnClickListener(clickOnCell);
            if (i < 8) {
                lineMatrix[i] = i;
            }
        }
        mCrossLineView = (CrossLineView) v.findViewById(R.id.cross_line);
        mCrossLineView.setWidthView(matrix[0].getMeasuredWidth() * 1f);
        mCrossLineView.setVisibility(View.GONE);
    }

    private void newGame() {
        for (int i = 0; i < length; i++) {
            matrix[i].setState(CellView.CellState.MNULL);
            matrix[i].setEnabled(true);
        }
        isFP = true;
        winner = "Player";
        mCrossLineView.setVisibility(View.GONE);
    }

    private void gameIsOver(String result) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i].setEnabled(false);
        }
        new AlertDialog.Builder(this).setInverseBackgroundForced(true)
                .setTitle(R.string.game_over)
                .setMessage((winner.contains("X") ? R.string.win_x : ((!winner.contains("O")) ? R.string.win_draw : R.string.win_o)))
                .setIcon((winner.contains("X") ? R.drawable.x_white : ((!winner.contains("O")) ? R.drawable.mnull_white : R.drawable.o_white)))
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        newGame();
                    }
                })
                .setNegativeButton(R.string.exit_game, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }).show();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < length; i++) {
            if ("MNULL".equals(matrix[i].getState().name())) {
                return false;
            }
        }
        return true;
    }

    private boolean hasWin() {
        if (checkDiagonalsForWin()) {
            return true;
        } else if (checkRowsForWin()) {
            return true;
        }
        return false;
    }

    private boolean checkRowsForWin() {
        CellView[] result1 = new CellView[N];
        CellView[] result2 = new CellView[N];
        for (int i = 0; i < N; i++) {
            StringBuilder resultTxt1 = new StringBuilder();
            StringBuilder resultTxt2 = new StringBuilder();
            for (int j = 0; j < N; j++) {
                int rowId1 = i * N + j;
                int rowId2 = i + j * N;
                result1[j] = matrix[rowId1];
                result2[j] = matrix[rowId2];
                resultTxt1.append(result1[j].getState().name());
                resultTxt2.append(result2[j].getState().name());
            }
            if (winnerChecker(resultTxt1.toString(), resultTxt2.toString(), i)) {
                return true;
            }
        }
        return false;
    }

    private boolean winnerChecker(String result1, String result2, int lineNumber) {
        if (WINNER_X.equals(result1) || WINNER_O.equals(result1)) {
            if (lineNumber == -1) {
                mCrossLineView.saveLinePoints(0, 8);
            } else if (lineNumber == 0) {
                mCrossLineView.saveLinePoints(0, 2);
            } else if (lineNumber == 1) {
                mCrossLineView.saveLinePoints(3, 5);
            } else if (lineNumber == 2) {
                mCrossLineView.saveLinePoints(6, 8);
            }
        } else if (WINNER_X.equals(result2) || WINNER_O.equals(result2)) {
            if (lineNumber == -1) {
                mCrossLineView.saveLinePoints(2, 6);
            } else if (lineNumber == 0) {
                mCrossLineView.saveLinePoints(0, 6);
            } else if (lineNumber == 1) {
                mCrossLineView.saveLinePoints(1, 7);
            } else if (lineNumber == 2) {
                mCrossLineView.saveLinePoints(2, 8);
            }
        }

        if (WINNER_X.equals(result1) || WINNER_X.equals(result2)) {
            winner = "X";
            mCrossLineView.setVisibility(View.VISIBLE);
            mCrossLineView.invalidate();
            return true;
        } else if (WINNER_O.equals(result1) || WINNER_O.equals(result2)) {
            winner = "O";
            mCrossLineView.setVisibility(View.VISIBLE);
            mCrossLineView.invalidate();
            return true;
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        CellView[] result1 = new CellView[N];
        CellView[] result2 = new CellView[N];
        StringBuilder resultTxt1 = new StringBuilder();
        StringBuilder resultTxt2 = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int elDiag1 = i * (N + 1);
            int elDiag2 = (i + 1) * (N - 1);
            result1[i] = matrix[elDiag1];
            result2[i] = matrix[elDiag2];
            resultTxt1.append(result1[i].getState().name());
            resultTxt2.append(result2[i].getState().name());
        }
        return winnerChecker(resultTxt1.toString(), resultTxt2.toString(), -1);
    }
}
