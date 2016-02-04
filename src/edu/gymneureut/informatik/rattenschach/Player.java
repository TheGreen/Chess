package edu.gymneureut.informatik.rattenschach;

import edu.gymneureut.informatik.rattenschach.figures.King;
import edu.gymneureut.informatik.rattenschach.figures.Pawn;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by green on 2/2/2016.
 */
public class Player {
    private int color;
    private Player opponent;

    Game game;

    List<Figure> figures;
    List<Figure> capturedFigures;

    Controller controller;

    long remainingTime; //nanoseconds
    long timeIncrement; //nanoseconds

    public Player(boolean isWhite, Controller controller, Game game, long timeLimit, long timeIncrement) {
        this.controller = controller;
        this.remainingTime = timeLimit;
        this.timeIncrement = timeIncrement;
        this.game = game;
        figures = new LinkedList<>();
        capturedFigures = new LinkedList<>();

        if (isWhite) {
            this.color = 1;
            for (int i = 1; i < 8; i++) {
                figures.add(new Pawn())
            }

        }
        //TODO Figuren hinzufuegen
    }

    public Player(int color, Player opponent, Game game, List<Figure> figures,
                  List<Figure> capturedFigures, Controller controller,
                  long remainingTime, long timeIncrement) {
        this.color = color;
        this.opponent = opponent;
        this.game = game;
        this.figures = figures;
        this.capturedFigures = capturedFigures;
        this.controller = controller;
        this.remainingTime = remainingTime;
        this.timeIncrement = timeIncrement;
    }

    public Player copyPlayer() {
        List<Figure> figuresCopy = new LinkedList<>();
        for (Figure figure : figures) {
            figuresCopy.add(figure.copyFigure());
        }
        List<Figure> capturedFiguresCopy = new LinkedList<>();
        for (Figure capturedFigure : capturedFigures) {
            capturedFiguresCopy.add(capturedFigure.copyFigure());
        }
        Player playerCopy = new Player(color, opponent, game, figuresCopy,
                capturedFiguresCopy, controller, remainingTime, timeIncrement);

        for (Figure figure : figuresCopy) {
            figure.setOwner(playerCopy);
        }
        for (Figure figure : capturedFiguresCopy) {
            figure.setOwner(playerCopy);
        }
        return playerCopy;
    }

    public boolean isAbleToCaptureKing() {
        List<Move> moves = new LinkedList<>();
        for (Figure figure : figures) {
            moves.addAll(figure.getPossibleMoves());
        }
        for (Move move : moves) {
            if (move.getCaptures() && move.getCapturedFigure() instanceof King) {
                return true;
            }
        }
        return false;
    }

    public Turn move(Game game) {
        if (game.getStatus() == Game.GameStatus.remisOffered) {
            List<Turn> turns = new LinkedList<>();
            turns.add(new Turn(null, Turn.TurnStatus.acceptsRemis));
            turns.add(new Turn(null, Turn.TurnStatus.deniesRemis));

            Turn turn = measureChooseTime(game.getField(), turns);
            if (remainingTime < 0) {
                return new Turn(null, Turn.TurnStatus.hasLost);
            }
            remainingTime += timeIncrement;
            return turn;
        }
        Map<Field, Figure> field = game.getField();
        List<Move> moves = new LinkedList<>();
        for (Figure figure : figures) {
            moves.addAll(figure.getPossibleMoves());
        }
        for (Move move : moves) {
            if (this == game.getWhite()) {
                if (move.testMove(game).getBlack().isAbleToCaptureKing()) {
                    moves.remove(move);
                }
            } else {
                if (move.testMove(game).getWhite().isAbleToCaptureKing()) {
                    moves.remove(move);
                }
            }
        }
        if (moves.size() == 0) {
            if (opponent.isAbleToCaptureKing()) {
                return new Turn(null, Turn.TurnStatus.hasLost);
            } else {
                return new Turn(null, Turn.TurnStatus.isPatt);
            }
        }
        List<Turn> turns = new LinkedList<>();
        for (Move move : moves) {
            turns.add(new Turn(move, Turn.TurnStatus.normallyRunning));
        }
        turns.add(new Turn(null, Turn.TurnStatus.offersRemis));
        Turn turn = measureChooseTime(field, turns);
        if (remainingTime < 0) {
            return new Turn(null, Turn.TurnStatus.hasLost);
        }
        remainingTime += timeIncrement;
        return turn;
    }

    private Turn measureChooseTime(Map<Field, Figure> field, List<Turn> turns) {
        long time = System.nanoTime();
        Turn turn = controller.pickMove(field, turns);
        remainingTime -= System.nanoTime() - time;
        return turn;
    }

    public void captureFigure(Figure captured) {
        figures.remove(captured);
        capturedFigures.add(captured);
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
