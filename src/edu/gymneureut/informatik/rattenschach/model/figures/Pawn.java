package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Promotion;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Pawn</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class
Pawn extends Figure implements Cloneable {
    private int direction;

    public Pawn(Player owner, Field position, Map<Field, Figure> field) {
        super(owner, position, field);
        direction = (owner.getColor() == 1) ? 1 : -1;
    }

    private Pawn() {
        super();
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
            if (tempPosition.getRank() < 8
                    && tempPosition.getRank() > 1) {
                moves.add(new Move(this, position, tempPosition, false, resultFigure));
            } else {
                moves.add(new Promotion(this, position, tempPosition, false, resultFigure,
                        new Queen(this.owner, position, field)));
                moves.add(new Promotion(this, position, tempPosition, false, resultFigure,
                        new Knight(this.owner, position, field)));
                moves.add(new Promotion(this, position, tempPosition, false, resultFigure,
                        new Rook(this.owner, position, field)));
                moves.add(new Promotion(this, position, tempPosition, false, resultFigure,
                        new Bishop(this.owner, position, field)));
            }
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
            Figure capturedFigure = field.get(tempPosition);
            if (capturedFigure != Figure.EMPTY && capturedFigure.getOwner() != this.owner) {
                if (tempPosition.getRank() < 8
                        && tempPosition.getRank() > 1) {
                    moves.add(new Move(this, position, tempPosition, true, capturedFigure));
                } else {
                    moves.add(new Promotion(this, position, tempPosition, true, capturedFigure,
                            new Queen(this.owner, position, field)));
                    moves.add(new Promotion(this, position, tempPosition, true, capturedFigure,
                            new Rook(this.owner, position, field)));
                    moves.add(new Promotion(this, position, tempPosition, true, capturedFigure,
                            new Knight(this.owner, position, field)));
                    moves.add(new Promotion(this, position, tempPosition, true, capturedFigure,
                            new Bishop(this.owner, position, field)));
                }
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

    @Override
    public Figure clone() {
        return cloneTo(new Pawn());
    }

    @Override
    public String getName() {
        return "Pawn";
    }
}
