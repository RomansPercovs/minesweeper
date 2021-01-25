package minesweeper.service.impl;

import minesweeper.model.Cell;
import minesweeper.service.MinesweeperService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {

    private final int BOARD_SIZE = 10;
    private final int NUMBER_OF_MINES = 10;
    private Cell gameBoard[][];

    public void startGame() {
        initializeBoard();
        placeMines();
        checkMinesAround();
    }

    public String showFieldState() {
        StringBuilder fieldState = new StringBuilder();
        fieldState.append("  1 2 3 4 5 6 7 8 9 10").append("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            StringBuilder row = new StringBuilder();
            row.append(i).append(" ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(gameBoard[i][j].isClosed()) {
                    row.append(". ");
                } else {
                    if(gameBoard[i][j].isMine()) {
                        row.append("x ");
                    } else {
                        row.append(gameBoard[i][j].getMinesAround()).append(" ");
                    }
                }
            }
            fieldState.append(row).append("\n");
        }
        return fieldState.toString();
    }

    public String openCell(int numberOfCell) {
        int actualNumber = numberOfCell - 1;
        int row = (actualNumber - (actualNumber % 10)) / 10;
        int cellInRow = actualNumber % BOARD_SIZE;
        Cell cell = gameBoard[row][cellInRow];
        cell.setClosed(false);
        StringBuilder result = new StringBuilder();
        result.append(this.showFieldState()).append("\n");
        if(cell.isMine()) {
            result.append("Game over");
        }
        if(isAllOpened()) {
            result.append("You win");
        }
        return result.toString();
    }

    private boolean isAllOpened() {
        int openedCellsCount = 0;
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(!gameBoard[i][j].isClosed() && !gameBoard[i][j].isMine()) {
                    openedCellsCount++;
                }
            }
        }
        return openedCellsCount == 90;
    }

    private void initializeBoard() {
        gameBoard = new Cell[BOARD_SIZE][];
        for(int i = 0; i < BOARD_SIZE; i++) {
            gameBoard[i] = new Cell[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;
        while(placedMines < NUMBER_OF_MINES) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            gameBoard[x][y].setMine(true);
            placedMines++;
        }
    }

    private void checkMinesAround() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j].setMinesAround(numberOfMinesAround(i,j));
            }
        }
    }

    private int numberOfMinesAround(int i, int j) {
        int minesAround = 0;
        // left,right
        minesAround += mineAt(i, j-1);
        minesAround += mineAt(i, j+1);
        // up,down
        minesAround += mineAt(i-1, j);
        minesAround += mineAt(i+1, j);
        // up left,right
        minesAround += mineAt(i-1, j-1);
        minesAround += mineAt(i-1, j+1);
        // down left,right
        minesAround += mineAt(i+1, j-1);
        minesAround += mineAt(i+1, j+1);
        return minesAround;
    }

    private int mineAt(int i, int j) {
        if(notOutsideBoard(i, j) && gameBoard[i][j].isMine()) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean notOutsideBoard(int i, int j) {
        return i >= 0 && j >= 0 && i < BOARD_SIZE && j < BOARD_SIZE;
    }
}
