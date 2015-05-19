package ru.isatimur.tisachenko.ttt.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import java.util.Random;

@Api(name = "tictactoe")
public class BoardEndpoint {
    @ApiMethod(name = "board.getmove", httpMethod = "POST")
    public Board getmove(Board board) {
        char[][] parsed = parseBoard(board.getState());
        int free = countFree(parsed);
        parsed = addMove(parsed, free);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parsed.length; i++) {
            builder.append(String.valueOf(parsed[i]));
        }
        Board updated = new Board();
        updated.setState(builder.toString());
        return updated;
    }

    private char[][] parseBoard(String state) {
        char[][] board = new char[3][3];
        char[] chars = state.toCharArray();
        if (chars.length == 9) {
            for (int i = 0; i < chars.length; i++) {
                board[i / 3][i % 3] = chars[i];
            }
        }

        return board;
    }

    private int countFree(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    count++;
                }
            }
        }
        return count;
    }

    private char[][] addMove(char[][] board, int free) {
        int index = new Random().nextInt(free) + 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '-') {
                    if (free == index) {
                        board[i][j] = 'O';
                        return board;
                    } else {
                        free--;
                    }
                }
            }
        }
        // Only occurs when empty > the number of actual empty squares.
        return board;
    }
}
