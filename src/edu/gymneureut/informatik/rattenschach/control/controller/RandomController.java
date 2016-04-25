package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.DrawNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>RandomController</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class RandomController implements Controller {
    private int numberOfGames;
    private int gamesWon;
    private int gamesLost;
    private int gamesDraw;
    private int gamesStalemate;

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        for (Turn turn : turns) {
            if (turn instanceof DrawNotification
                    && ((DrawNotification) turn).getDrawType() == DrawNotification.DrawType.offers) {
                turns.remove(turn);
            }
        }
        return turns.get((int) (Math.random() * (double) turns.size()));
    }

    @Override
    public void hasWon() {
        numberOfGames += 1;
        gamesWon += 1;
    }

    @Override
    public void hasLost() {
        numberOfGames += 1;
        gamesLost += 1;
    }

    @Override
    public void isStalemate() {
        numberOfGames += 1;
        gamesStalemate += 1;
    }

    @Override
    public void isDraw() {
        numberOfGames += 1;
        gamesDraw += 1;
    }
}
