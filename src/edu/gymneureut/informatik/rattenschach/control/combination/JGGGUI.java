package edu.gymneureut.informatik.rattenschach.control.combination;

import ch.aplu.jgamegrid.GameGrid;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>JGameGridGraphicalUserInterface</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class JGGGUI extends GameGrid implements Controller, Observer {

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

    }

    @Override
    public void nextTurn(Turn turn) {

    }
}
