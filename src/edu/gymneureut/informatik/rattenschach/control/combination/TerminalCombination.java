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
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserver;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The <tt>TerminalCombination</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class TerminalCombination implements Controller, Observer {
    private final TerminalObserver observer;

    public TerminalCombination() {
        this.observer = new TerminalObserver();
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a figure to move: \n");
        List<Figure> figures = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof Move) {
                if (!figures.contains(((Move) turn).getFigure())) {
                    figures.add(((Move) turn).getFigure());
                }

            } else if (turn instanceof Castling) {
                if (!figures.contains(((Castling) turn).getKing())) {
                    figures.add(((Castling) turn).getKing());
                }
            }
        }
        for (int i = 1; i <= figures.size(); i++) {
            System.out.println(i + ". "
                    + TerminalObserver.getFigureName(figures.get(i - 1))
                    + " at " + figures.get(i - 1).getPosition().getName());
        }
        System.out.println("Alternatively, you could offer draw:");
        System.out.println((figures.size() + 1) + ". Draw");
        String input = scanner.next();
        if (input.equals("" + (figures.size() + 1))) {
            for (Turn turn : turns) {
                if (turn instanceof DrawNotification
                        && ((DrawNotification) turn).getDrawType() == DrawNotification.DrawType.offers) {
                    return turn;
                }
            }
            System.out.println("Error, offering draw is not available.");
            return pickMove(field, turns);
        } else {
            Figure chosenFigure = figures.get(Integer.parseInt(input) - 1);
            List<Turn> availableTurns = new LinkedList<>();
            for (Turn turn : turns) {
                if (turn instanceof Move && ((Move) turn).getFigure() == chosenFigure) {
                    availableTurns.add(turn);
                } else if (turn instanceof Castling &&
                        (((Castling) turn).getKing() == chosenFigure
                                || ((Castling) turn).getRook() == chosenFigure)) {
                    availableTurns.add(turn);

                }
            }
            System.out.println("Choose your Move:");
            for (int i = 1; i <= availableTurns.size(); i++) {
                System.out.println(i + ". " + availableTurns.get(i - 1).toString());
            }
            input = scanner.next();
            return availableTurns.get(Integer.parseInt(input) - 1);
        }
    }

    private Promotion choosePromotion(List<Promotion> promotions) {
        System.out.print("Choose to what you want to promote your Pawn to:\n");
        for (int i = 0; i < promotions.size(); i++) {
            System.out.println((i + 1) + ". "
                    + TerminalObserver.getFigureName(promotions.get(i).getReplacement()));
        }
        String input = new Scanner(System.in).next();
        int inputInt = Integer.parseInt(input);
        if (inputInt < 0 || inputInt >= promotions.size()) {
            return null;
        } else {
            return promotions.get(inputInt);
        }
    }

    @Override
    public void hasWon() {
        System.out.println("You have Won!");
    }

    @Override
    public void hasLost() {
        System.out.println("You have Lost!");
    }

    @Override
    public void isStalemate() {
        System.out.println("Game is Stalemate!");

    }

    @Override
    public void isDraw() {
        System.out.println("Game is Draw!");
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
