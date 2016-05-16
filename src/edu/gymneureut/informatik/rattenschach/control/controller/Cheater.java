package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.Pawn;
import edu.gymneureut.informatik.rattenschach.model.figures.Queen;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Promotion;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Cheater</tt> controller,
 * that shows, that the game does not enforce rules and an "evil" controller can do what it wants to do.
 * It does not play very well, it works like a RandomCaptureController, but with an additional step,
 * if it can not capture something, it transforms one of it's Pawns to a Queen in Place, without moving it.
 * <p>
 * With access to the field,which it has, a controller can do much more stuff,
 * like crafting and executing turns for the opponent and making illegal moves like it wants to.
 * <p>
 * This class is just a representation that there is no checking int the other code at this time,
 * which is not required anyway, if you play in one of the standard game modes.
 *
 * @author Jan Christian Grünhage, Alex Klug
 * @version 0.1
 */
public class Cheater implements Controller {
    private Controller controller = new RandomCaptureController();


    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Move> capturingMoves = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof Move && ((Move) turn).getCaptures()) {
                capturingMoves.add((Move) turn);
            }
        }
        if (capturingMoves.size() > 0) {
            return capturingMoves.get((int) (Math.random() * (double) capturingMoves.size()));
        }
        for (Figure figure : Controller.getFigures(turns)) {
            if (figure instanceof Pawn) {
                return new Promotion(figure.getOwner(), figure, figure.getPosition(),
                        figure.getPosition(), false, Figure.EMPTY, new Queen(
                        figure.getOwner(), figure.getPosition(), field));
            }
        }
        return controller.pickMove(field, turns);
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
