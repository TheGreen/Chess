package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.DrawNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>RandomCaptureController</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class RandomCaptureController implements Controller {

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        List<DrawNotification> notifications = new LinkedList<>();
        List<Move> capturingMoves = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof DrawNotification
                    && ((DrawNotification) turn).getDrawType() == DrawNotification.DrawType.offers) {
                notifications.add((DrawNotification) turn);
            } else if (turn instanceof Move && ((Move) turn).getCaptures()) {
                capturingMoves.add((Move) turn);
            }
        }
        for (DrawNotification notification : notifications) {
            turns.remove(notification);
        }
        if (capturingMoves.size() > 0) {
            return capturingMoves.get((int) (Math.random() * (double) capturingMoves.size()));
        }
        return turns.get((int) (Math.random() * (double) turns.size()));
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
