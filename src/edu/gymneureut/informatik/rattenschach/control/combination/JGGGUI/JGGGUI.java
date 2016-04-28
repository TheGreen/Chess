package edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

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
        super(8, 8, 120, false);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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
        return new Location(field.getFile() - 1, 8 - field.getRank());
    }

    private void updateFieldView() {
        super.removeAllActors();
        List<edu.gymneureut.informatik.rattenschach.model.figures.Figure> livingFigures = game.getLivingFigures();
        for (Figure figure : livingFigures) {
            placeFigure(new FigureActor(figure));
        }
    }

    private void updateFieldView(Turn turn) {
        if (turn instanceof Move) {
            Move move = (Move) turn;
            if (move.getCaptures()) {
                super.removeActorsAt(fieldToLocation(move.getDestination()));
            }
            super.removeActorsAt(fieldToLocation(move.getOrigin()));
            super.addActor(new FigureActor(move.getFigure()), fieldToLocation(move.getDestination()));
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

    }

    @Override
    public void hasLost() {

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
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
