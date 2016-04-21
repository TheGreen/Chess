package edu.gymneureut.informatik.rattenschach.model;

import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.Pawn;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Game</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Game implements Cloneable {
    private long totalTime;
    private Player white;
    private Player black;
    private Player currentPlayer = white;
    private Map<Field, Figure> field;
    private List<Observer> observers;
    private GameStatus status = GameStatus.running;
    private List<Figure> livingFigures;
    private List<Figure> capturedFigures;

    private Game() {
    }

    public Game(Controller controllerWhite, Controller controllerBlack, List<Observer> observers) {
        field = new HashMap<>();
        initializeField(field);
        this.observers = observers;
        totalTime = 1000000000000l;
        white = new Player(true, controllerWhite, this, totalTime, 100000000);
        black = new Player(false, controllerBlack, this, totalTime, 100000000);
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
        cloned.livingFigures = cloneLivingFigures(cloned);
        cloned.capturedFigures = cloneCapturedFigures(cloned);
        return cloned;
    }

    private List<Figure> cloneLivingFigures(Game clonedGame) {
        List<Figure> clonedLivingFigures = new LinkedList<>();
        for (Figure figure : livingFigures) {
            clonedLivingFigures.add(clonedGame.getField().get(figure.getPosition()));
        }
        return clonedLivingFigures;
    }

    private List<Figure> cloneCapturedFigures(Game clonedGame) {
        List<Figure> clonedCapturedFigures = new LinkedList<>();
        for (Figure figure : clonedGame.getWhite().getCapturedFigures()) {
            clonedCapturedFigures.add(figure);
        }
        for (Figure figure : clonedGame.getBlack().getCapturedFigures()) {
            clonedCapturedFigures.add(figure);
        }
        return clonedCapturedFigures;
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
        } else if (status == GameStatus.draw) {
            white.getController().isDraw();
            black.getController().isDraw();
        } else if (status == GameStatus.stalemate) {
            white.getController().isStalemate();
            black.getController().isStalemate();
        }
    }

    private void act() {
        Turn turn = currentPlayer.move(this);
        turn.execute(this);

        for (Observer observer : observers) {
            observer.nextTurn(turn);
        }

        currentPlayer = (currentPlayer == white) ? black : white;

        checkGame();
    }

    private void checkGame() {
        List<Figure> seenFigures = new LinkedList<>();
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                Figure currentFigure = field.get(new Field(i, j));
                if (currentFigure != Figure.EMPTY && seenFigures.contains(currentFigure)) {
                    throw new IllegalStateException("Duplicate Figure"
                            + i + j
                            + currentFigure.getName());
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

    public void promote(Pawn pawn, Figure replacement) {
        livingFigures.remove(pawn);
        livingFigures.add(replacement);
    }


    public enum GameStatus {
        running, whiteWon, blackWon, draw, stalemate, remisOffered
    }
}
