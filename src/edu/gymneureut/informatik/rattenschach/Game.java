package edu.gymneureut.informatik.rattenschach;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Player white;
    private Player black;

    private Player currentPlayer = white;

    private Map<Field, Figure> field;

    int totalTime;
    private GameStatus status = GameStatus.running;

    public GameStatus getStatus() {
        return status;
    }

    public Game(Controller controllerWhite, Controller controllerBlack) {
    }

    public Game(Player white, Player black, Player currentPlayer,
                Map<Field, Figure> field, int totalTime, GameStatus status) {
        this.white = white;
        this.black = black;
        this.currentPlayer = currentPlayer;
        this.field = field;
        this.totalTime = totalTime;
        this.status = status;
    }

    private void play() {
        while (gameRunning) {
            act();
            checkGameRunning();
        }
    }

    public Game copyGame() {
        Player whiteCopy = white.copyPlayer();
        Player blackCopy = black.copyPlayer();
        whiteCopy.setOpponent(blackCopy);
        blackCopy.setOpponent(whiteCopy);
        Map<Field, Figure> fieldCopy = new HashMap<>();
        initiateField(fieldCopy);
        for (Figure figure : whiteCopy.getFigures()) {
            field.replace(figure.getPosition(), figure);
        }
        for (Figure figure : blackCopy.getFigures()) {
            field.replace(figure.getPosition(), figure);
        }
        Game gameCopy = new Game(whiteCopy, blackCopy, (currentPlayer == white) ? whiteCopy : blackCopy,
                fieldCopy, totalTime, gameRunning);
        whiteCopy.setGame(gameCopy);
        blackCopy.setGame(gameCopy);
        return gameCopy;
    }

    /**
     * Checks if the game has finished, game ends when either of the following conditionsis met:
     * -   Remis/Patt
     */
    private void checkGameRunning() {
//        if () {
//            gameRunning = false;
//        }
    }

    private boolean act() {
        Turn turn = currentPlayer.move(this);
        if (turn.getStatus() == Turn.TurnStatus.hasLost) {
            if (currentPlayer == white) {
                status = GameStatus.blackWon;
            } else {
                status = GameStatus.whiteWon;
            }
        } else if (turn.getStatus() == Turn.TurnStatus.offersRemis) {
            status = GameStatus.remisOffered;
        } else if (turn.getStatus() == Turn.TurnStatus.normallyRunning) {
            turn.getMove().execute(this);
        }
        currentPlayer = (currentPlayer == white) ? black : white;
        return false;
    }

    private void initiateField(Map<Field, Figure> field) {
        for (int i = 1; i <= 8; i += 1) {
            for (int j = 1; j <= 8; j += 1) {
                field.put(new Field(i, j), Figure.EMPTY);
            }
        }
    }

    public Map<Field, Figure> getField() {
        return field;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public enum GameStatus {
        running, whiteWon, blackWon, remis, patt, remisOffered
    }
}
