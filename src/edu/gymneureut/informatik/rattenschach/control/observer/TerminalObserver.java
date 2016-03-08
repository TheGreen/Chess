package edu.gymneureut.informatik.rattenschach.control.observer;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.*;
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

    private static String getShortFigureName(Figure figure) {
        if (figure instanceof Bishop) {
            return "B" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else if (figure instanceof King) {
            return "K" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else if (figure instanceof Knight) {
            return "N" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else if (figure instanceof Pawn) {
            return "P" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else if (figure instanceof Queen) {
            return "Q" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else if (figure instanceof Rook) {
            return "R" + ((figure.getOwner().getColor() == 0) ? "b" : "w");
        } else {
            return "  ";
        }
    }

    public static String getFigureName(Figure figure) {
        if (figure instanceof Bishop) {
            return "Bishop";
        } else if (figure instanceof King) {
            return "King";
        } else if (figure instanceof Knight) {
            return "Knight";
        } else if (figure instanceof Pawn) {
            return "Pawn";
        } else if (figure instanceof Queen) {
            return "Queen";
        } else if (figure instanceof Rook) {
            return "Rook";
        } else {
            return "";
        }
    }

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
        for (int i = 8; i >= 1; i--) {
            System.out.println("  -----------------------------------------");
            System.out.print(Field.Rank.getName(i) + " |");
            for (int j = 1; j <= 8; j++) {
                System.out.print(" "
                        + getShortFigureName(game.getField().get(new Field(j, i)))
                        + " |");
            }
            System.out.print("\n");
        }
        System.out.println("  -----------------------------------------");
        System.out.println("     A    B    C    D    E    F    G    H  ");
    }

    private void printTurn(Turn turn) {
        counter += 1;
        if (turn instanceof Move) {
            Move move = (Move) turn;
            System.out.println(getFigureName(move.getFigure())
                    + " from " + move.getOrigin().getName()
                    + " to " + move.getDestination().getName() + "; Turn " + counter);
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
