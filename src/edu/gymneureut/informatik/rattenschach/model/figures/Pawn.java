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
        Field tempPosition = new Field(position.getFile(), position.getRank());
        tempPosition.setRank(tempPosition.getRank() + direction);
        Figure resultFigure = field.get(tempPosition);
        if (resultFigure == Figure.EMPTY) {
            moves.add(new Move(this, position, tempPosition, false, resultFigure));
        }
        if (!hasMoved && resultFigure == Figure.EMPTY) {
            tempPosition = new Field(position.getFile(), position.getRank());
            tempPosition.setRank(tempPosition.getRank() + 2 * direction);
            resultFigure = field.get(tempPosition);
            if (resultFigure == Figure.EMPTY) {
                moves.add(new Move(this, position, tempPosition, false, resultFigure));
            }
        }
        if (position.getFile() > 1) {
            tempPosition = new Field(position.getFile() - 1, position.getRank() + direction);
            Figure captureFigure = field.get(tempPosition);
            if (captureFigure != Figure.EMPTY && captureFigure.getOwner() != this.owner) {
                moves.add(new Move(this, position, tempPosition, true, captureFigure));
            }
        }
        if (position.getFile() < 8) {
            tempPosition = new Field(position.getFile() + 1, position.getRank() + direction);
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
