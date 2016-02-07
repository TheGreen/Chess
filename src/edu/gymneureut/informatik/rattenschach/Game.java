package edu.gymneureut.informatik.rattenschach;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game implements Cloneable {
    long totalTime;
    private Player white;
    private Player black;
    private Player currentPlayer = white;
    private Map<Field, Figure> field;
    private List<Observer> observers;
    private GameStatus status = GameStatus.running;

    public Game() {
    }

    public Game(Controller controllerWhite, Controller controllerBlack, List<Observer> observers) {
        field = new HashMap<>();
        initiateField(field);
        this.observers = observers;
        totalTime = 1000000000000l;
        white = new Player(true, controllerWhite, this, totalTime, 100000000);
        black = new Player(false, controllerWhite, this, totalTime, 100000000);
        white.setOpponent(black);
        black.setOpponent(white);
        currentPlayer = white;
        status = GameStatus.running;
        for (Observer observer : observers) {
            observer.startGame(this);
        }
    }

    public Game(Player white, Player black, Player currentPlayer,
                Map<Field, Figure> field, long totalTime, GameStatus status) {
        this.white = white;
        this.black = black;
        this.currentPlayer = currentPlayer;
        this.field = field;
        this.totalTime = totalTime;
        this.status = status;
    }

    @Override
    public Game clone() {
        Game cloned = new Game();
        cloned.field = new HashMap<>();
        initiateField(cloned.field);
        cloned.white = white.clone();
        cloned.black = black.clone();
        cloned.white.setGameClone(cloned);
        cloned.black.setGameClone(cloned);
        cloned.currentPlayer = (currentPlayer == white) ? cloned.white : cloned.black;
        cloned.observers = new LinkedList<>();
        cloned.status = status;
        return cloned;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void play() {
        while (status == GameStatus.running
                || status == GameStatus.remisOffered) {
            act();
        }

        if (status == GameStatus.whiteWon) {
            white.getController().hasWon();
            black.getController().hasLost();
        } else if (status == GameStatus.blackWon) {
            black.getController().hasWon();
            white.getController().hasLost();
        } else if (status == GameStatus.remis || status == GameStatus.patt) {
            white.getController().isPatt();
            black.getController().isPatt();
        }
    }

//    public Game copyGame() {
//        Player whiteCopy = white.copyPlayer();
//        Player blackCopy = black.copyPlayer();
//        whiteCopy.setOpponent(blackCopy);
//        blackCopy.setOpponent(whiteCopy);
//        Map<Field, Figure> fieldCopy = new HashMap<>();
//        initiateField(fieldCopy);
//        for (Figure figure : whiteCopy.getFigures()) {
//            field.replace(figure.getPosition(), figure);
//        }
//        for (Figure figure : blackCopy.getFigures()) {
//            field.replace(figure.getPosition(), figure);
//        }
//        Game gameCopy = new Game(whiteCopy, blackCopy, (currentPlayer == white) ? whiteCopy : blackCopy,
//                fieldCopy, totalTime, status);
//        whiteCopy.setGame(gameCopy);
//        blackCopy.setGame(gameCopy);
//        return gameCopy;
//    }

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
        } else if (turn.getStatus() == Turn.TurnStatus.acceptsRemis) {
            status = GameStatus.patt;
        } else if (turn.getStatus() == Turn.TurnStatus.deniesRemis) {
            status = GameStatus.running;
        } else if (turn.getStatus() == Turn.TurnStatus.normallyRunning) {
            turn.getMove().execute(this);
        }

        for (Observer observer : observers) {
            observer.nextTurn(turn);
        }

        currentPlayer = (currentPlayer == white) ? black : white;

        checkGame();
        return false;
    }

    private void checkGame() {
        int figureCounter = 0;
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                if (field.get(new Field(i, j)) != Figure.EMPTY) {
                    figureCounter += 1;
                }
            }
        }
        if (figureCounter > 32) {
            throw new IllegalStateException("Too many figures on Field: " + figureCounter);
        }
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public enum GameStatus {
        running, whiteWon, blackWon, remis, patt, remisOffered
    }
}
