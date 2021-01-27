package minesweeper.model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GameBoard {
    private final int BOARD_SIZE = 10;
    private final int NUMBER_OF_MINES = 10;
    private boolean gameOver = false;
    private int openedCells = 0;

    public Cell[][] initializeBoard() {
        Cell[][] board = new Cell[BOARD_SIZE][];
        for(int i = 0; i < BOARD_SIZE; i++) {
            board[i] = new Cell[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Cell();
            }
        }
        placeMines(board);
        checkMinesAround(board);
        gameOver = false;
        openedCells = 0;

        return board;
    }

    public String showFieldState(Cell[][] board) {
        StringBuilder fieldState = new StringBuilder();
        fieldState.append("  1 2 3 4 5 6 7 8 9 10").append("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            StringBuilder row = new StringBuilder();
            row.append(i).append(" ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(board[i][j].isClosed()) {
                    row.append(". ");
                } else {
                    if(board[i][j].isMine()) {
                        row.append("x ");
                    } else {
                        row.append(board[i][j].getMinesAround()).append(" ");
                    }
                }
            }
            fieldState.append(row).append("\n");
        }
        return fieldState.toString();
    }

    public String openCell(Cell[][] board, int numberOfCell) {
        if(!gameOver) {
            int actualNumber = numberOfCell - 1;
            int row = (actualNumber - (actualNumber % 10)) / 10;
            int cellInRow = actualNumber % BOARD_SIZE;
            Cell cell = board[row][cellInRow];
            cell.setClosed(false);
            if (cell.isMine()) {
                gameOver = true;
            } else {
                openedCells++;
            }
        }
        StringBuilder result = new StringBuilder();
        result.append(showFieldState(board)).append("\n");
        if(gameOver) {
            result.append("Game over");
        }
        if(openedCells == 90) {
            result.append("You win");
        }

        return result.toString();
    }

    private void placeMines(Cell[][] board) {
        Random random = new Random();
        int placedMines = 0;
        while(placedMines < NUMBER_OF_MINES) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            board[x][y].setMine(true);
            placedMines++;
        }
    }

    private void checkMinesAround(Cell[][] board) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setMinesAround(numberOfMinesAround(i,j, board));
            }
        }
    }

    private int numberOfMinesAround(int i, int j, Cell[][] board) {
        int minesAround = 0;
        // left,right
        minesAround += mineAt(i, j-1, board);
        minesAround += mineAt(i, j+1, board);
        // up,down
        minesAround += mineAt(i-1, j, board);
        minesAround += mineAt(i+1, j, board);
        // up left,right
        minesAround += mineAt(i-1, j-1, board);
        minesAround += mineAt(i-1, j+1, board);
        // down left,right
        minesAround += mineAt(i+1, j-1, board);
        minesAround += mineAt(i+1, j+1, board);
        return minesAround;
    }

    private int mineAt(int i, int j, Cell[][] board) {
        if(notOutsideBoard(i, j) && board[i][j].isMine()) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean notOutsideBoard(int i, int j) {
        return i >= 0 && j >= 0 && i < BOARD_SIZE && j < BOARD_SIZE;
    }

}
