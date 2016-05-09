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

package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.King;

/**
 * The <tt>Move</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Move extends Turn {
    final Field origin;
    final Field destination;
    final boolean captures;
    final Figure captured;
    Figure figure;

    public Move(Figure figure, Field origin, Field destination, boolean captures, Figure captured) {
        super(figure.getOwner());
        this.figure = figure;
        this.origin = origin;
        this.destination = destination;
        this.captures = captures;
        this.captured = captured;
    }

    @Override
    public void execute(Game game) {
        game.getField().replace(origin, Figure.EMPTY);
        game.getField().replace(destination, figure);
        figure.setPosition(destination);
        figure.setHasMoved(true);
        if (captures) {
            if (captured instanceof King) {
                throw new IllegalStateException("Capturing King is an illegal Move and should be illegal");
            }
            if (figure.getOwner() == game.getBlack()) {
                game.getWhite().captureFigure(captured);
            } else {
                game.getBlack().captureFigure(captured);
            }
            captured.setCaptured();
            game.captureFigure(captured);
        }
    }

    private Game testMove(Game game) {
        Game clonedGame = game.clone();
        cloneWith(clonedGame).execute(clonedGame);
        return clonedGame;
    }

    public boolean isLegal(Game game) {
        return !testMove(game).getCurrentPlayer().getOpponent().isAbleToCaptureKing();
    }

    Move cloneWith(Game clonedGame) {
        return new Move(clonedGame.getField().get(figure.getPosition()),
                origin, destination, captures,
                clonedGame.getField().get(captured.getPosition()));
    }

    public boolean getCaptures() {
        return captures;
    }

    public Figure getCapturedFigure() {
        return captured;
    }

    public Figure getFigure() {
        return figure;
    }

    void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Field getOrigin() {
        return origin;
    }

    public Field getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "" + figure.getName()
                + " from "
                + origin.getName()
                + " to "
                + destination.getName()
                + ((captures) ? " capturing " + captured.getName() : "");
    }
}
