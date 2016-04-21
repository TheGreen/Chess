package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Queen</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Queen extends Figure implements Cloneable {
    public Queen(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    private Queen() {

    }

    public List<Move> getPossibleMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        if (captured) {
            return moves;
        }
        moves.addAll(getMoves(1, 1, position, -1));
        moves.addAll(getMoves(1, -1, position, -1));
        moves.addAll(getMoves(-1, 1, position, -1));
        moves.addAll(getMoves(-1, -1, position, -1));
        moves.addAll(getMoves(1, 0, position, -1));
        moves.addAll(getMoves(-1, 0, position, -1));
        moves.addAll(getMoves(0, 1, position, -1));
        moves.addAll(getMoves(0, -1, position, -1));
        return moves;
    }

//    public Queen copyFigure() {
//        return new Queen(owner, position, field);
//    }

    @Override
    public Figure clone() {
        return cloneTo(new Queen());
    }

    @Override
    public String getName() {
        return "Queen";
    }
}
