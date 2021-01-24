package minesweeper.controller;

import minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MinesweeperController {

    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping("/start")
    public void start(){
        minesweeperService.startGame();
    }

    @GetMapping("/state")
    public String retrieveState(){
        return minesweeperService.showFieldState();
    }

    @GetMapping("/open/{number}")
    public String openCell(@PathVariable("number") int numberOfCell) {
        return "Current field state + result of opened " + numberOfCell + " cell";
    }
}
