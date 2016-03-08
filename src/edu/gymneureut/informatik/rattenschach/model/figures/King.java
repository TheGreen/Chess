package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Castling;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>King</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class King extends Figure implements Cloneable {
    public King(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    private King() {
    }

    public List<Move> getPossibleMoves() {
        List<Move> moves = new LinkedList<>();
        moves.addAll(getMoves(1, 1, position, 1));
        moves.addAll(getMoves(1, -1, position, 1));
        moves.addAll(getMoves(-1, 1, position, 1));
        moves.addAll(getMoves(-1, -1, position, 1));
        moves.addAll(getMoves(1, 0, position, 1));
        moves.addAll(getMoves(-1, 0, position, 1));
        moves.addAll(getMoves(0, 1, position, 1));
        moves.addAll(getMoves(0, -1, position, 1));
        return moves;
    }

    public List<Castling> getPossibleCastlings() {
        List<Castling> castlings = new LinkedList<>();
        //TODO implementation
        return castlings;
    }

//    public King copyFigure() {
//        return new King(owner, position, field);
//    }

    @Override
    public Figure clone() {
        return cloneTo(new King());
    }

    @Override
    public String getName() {
        return "King";
    }
}
