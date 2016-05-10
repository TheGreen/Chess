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
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.Pawn;

/**
 * The <tt>Promotion</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Promotion extends Move {
    private Figure replacement;
    private Pawn pawn;

    public Promotion(Player executor, Figure figure, Field origin,
                     Field destination, boolean captures,
                     Figure captured, Figure replacement) {
        super(executor, figure, origin, destination, captures, captured);
        if (replacement instanceof Pawn) {
            throw new IllegalArgumentException("Pawns cannot get promoted to being a Pawn");
        }
        pawn = (Pawn) figure;
        this.replacement = replacement;
    }

    @Override
    public void execute(Game game) {
        super.setFigure(replacement);
        game.promotePawn(pawn, replacement);
        executor.promotePawn(pawn, replacement);
        super.execute(game);
    }

    @Override
    protected Move cloneWith(Game clonedGame) {
        return new Promotion((executor.getColor() == 1) ? clonedGame.getWhite() : clonedGame.getBlack(),
                clonedGame.getField().get(figure.getPosition()), origin, destination, captures,
                clonedGame.getField().get(captured.getPosition()), replacement.clone());
    }

    public Figure getReplacement() {
        return replacement;
    }

    @Override
    public String toString() {
        return super.toString()
                + " being promoted to "
                + replacement.getName();
    }
}
