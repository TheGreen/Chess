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

package edu.gymneureut.informatik.rattenschach.control.combination;

import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.controller.TerminalController;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserver;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>TerminalCombination</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class TerminalCombination implements Controller, Observer {
    private final TerminalObserver observer;
    private final TerminalController controller;

    public TerminalCombination() {
        this.observer = new TerminalObserver();
        this.controller = new TerminalController();
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        return controller.pickMove(field, turns);
    }

    @Override
    public void hasWon() {
        controller.hasWon();
    }

    @Override
    public void hasLost() {
        controller.hasLost();
    }

    @Override
    public void isStalemate() {
        controller.isStalemate();
    }

    @Override
    public void isDraw() {
        controller.isDraw();
    }

    @Override
    public void startGame(Game game) {
        System.out.println("Game has started.");
        observer.startGame(game);
    }

    @Override
    public void nextTurn(Turn turn) {
        observer.nextTurn(turn);
    }
}
