package edu.gymneureut.informatik.rattenschach;

/**
 * Created by green on 2/2/2016.
 */
public class Move {
    Figure figure;
    Field origin;
    Field destination;

    boolean captures;
    Figure captured;

    public Move(Figure figure, Field origin, Field destination, boolean captures, Figure captured) {
        this.figure = figure;
        this.origin = origin;
        this.destination = destination;
        this.captures = captures;
        this.captured = captured;
    }

    public void execute(Game game) {
        game.getField().replace(origin, Figure.EMPTY);
        game.getField().replace(destination, figure);
        figure.setPosition(destination);
        if (captures) {
            if (figure.getOwner() == game.getBlack()) {
                game.getWhite().captureFigure(captured);
            } else {
                game.getBlack().captureFigure(captured);
            }
            captured.setCaptured();
        }
    }

    public Game testMove(Game game) {
        Game clonedGame = game.clone();
        cloneWith(clonedGame).execute(clonedGame);
        return clonedGame;
    }

    private Move cloneWith(Game clonedGame) {
        return new Move(clonedGame.getField().get(origin),
                origin, destination, captures,
                clonedGame.getField().get(destination));
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

    public Field getOrigin() {
        return origin;
    }

    public Field getDestination() {
        return destination;
    }
}
