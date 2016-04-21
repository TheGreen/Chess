package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * The abstract <tt>Turn</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public abstract class Turn {
    final Player executor;

    Turn(Player executor) {
        this.executor = executor;
    }

    public abstract void execute(Game game);

}
