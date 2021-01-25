package minesweeper.controller;

import minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MinesweeperController {

    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping("/start")
    public ResponseEntity<String> start(){
        minesweeperService.startGame();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/state")
    public ResponseEntity<String> retrieveState(){
        return ResponseEntity.ok().body(minesweeperService.showFieldState());
    }

    @GetMapping("/open/{number}")
    public ResponseEntity<String> openCell(@PathVariable("number") int numberOfCell) {
        return ResponseEntity.ok().body(minesweeperService.openCell(numberOfCell));
    }
}
