package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Bishop</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Bishop extends Figure implements Cloneable {

    public Bishop(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    private Bishop() {
        super();
    }


    public List<Move> getPossibleMoves() {
        List<Move> moves = new LinkedList<>();
        if (captured) {
            return moves;
        }
        moves.addAll(getMoves(1, 1, position, -1));
        moves.addAll(getMoves(1, -1, position, -1));
        moves.addAll(getMoves(-1, 1, position, -1));
        moves.addAll(getMoves(-1, -1, position, -1));
        return moves;
    }

//    public Bishop copyFigure() {
//        return new Bishop(owner, position, field);
//    }

    @Override
    public Figure clone() {
        return cloneTo(new Bishop());
    }

    @Override
    public String getName() {
        return "Bishop";
    }


}
