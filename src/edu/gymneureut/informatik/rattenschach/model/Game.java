package edu.gymneureut.informatik.rattenschach.model;

import edu.gymneureut.informatik.rattenschach.control.Controller;
import edu.gymneureut.informatik.rattenschach.control.Observer;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

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
    private List<Figure> livingFigures;
    private List<Figure> capturedFigures;

    public Game() {
    }

    public Game(Controller controllerWhite, Controller controllerBlack, List<Observer> observers) {
        field = new HashMap<>();
        initializeField(field);
        this.observers = observers;
        totalTime = 1000000000000l;
        white = new Player(true, controllerWhite, this, totalTime, 100000000);
        black = new Player(false, controllerWhite, this, totalTime, 100000000);
        white.setOpponent(black);
        black.setOpponent(white);
        livingFigures = new LinkedList<>();
        livingFigures.addAll(white.getFigures());
        livingFigures.addAll(black.getFigures());
        capturedFigures = new LinkedList<>();
        currentPlayer = white;
        status = GameStatus.running;
        for (Observer observer : observers) {
            observer.startGame(this);
        }
    }

    @Override
    public Game clone() {
        Game cloned = new Game();
        cloned.field = new HashMap<>();
        initializeField(cloned.field);
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

    public void setStatus(GameStatus status) {
        this.status = status;
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

    private boolean act() {
        Turn turn = currentPlayer.move(this);
        turn.execute(this);

        for (Observer observer : observers) {
            observer.nextTurn(turn);
        }

        currentPlayer = (currentPlayer == white) ? black : white;

        checkGame();
        return false;
    }

    private void checkGame() {
        List<Figure> seenFigures = new LinkedList<>();
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                Figure currentFigure = field.get(new Field(i, j));
                if (currentFigure != Figure.EMPTY && seenFigures.contains(currentFigure)) {
                    throw new IllegalStateException("Duplicate Figure"
                            + i + j
                            + currentFigure.getDetails());
                }
                seenFigures.add(currentFigure);
                if (currentFigure != Figure.EMPTY && !livingFigures.contains(currentFigure)) {
                    throw new IllegalStateException("Figure not a living figure" + i + j);
                }
            }
        }
    }

    private void initializeField(Map<Field, Figure> field) {
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

    public void captureFigure(Figure captured) {
        livingFigures.remove(captured);
        capturedFigures.add(captured);
    }


    public enum GameStatus {
        running, whiteWon, blackWon, remis, patt, remisOffered
    }
}
