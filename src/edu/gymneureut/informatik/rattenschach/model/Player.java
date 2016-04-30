package edu.gymneureut.informatik.rattenschach.model;

import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.model.figures.*;
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Player</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Player implements Cloneable {
    private Game game;
    private List<Figure> figures;
    private List<Figure> capturedFigures;
    private Controller controller;
    private int color;
    private Player opponent;

    private Player() {

    }

    public Player(boolean isWhite, Controller controller, Game game) {
        this.controller = controller;
        this.game = game;
        figures = new LinkedList<>();
        capturedFigures = new LinkedList<>();

        int upperRow;
        int lowerRow;
        if (isWhite) {
            this.color = 1;
            upperRow = 2;
            lowerRow = 1;
        } else {
            this.color = 0;
            upperRow = 7;
            lowerRow = 8;
        }
        for (int i = 1; i <= 8; i++) {
            figures.add(new Pawn(this, new Field(i, upperRow), game.getField()));
        }
        figures.add(new Rook(this, new Field(1, lowerRow), game.getField()));
        figures.add(new Rook(this, new Field(8, lowerRow), game.getField()));

        figures.add(new Knight(this, new Field(2, lowerRow), game.getField()));
        figures.add(new Knight(this, new Field(7, lowerRow), game.getField()));

        figures.add(new Bishop(this, new Field(3, lowerRow), game.getField()));
        figures.add(new Bishop(this, new Field(6, lowerRow), game.getField()));

        figures.add(new Queen(this, new Field(4, lowerRow), game.getField()));
        figures.add(new King(this, new Field(5, lowerRow), game.getField()));

        for (Figure figure : figures) {
            game.getField().replace(figure.getPosition(), figure);
        }
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
    }

    @Override
    public Player clone() {
        Player cloned = new Player();
        cloned.figures = new LinkedList<>();
        for (Figure figure : figures) {
            cloned.figures.add(figure.clone());
        }
        cloned.capturedFigures = new LinkedList<>();
        for (Figure figure : capturedFigures) {
            cloned.capturedFigures.add(figure.clone());
        }
        cloned.controller = controller;
        cloned.color = color;
        return cloned;
    }

    public void finishClone(Game game) {
        this.game = game;
        for (Figure figure : figures) {
            figure.setField(game.getField());
        }
        for (Figure figure : capturedFigures) {
            figure.setField(game.getField());
        }
    }

//    public Player copyPlayer() {
//        List<Figure> figuresCopy = new LinkedList<>();
//        for (Figure figure : figures) {
//            figuresCopy.add(figure.copyFigure());
//        }
//        List<Figure> capturedFiguresCopy = new LinkedList<>();
//        for (Figure capturedFigure : capturedFigures) {
//            capturedFiguresCopy.add(capturedFigure.copyFigure());
//        }
//        Player playerCopy = new Player(color, opponent, game, figuresCopy,
//                capturedFiguresCopy, controller, remainingTime, timeIncrement);
//
//        for (Figure figure : figuresCopy) {
//            figure.setOwner(playerCopy);
//        }
//        for (Figure figure : capturedFiguresCopy) {
//            figure.setOwner(playerCopy);
//        }
//        return playerCopy;
//    }

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
            turns.add(new DrawNotification(this, DrawNotification.DrawType.accepts));
            turns.add(new DrawNotification(this, DrawNotification.DrawType.denies));


            return controller.pickMove(game.getField(), turns);
        }
        Map<Field, Figure> field = game.getField();
        List<Turn> turns = new LinkedList<>();
        for (Figure figure : figures) {
            turns.addAll(figure.getPossibleMoves());
        }
        List<Move> illegalMoves = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof Move) {
                Move move = (Move) turn;
                if (this == game.getWhite()) {
                    if (move.testMove(game).getBlack().isAbleToCaptureKing()) {
                        illegalMoves.add(move);
                    }
                } else {
                    if (move.testMove(game).getWhite().isAbleToCaptureKing()) {
                        illegalMoves.add(move);
                    }
                }
            }
        }
        for (Move move : illegalMoves) {
            turns.remove(move);
        }
        if (turns.size() == 0) {
            if (opponent.isAbleToCaptureKing()) {
                return new Notification(this, Notification.Type.hasLost);
            } else {
                return new Notification(this, Notification.Type.isStalemate);
            }
        }
        turns.add(new DrawNotification(this, DrawNotification.DrawType.offers));
        turns.addAll(Castling.possibleCastlings(game));
        return controller.pickMove(game.getField(), turns);
    }

    private Turn measureChooseTime(Map<Field, Figure> field, List<Turn> turns) {
        Turn turn = controller.pickMove(field, turns);
        return turn;
    }

    public void captureFigure(Figure captured) {
        figures.remove(captured);
        capturedFigures.add(captured);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Controller getController() {
        return controller;
    }

    public int getColor() {
        return color;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setGameClone(Game cloned) {
        this.game = cloned;
        this.opponent = (game.getWhite() == this) ? game.getBlack() : game.getWhite();
        for (Figure figure : figures) {
            figure.setField(game.getField());
            game.getField().replace(figure.getPosition(), figure);
        }
        for (Figure figure : capturedFigures) {
            figure.setField(game.getField());
        }
    }

    public List<Figure> getCapturedFigures() {
        return capturedFigures;
    }
}
