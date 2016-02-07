package edu.gymneureut.informatik.rattenschach;

import edu.gymneureut.informatik.rattenschach.figures.*;

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
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void printField() {
        for (int i = 8; i >= 1; i--) {
            System.out.println(" -----------------------------------------");
            for (int j = 1; j <= 8; j++) {
                System.out.print(" | "
                        + getShortFigureName(game.getField().get(new Field(j, i))));
            }
            System.out.print(" | \n");
        }
        System.out.println(" -----------------------------------------");
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
        if (turn.getStatus() == Turn.TurnStatus.normallyRunning) {
            System.out.println(getFigureName(turn.getMove().getFigure())
                    + " from " + turn.getMove().getOrigin().getName()
                    + " to " + turn.getMove().getDestination().getName());
        } else if (turn.getStatus() == Turn.TurnStatus.offersRemis) {
            if (game.getCurrentPlayer() == game.getWhite()) {
                System.out.println("White offers remis");
            } else {
                System.out.println("Black offers remis");
            }
        } else if (turn.getStatus() == Turn.TurnStatus.acceptsRemis) {
            if (game.getCurrentPlayer() == game.getWhite()) {
                System.out.println("White accepted remis");
            } else {
                System.out.println("Black accepted remis");
            }
        } else if (turn.getStatus() == Turn.TurnStatus.deniesRemis) {
            if (game.getCurrentPlayer() == game.getWhite()) {
                System.out.println("White denies remis");
            } else {
                System.out.println("Black denies remis");
            }
        } else if (turn.getStatus() == Turn.TurnStatus.hasLost) {
            if (game.getCurrentPlayer() == game.getWhite()) {
                System.out.println("White has lost");
            } else {
                System.out.println("Black has lost");
            }
        } else if (turn.getStatus() == Turn.TurnStatus.isPatt) {
            System.out.println("Game is Patt");
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
