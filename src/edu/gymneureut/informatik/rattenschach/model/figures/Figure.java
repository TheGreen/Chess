package edu.gymneureut.informatik.rattenschach.model.figures;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Figure</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public abstract class Figure implements Cloneable {
    public static final Figure EMPTY = new Figure() {
        @Override
        public Figure clone() {
            return Figure.EMPTY;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public List<Move> getPossibleMoves() {
            return new LinkedList<>();
        }
    };
    Player owner;
    Field position;
    Map<Field, Figure> field;
    boolean captured = false;
    boolean hasMoved = false;


    Figure(Player owner, Field position, Map<Field, Figure> field) {
        this.owner = owner;
        this.position = position;
        this.field = field;
    }

    Figure() {

    }

    public abstract List<Move> getPossibleMoves();

    public abstract Figure clone();

    Figure cloneTo(Figure figure) {
        return figure.setAll(owner, position, field, captured, hasMoved);
    }

    private Figure setAll(Player owner, Field position, Map<Field, Figure> field, boolean captured, boolean hasMoved) {
        this.owner = owner;
        this.position = position;
        this.field = field;
        this.captured = captured;
        this.hasMoved = hasMoved;
        return this;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setCaptured() {
        captured = true;
    }

    public void setNotCaptured() {
        captured = false;
    }

    LinkedList<Move> getMoves(int lineChange, int rowChange, Field position, int maxDistance) {
        int walkedDistance = 0;
        Field tempPosition = new Field(position.getFile(), position.getRank());
        boolean directionUnfinished = true;
        LinkedList<Move> moves = new LinkedList<>();
        while (directionUnfinished) {
            if (tempPosition.getFile() + lineChange > 8
                    || tempPosition.getRank() + rowChange > 8
                    || tempPosition.getFile() + lineChange < 1
                    || tempPosition.getRank() + rowChange < 1
                    || (maxDistance != -1 && walkedDistance >= maxDistance)) {
                directionUnfinished = false;
            } else {
                if (maxDistance != -1) {
                    walkedDistance += 1;
                }
                tempPosition = new Field(tempPosition.getFile() + lineChange,
                        tempPosition.getRank() + rowChange);
                Figure resultFigure = field.get(tempPosition);
                if (resultFigure == Figure.EMPTY) {
                    moves.add(new Move(this, position, tempPosition, false, resultFigure));
                } else if (resultFigure.getOwner() == this.getOwner().getOpponent()) {
                    moves.add(new Move(this, position, tempPosition, true, resultFigure));
                    directionUnfinished = false;
                } else if (resultFigure.getOwner() == this.getOwner()) {
                    directionUnfinished = false;
                }
            }
        }
        return moves;
    }

    public void move(Field destinationPosition) {
        if (!hasMoved) {
            hasMoved = true;
        }
        position = destinationPosition;
    }

    public Field getPosition() {
        return position;
    }

    public void setPosition(Field position) {
        this.position = position;
    }

    public void setField(Map<Field, Figure> field) {
        this.field = field;
    }

    public abstract String getName();

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
