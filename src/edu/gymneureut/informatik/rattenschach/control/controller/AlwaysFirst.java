package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>AlwaysFirst</tt> controller,
 * that always chooses the first out of a list of Turns.
 *
 * @author Jan Christian Grünhage, Alex Klug
 * @version 0.1
 */
public class AlwaysFirst implements Controller {
    TerminalController controller = new TerminalController();

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        return turns.get(0);
    }

    @Override
    public void hasWon() {
        controller.hasWon();
    }

    @Override
    public void hasLost() {
        controller.hasLost();
    }

    @Override
    public void isStalemate() {
        controller.isStalemate();
    }

    @Override
    public void isDraw() {
        controller.isDraw();
    }
}
