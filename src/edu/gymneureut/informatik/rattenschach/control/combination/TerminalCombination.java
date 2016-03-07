package edu.gymneureut.informatik.rattenschach.control.combination;

import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserver;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.Pawn;
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by green on 2/18/2016.
 */
public class TerminalCombination implements Controller, Observer {
    private final TerminalObserver observer;

    public TerminalCombination() {
        this.observer = new TerminalObserver();
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your next Turn: \n");
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

        }


        Field origin = Field.parseField(input.split(";")[0]);
        Field destination = Field.parseField(input.split(";")[1]);
        if (field.get(origin) instanceof Pawn
                && (destination.getRank() == 1 || destination.getRank() == 8)) {
            List<Promotion> promotions = new LinkedList<>();
            for (Turn turn : turns) {
                if (turn instanceof Promotion) {
                    promotions.add((Promotion) turn);
                }
            }
            Promotion chosen = choosePromotion(promotions);
            if (chosen != null) {
                return chosen;
            } else {
                return pickMove(field, turns);
            }
        } else {
            List<Move> moves = new LinkedList<>();
            for (Turn turn : turns) {
                if (turn instanceof Move) {
                    Move move = (Move) turn;
                    if (origin == move.getOrigin() && destination == move.getDestination()) {
                        return move;
                    }
                }
            }
        }

        return null;
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
