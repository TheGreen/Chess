package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

/**
 * Created by green on 2/4/2016.
 */
public interface Observer {
    void startGame(Game game);

    void nextTurn(Turn turn);
}
