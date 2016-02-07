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
public class Pawn extends Figure implements Cloneable {
    private int direction;

    public Pawn(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
        direction = (owner.getColor() == 1) ? 1 : -1;
    }

    public Pawn() {

    }


    public List<Move> getPossibleMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        if (captured) {
            return moves;
        }
        Field tempPosition = new Field(position.getLine(), position.getRow());
        tempPosition.setRow(tempPosition.getRow() + direction);
        Figure resultFigure = field.get(tempPosition);
        if (resultFigure == Figure.EMPTY) {
            moves.add(new Move(this, position, tempPosition, false, resultFigure));
        }
        if (!hasMoved && resultFigure == Figure.EMPTY) {
            tempPosition = new Field(position.getLine(), position.getRow());
            tempPosition.setRow(tempPosition.getRow() + 2 * direction);
            resultFigure = field.get(tempPosition);
            if (resultFigure == Figure.EMPTY) {
                moves.add(new Move(this, position, tempPosition, false, resultFigure));
            }
        }
        if (position.getLine() > 1) {
            tempPosition = new Field(position.getLine() - 1, position.getRow() + direction);
            Figure captureFigure = field.get(tempPosition);
            if (captureFigure != Figure.EMPTY && captureFigure.getOwner() != this.owner) {
                moves.add(new Move(this, position, tempPosition, true, captureFigure));
            }
        }
        if (position.getLine() < 8) {
            tempPosition = new Field(position.getLine() + 1, position.getRow() + direction);
            Figure captureFigure = field.get(tempPosition);
            if (captureFigure != Figure.EMPTY && captureFigure.getOwner() != this.owner) {
                moves.add(new Move(this, position, tempPosition, true, captureFigure));
            }
        }
        return moves;
    }

//    public Pawn copyFigure() {
//        return new Pawn(owner, position, field);
//    }

    @Override
    public Figure clone() {
        return cloneTo(new Pawn());
    }
}
