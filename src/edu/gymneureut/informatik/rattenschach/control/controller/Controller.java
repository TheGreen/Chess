package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>Controller</tt> interface.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public interface Controller {
    Turn pickMove(Map<Field, Figure> field, List<Turn> turns);

    void hasWon();

    void hasLost();

    void isStalemate();

    void isDraw();
}
