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
public class Pawn extends Figure implements Cloneable {
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
        Field destination;

        //FORWARD

        destination = forwardStraight(position);
        if (field.get(destination) == EMPTY) {
            getPossibleMoves(destination, false, moves);
            if (destination.getRank() < 8 && destination.getRank() > 1) {
                destination = forwardStraight(forwardStraight(position));
                if (!hasMoved && field.get(destination) == EMPTY) {
                    getPossibleMoves(destination, false, moves);
                }
            }
        }

        //CAPTURE LEFT

        if (position.getFile() > 1) {
            destination = forwardLeft(position);
            if (field.get(destination) != EMPTY
                    && field.get(destination).getOwner() != this.getOwner()) {
                getPossibleMoves(destination, true, moves);
            }
        }

        //CAPTURE RIGHT

        if (position.getFile() < 8) {
            destination = forwardRight(position);
            if (field.get(destination) != EMPTY
                    && field.get(destination).getOwner() != this.getOwner()) {
                getPossibleMoves(destination, true, moves);
            }
        }
        return moves;
    }

    private void getPossibleMoves(Field destination, boolean captures, List<Move> moves) {
        if (destination.getRank() < 8
                && destination.getRank() > 1) {
            moves.add(new Move(this.getOwner(), this, position, destination, captures, field.get(destination)));
        } else {
            moves.add(new Promotion(this.getOwner(), this, position, destination, captures, field.get(destination),
                    new Queen(this.owner, position, field)));
            moves.add(new Promotion(this.getOwner(), this, position, destination, captures, field.get(destination),
                    new Rook(this.owner, position, field)));
            moves.add(new Promotion(this.getOwner(), this, position, destination, captures, field.get(destination),
                    new Knight(this.owner, position, field)));
            moves.add(new Promotion(this.getOwner(), this, position, destination, captures, field.get(destination),
                    new Bishop(this.owner, position, field)));
        }
    }

    private Field forwardStraight(Field position) {
        return new Field(position.getFile(), position.getRank() + direction);
    }

    private Field forwardRight(Field position) {
        return new Field(position.getFile() + 1, position.getRank() + direction);
    }

    private Field forwardLeft(Field position) {
        return new Field(position.getFile() - 1, position.getRank() + direction);
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
