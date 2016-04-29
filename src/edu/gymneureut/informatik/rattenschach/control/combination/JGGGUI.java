package edu.gymneureut.informatik.rattenschach.control.combination;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.*;
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The <tt>JGameGridGraphicalUserInterface</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class JGGGUI extends GameGrid implements Controller, Observer {
    private Game game;

    public JGGGUI() {
        super(16, 12, 110, false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                super.getBg().fillCell(new Location(i, j), new Color(0xA4, 0x7D, 0x58));
            }
        }

        for (int i = 4; i < 12; i++) {
            for (int j = 2; j < 10; j++) {
                if ((i + j) % 2 == 0) {
                    super.getBg().fillCell(new Location(i, j), new Color(0xFF, 0xCE, 0x9E));
                } else {
                    super.getBg().fillCell(new Location(i, j), new Color(0xD1, 0x8B, 0x47));
                }
            }
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 2; j < 10; j++) {
                if ((i + j) % 2 == 0) {
                    super.getBg().fillCell(new Location(i, j), new Color(0xFF, 0xCE, 0x9E));
                } else {
                    super.getBg().fillCell(new Location(i, j), new Color(0xD1, 0x8B, 0x47));
                }
            }
        }
        for (int i = 13; i < 15; i++) {
            for (int j = 2; j < 10; j++) {
                if ((i + j) % 2 == 0) {
                    super.getBg().fillCell(new Location(i, j), new Color(0xFF, 0xCE, 0x9E));
                } else {
                    super.getBg().fillCell(new Location(i, j), new Color(0xD1, 0x8B, 0x47));
                }
            }
        }
        this.show();
    }

    public static void main(String[] args) {
        new JGGGUI().show();
    }

    private Location fieldToLocation(Field field) {
        return new Location(field.getFile() + 3, 10 - field.getRank());
    }

    private void updateFieldView() {
        super.removeAllActors();
        List<edu.gymneureut.informatik.rattenschach.model.figures.Figure> livingFigures = game.getLivingFigures();
        for (Figure figure : livingFigures) {
            placeFigure(new FigureActor(figure));
        }
    }

    private void updateFieldView(Turn turn) {
        if (turn instanceof Promotion) {
            Promotion promotion = (Promotion) turn;
            if (promotion.getCaptures()) {
                super.removeActorsAt(fieldToLocation(promotion.getDestination()));
            }
            super.removeActorsAt(fieldToLocation(promotion.getOrigin()));
            super.addActor(new FigureActor(promotion.getReplacement()), fieldToLocation(promotion.getDestination()));
        } else if (turn instanceof Move) {
            Move move = (Move) turn;
            if (move.getCaptures()) {
                super.removeActorsAt(fieldToLocation(move.getDestination()));
            }
            super.removeActorsAt(fieldToLocation(move.getOrigin()));
            super.addActor(new FigureActor(move.getFigure()), fieldToLocation(move.getDestination()));
        } else if (turn instanceof Castling) {
            //TODO: Missing Implementation
            System.out.println("TODO:Missing Implementation at JGGGUI.updateFieldView(Turn turn)");
        } else if (turn instanceof DrawNotification) {
            //TODO: Missing Implementation
            System.out.println("TODO:Missing Implementation at JGGGUI.updateFieldView(Turn turn)");
        } else if (turn instanceof Notification) {
            //TODO: Missing Implementation
            System.out.println("TODO:Missing Implementation at JGGGUI.updateFieldView(Turn turn)");
        }
    }


    private void placeFigure(FigureActor figureActor) {
        super.addActor(figureActor, fieldToLocation(figureActor.getFigure().getPosition()));
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        return null;
    }

    @Override
    public void hasWon() {
        System.out.println("has won");
    }

    @Override
    public void hasLost() {
        System.out.println("has lost");
    }

    @Override
    public void isStalemate() {

    }

    @Override
    public void isDraw() {

    }

    @Override
    public void startGame(Game game) {
        this.game = game;
        updateFieldView();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTurn(Turn turn) {
        updateFieldView(turn);
        //updateFieldView();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by jcgruenhage on 4/28/16.
     */
    private static class FigureActor extends Actor {
        private Figure figure;

        public FigureActor(Figure figure) {
            super(
                    (figure instanceof Bishop)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/bishop_white.png" : "sprites/bishop_black.png"
                            : (figure instanceof King)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/king_white.png" : "sprites/king_black.png"
                            : (figure instanceof Knight)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/knight_white.png" : "sprites/knight_black.png"
                            : (figure instanceof Pawn)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/pawn_white.png" : "sprites/pawn_black.png"
                            : (figure instanceof Queen)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/queen_white.png" : "sprites/queen_black.png"
                            : (figure instanceof Rook)
                            ? (figure.getOwner().getColor() == 1)
                            ? "sprites/rook_white.png" : "sprites/rook_black.png"
                            : "sprites/error.png");
            this.figure = figure;
        }

        public Figure getFigure() {
            return figure;
        }
    }
}
