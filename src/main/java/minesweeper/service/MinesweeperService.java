package minesweeper.service;

import minesweeper.exception.MinesweeperException;

public interface MinesweeperService {
    void startGame();
    String showFieldState() throws MinesweeperException;
    String openCell(int numberOfCell) throws MinesweeperException;
}
