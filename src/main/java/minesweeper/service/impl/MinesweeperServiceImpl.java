package minesweeper.service.impl;

import minesweeper.exception.MinesweeperException;
import minesweeper.model.Cell;
import minesweeper.model.GameBoard;
import minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {

    @Autowired
    private GameBoard gameBoard;

    private Cell board[][];

    public void startGame() {
        board = gameBoard.initializeBoard();
    }

    public String showFieldState() throws MinesweeperException {
        if(board != null) {
            return gameBoard.showFieldState(board);
        } else {
            throw new MinesweeperException("Board not initialized");
        }
    }

    public String openCell(int numberOfCell) throws MinesweeperException {
        if(numberOfCell <= 0 || numberOfCell > 100) {
            throw new MinesweeperException("Number of cell should be between 1 and 100");
        }
        if(board != null) {
            return gameBoard.openCell(board, numberOfCell);
        } else {
            throw new MinesweeperException("Board not initialized");
        }
    }
}
