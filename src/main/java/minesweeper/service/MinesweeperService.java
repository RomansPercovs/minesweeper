package minesweeper.service;

public interface MinesweeperService {
    void startGame();
    String showFieldState();
    String openCell(int numberOfCell);
}
