package minesweeper.controller;

import minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class MinesweeperController {

    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping("/start")
    public ResponseEntity<String> start(){
        minesweeperService.startGame();
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/state")
    public ResponseEntity<String> retrieveState(){
        try {
            return ResponseEntity.ok().body(minesweeperService.showFieldState());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/open/{number}")
    public ResponseEntity<String> openCell(@PathVariable("number") int numberOfCell) {
        try {
            return ResponseEntity.ok().body(minesweeperService.openCell(numberOfCell));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
