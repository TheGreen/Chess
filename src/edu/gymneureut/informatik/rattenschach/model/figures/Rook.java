package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by green on 2/2/2016.
 */
public class Rook extends Figure implements Cloneable {
    public Rook(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    public Rook() {

    }

    public List<Move> getPossibleMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        if (captured) {
            return moves;
        }
        moves.addAll(getMoves(1, 0, position, -1));
        moves.addAll(getMoves(-1, 0, position, -1));
        moves.addAll(getMoves(0, 1, position, -1));
        moves.addAll(getMoves(0, -1, position, -1));
        return moves;
    }

    @Override
    public Figure clone() {
        return cloneTo(new Rook());
    }
}
