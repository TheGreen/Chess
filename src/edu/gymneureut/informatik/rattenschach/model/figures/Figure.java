/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
                    moves.add(new Move(this.getOwner(), this, position, tempPosition, false, resultFigure));
                } else if (resultFigure.getOwner() == this.getOwner().getOpponent()) {
                    moves.add(new Move(this.getOwner(), this, position, tempPosition, true, resultFigure));
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

    @Override
    public String toString() {
        return getName() + " at " + getPosition().toString();
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
