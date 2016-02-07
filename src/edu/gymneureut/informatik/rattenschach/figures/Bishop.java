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
public class Bishop extends Figure implements Cloneable {

    public Bishop(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
    }

    public Bishop() {

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


}
