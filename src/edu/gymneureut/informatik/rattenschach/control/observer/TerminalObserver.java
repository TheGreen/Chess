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

package edu.gymneureut.informatik.rattenschach.control.observer;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.turns.DrawNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Notification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

/**
 * The <tt>TerminalObserver</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class TerminalObserver implements Observer {
    private Game game;
    private int counter = 0;

    @Override
    public void startGame(Game game) {
        this.game = game;
    }

    @Override
    public void nextTurn(Turn turn) {
        printField();
        System.out.println();
        printTurn(turn);
        try {
            Thread.sleep(10);
//            System.in.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void printField() {
        System.out.println(game.toString());
    }

    private void printTurn(Turn turn) {
        counter += 1;
        if (turn instanceof Move) {
            Move move = (Move) turn;
            System.out.println(move.getFigure().getName()
                    + " from " + move.getOrigin().toString()
                    + " to " + move.getDestination().toString() + "; Turn " + counter);
        } else if (turn instanceof DrawNotification) {
            DrawNotification notification = (DrawNotification) turn;
            if (notification.getDrawType() == DrawNotification.DrawType.offers) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White offers draw");
                } else {
                    System.out.println("Black offers draw");
                }
            } else if (notification.getDrawType() == DrawNotification.DrawType.accepts) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White accepted draw");
                } else {
                    System.out.println("Black accepted draw");
                }
            } else if (notification.getDrawType() == DrawNotification.DrawType.accepts) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White denies draw");
                } else {
                    System.out.println("Black denies draw");
                }
            }
        } else if (turn instanceof Notification) {
            Notification notification = (Notification) turn;
            if (notification.getType() == Notification.Type.hasLost) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White has lost");
                } else {
                    System.out.println("Black has lost");
                }
            } else if (notification.getType() == Notification.Type.isStalemate) {
                System.out.println("Game is Stalemate");
            }
        }
    }
}
