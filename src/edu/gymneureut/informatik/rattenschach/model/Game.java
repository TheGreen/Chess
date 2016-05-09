/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.gymneureut.informatik.rattenschach.model;

import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.Pawn;
import edu.gymneureut.informatik.rattenschach.model.turns.Notification;
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
    private Timer timer;
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
        timer = new Timer(1800000000000L, 15000000000L);
        white = new Player(true, controllerWhite, this);
        black = new Player(false, controllerBlack, this);
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

    GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void play() {
        timer.startGame();
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
        if (!switchPlayer(turn.getExecutor())) {
            turn = new Notification(turn.getExecutor(), Notification.Type.hasLost);
        }
        System.out.println("Before: " + getCurrentPlayer().getOpponent().isAbleToCaptureKing());
        turn.execute(this);
        System.out.println("After:  " + getCurrentPlayer().getOpponent().isAbleToCaptureKing());
        System.out.println(turn.toString());

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

    public Timer getTimer() {
        return timer;
    }

    public void captureFigure(Figure captured) {
        livingFigures.remove(captured);
        capturedFigures.add(captured);
    }

    public void promote(Pawn pawn, Figure replacement) {
        livingFigures.remove(pawn);
        livingFigures.add(replacement);
    }

    public List<Figure> getLivingFigures() {
        return livingFigures;
    }

    private boolean switchPlayer(Player player) {
        return timer.switchPlayer(player);
    }

    public List<Figure> getCapturedFigures() {
        return capturedFigures;
    }


    public enum GameStatus {
        running, whiteWon, blackWon, draw, stalemate, remisOffered
    }
}
