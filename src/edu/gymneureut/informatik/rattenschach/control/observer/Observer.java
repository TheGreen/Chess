package edu.gymneureut.informatik.rattenschach.control.observer;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

/**
 * The <tt>Observer</tt> interface.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public interface Observer {
    void startGame(Game game);

    void nextTurn(Turn turn);
}
