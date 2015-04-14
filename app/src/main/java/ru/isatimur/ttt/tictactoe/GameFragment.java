package ru.isatimur.ttt.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A placeholder fragment containing a simple view.
 */
public class GameFragment extends Fragment {

    private static final int length = 9;
    private CellView[] matrix = new CellView[length];
    private static final int N = 3;
    private static final String WINNER_X = "XXX";
    private static final String WINNER_O = "OOO";
    private String winner;
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
                //return true;
            } else if (isBoardFull()) {
                String text = "Game is over with draw!";
                gameIsOver(text);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().supportInvalidateOptionsMenu();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = (LinearLayout) inflater.inflate(R.layout.fragment_game, container, false);
        matrix = new CellView[length];
        init(v);
        newGame();
        return v;
    }

    private void init(View v) {
        for (int i = 0; i < length; i++) {
            int id = getResources().getIdentifier("ttt_cell" + i, "id", getActivity().getPackageName());
            matrix[i] = (CellView) v.findViewById(id);
            matrix[i].setOnClickListener(clickOnCell);
        }
    }

    private void newGame() {
        for (int i = 0; i < length; i++) {
            matrix[i].setState(CellView.CellState.MNULL);
        }
        isFP = true;
        winner = "Player";
    }

    private void gameIsOver(String result) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        final EndGameFragment endGameFragment = new EndGameFragment(result);
        endGameFragment.setListener(new EndGameFragment.EndGameListener() {

            @Override
            public void onNewGame() {
                newGame();
                endGameFragment.dismiss();
            }

            @Override
            public void onExitGame() {
                endGameFragment.dismiss();

                getActivity().finish();

            }
        });
        endGameFragment.show(fm, "frame_edit_text");
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
            if (winnerChecker(resultTxt1.toString(), resultTxt2.toString())) {
                return true;
            }
        }

        return false;
    }

    private boolean winnerChecker(String result1, String result2) {
        if (WINNER_X.equals(result1) || WINNER_X.equals(result2.toString())) {
            winner = winner + " X";
            return true;
        } else if (WINNER_O.equals(result1.toString()) || WINNER_O.equals(result2.toString())) {
            winner = winner + " O";
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
        return winnerChecker(resultTxt1.toString(), resultTxt2.toString());
    }
}
