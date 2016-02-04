package edu.gymneureut.informatik.rattenschach;

/**
 * Created by green on 2/4/2016.
 */
public interface Observer {
    void startGame(Game game);

    void nextTurn(Turn turn);
}
