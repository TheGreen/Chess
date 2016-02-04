package edu.gymneureut.informatik.rattenschach.figures;

import edu.gymneureut.informatik.rattenschach.Field;
import edu.gymneureut.informatik.rattenschach.Figure;
import edu.gymneureut.informatik.rattenschach.Move;
import edu.gymneureut.informatik.rattenschach.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by green on 2/2/2016.
 */
public class King extends Figure {
    public King(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    public List<Move> getPossibleMoves() {
        LinkedList<Move> moves = new LinkedList<>();
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

    public King copyFigure() {
        return new King(owner, position, field);
    }
}
