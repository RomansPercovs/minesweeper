package minesweeper.controller;

import minesweeper.exception.MinesweeperException;
import minesweeper.service.MinesweeperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(MinesweeperController.class)
public class MinesweeperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MinesweeperService minesweeperService;

    @Test
    public void startGame() throws Exception {
        mockMvc.perform(post("/start"))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveState_returnOk() throws Exception {
        when(minesweeperService.showFieldState()).thenReturn("Field state");
        MvcResult result = mockMvc.perform(get("/state"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Field state", result.getResponse().getContentAsString());
    }

    @Test
    public void retrieveState_returnInternalError() throws Exception {
        when(minesweeperService.showFieldState()).thenThrow(new MinesweeperException("Board not initialized"));
        MvcResult result = mockMvc.perform(get("/state"))
                .andExpect(status().isInternalServerError())
                .andReturn();
        assertEquals("Board not initialized", result.getResponse().getContentAsString());
    }

    @Test
    public void openCell_returnOk() throws Exception {
        when(minesweeperService.openCell(1)).thenReturn("Field state + result");
        MvcResult result = mockMvc.perform(get("/open/{number}", 1))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Field state + result", result.getResponse().getContentAsString());
    }

    @Test
    public void openCell_returnInternalError() throws Exception {
        when(minesweeperService.openCell(0)).thenThrow(new MinesweeperException("Number of cell should be between 1 and 100"));
        MvcResult result = mockMvc.perform(get("/open/{number}", 0))
                .andExpect(status().isInternalServerError())
                .andReturn();
        assertEquals("Number of cell should be between 1 and 100", result.getResponse().getContentAsString());
    }
}
