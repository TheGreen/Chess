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
public class Knight extends Figure implements Cloneable {
    public Knight(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    private Knight() {

    }

    public List<Move> getPossibleMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        if (captured) {
            return moves;
        }
        moves.addAll(getMoves(1, 2, position, 1));
        moves.addAll(getMoves(1, -2, position, 1));
        moves.addAll(getMoves(-1, 2, position, 1));
        moves.addAll(getMoves(-1, -2, position, 1));
        moves.addAll(getMoves(2, 1, position, 1));
        moves.addAll(getMoves(-2, 1, position, 1));
        moves.addAll(getMoves(2, -1, position, 1));
        moves.addAll(getMoves(-2, -1, position, 1));
        return moves;
    }

//    public Knight copyFigure() {
//        return new Knight(owner, position, field);
//    }

    @Override
    public Figure clone() {
        return cloneTo(new Knight());
    }
}
