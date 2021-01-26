package minesweeper.service;

import minesweeper.exception.MinesweeperException;
import minesweeper.model.Cell;
import minesweeper.model.GameBoard;
import minesweeper.service.impl.MinesweeperServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class MinesweeperServiceTest {

    @Mock
    private GameBoard gameBoard;

    @InjectMocks
    private MinesweeperServiceImpl minesweeperService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void startGameTest() {
        Cell[][] board = new Cell[2][2];
        when(gameBoard.initializeBoard()).thenReturn(board);
        minesweeperService.startGame();
        verify(gameBoard).initializeBoard();
    }

    @Test
    public void showFieldState() throws Exception {
        Cell[][] board = new Cell[10][10];
        when(gameBoard.initializeBoard()).thenReturn(board);
        minesweeperService.startGame();
        String allGood = "All good";
        when(gameBoard.showFieldState(board)).thenReturn(allGood);
        assertEquals(allGood, minesweeperService.showFieldState());
        verify(gameBoard).showFieldState(board);
    }

    @Test
    public void showFieldState_boardNotInitialized() throws Exception {
        exceptionRule.expect(MinesweeperException.class);
        exceptionRule.expectMessage("Board not initialized");
        minesweeperService.showFieldState();
    }

    @Test
    public void openCell() throws Exception {
        Cell[][] board = new Cell[10][10];
        when(gameBoard.initializeBoard()).thenReturn(board);
        minesweeperService.startGame();
        String allGood = "All good";
        when(gameBoard.openCell(board, 1)).thenReturn(allGood);
        assertEquals(allGood, minesweeperService.openCell(1));
        verify(gameBoard).openCell(board, 1);
    }

    @Test
    public void openCell_numberLessThanOne() throws Exception {
        exceptionRule.expect(MinesweeperException.class);
        exceptionRule.expectMessage("Number of cell should be between 0 and 100");
        minesweeperService.openCell(0);
    }

    @Test
    public void openCell_numberMoreThanHundred() throws Exception {
        exceptionRule.expect(MinesweeperException.class);
        exceptionRule.expectMessage("Number of cell should be between 0 and 100");
        minesweeperService.openCell(101);
    }

    @Test
    public void openCell_boardNotInitialized() throws Exception {
        exceptionRule.expect(MinesweeperException.class);
        exceptionRule.expectMessage("Board not initialized");
        minesweeperService.openCell(100);
    }
}
