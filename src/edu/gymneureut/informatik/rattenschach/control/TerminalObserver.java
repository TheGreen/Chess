package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.*;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Notification;
import edu.gymneureut.informatik.rattenschach.model.turns.RemisNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.io.IOException;

/**
 * Created by green on 2/5/2016.
 */
public class TerminalObserver implements Observer {
    Game game;

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
            System.in.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println("     1    2    3    4    5    6    7    8  ");
    }

    private String getShortFigureName(Figure figure) {
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

    private void printTurn(Turn turn) {
        if (turn instanceof Move) {
            Move move = (Move) turn;
            System.out.println(getFigureName(move.getFigure())
                    + " from " + move.getOrigin().getName()
                    + " to " + move.getDestination().getName());
        } else if (turn instanceof RemisNotification) {
            RemisNotification notification = (RemisNotification) turn;
            if (notification.getType() == RemisNotification.Type.offers) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White offers remis");
                } else {
                    System.out.println("Black offers remis");
                }
            } else if (notification.getType() == RemisNotification.Type.accepts) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White accepted remis");
                } else {
                    System.out.println("Black accepted remis");
                }
            } else if (notification.getType() == RemisNotification.Type.accepts) {
                if (game.getCurrentPlayer() == game.getWhite()) {
                    System.out.println("White denies remis");
                } else {
                    System.out.println("Black denies remis");
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
            } else if (notification.getType() == Notification.Type.isPatt) {
                System.out.println("Game is Patt");
            }
        }
    }

    private String getFigureName(Figure figure) {
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
}
