package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * Created by green on 2/17/2016.
 */
public abstract class Turn {
    Player executor;

    protected Turn(Player executor) {
        this.executor = executor;
    }

    public abstract void execute(Game game);

}
