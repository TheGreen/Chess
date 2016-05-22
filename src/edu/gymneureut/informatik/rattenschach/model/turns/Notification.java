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

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * The <tt>Notification</tt> class.
 * Notifies the game about the loss or win of a Player, or the case of a stalemate.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Notification extends Turn {
    private Type type;

    public Notification(Player executor, Type type) {
        super(executor);
        this.type = type;
    }

    Notification(Player executor) {
        super(executor);
    }

    @Override
    public void execute(Game game) {
        if (type == Type.hasLost) {
            if (executor == game.getWhite()) {
                game.setStatus(Game.GameStatus.blackWon);
            } else if (executor == game.getBlack()) {
                game.setStatus(Game.GameStatus.whiteWon);
            }
        } else if (type == Type.isStalemate) {
            game.setStatus(Game.GameStatus.stalemate);
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.hasLost) {
            String player = (executor.getColor() == 1) ? "White" : "Black";
            return player + "has Lost.";
        } else {
            return "Game is stalemate.";
        }
    }

    public enum Type {
        hasLost, isStalemate
    }
}
