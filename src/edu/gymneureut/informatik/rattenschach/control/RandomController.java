package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.RemisNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;


public class RandomController implements Controller {
    int numberOfGames;
    int gamesWon;
    int gamesLost;
    int gamesPatt;

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        for (Turn turn : turns) {
            if (turn instanceof RemisNotification
                    && ((RemisNotification) turn).getType() == RemisNotification.Type.offers) {
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
    public void isPatt() {
        numberOfGames += 1;
        gamesPatt += 1;
    }

    public void print() {
        System.out.println("Total Games: " + numberOfGames);
        System.out.println("Won Games: " + gamesWon);
        System.out.println("Lost Games: " + gamesLost);
        System.out.println("Patt or RemisNotification: " + gamesPatt);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
