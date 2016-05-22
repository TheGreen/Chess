package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.DrawNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>DrawOfferer</tt> controller, that only offers draw always.
 *
 * @author Jan Christian Grünhage, Alex Klug
 * @version 0.1
 */
public class DrawOfferer implements Controller {
    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        return turns.stream().filter(turn ->
                turn instanceof DrawNotification
                        && ((DrawNotification) turn).getDrawType()
                        == DrawNotification.DrawType.offers)
                .findFirst().get();
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
}
