package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;

/**
 * Created by green on 2/17/2016.
 */
public class Move extends Turn {
    final Field origin;
    final Field destination;
    final boolean captures;
    final Figure captured;
    Figure figure;

    public Move(Figure figure, Field origin, Field destination, boolean captures, Figure captured) {
        super(figure.getOwner());
        this.figure = figure;
        this.origin = origin;
        this.destination = destination;
        this.captures = captures;
        this.captured = captured;
    }

    @Override
    public void execute(Game game) {
        game.getField().replace(origin, Figure.EMPTY);
        game.getField().replace(destination, figure);
        figure.setPosition(destination);
        figure.setHasMoved(true);
        if (captures) {
            if (figure.getOwner() == game.getBlack()) {
                game.getWhite().captureFigure(captured);
            } else {
                game.getBlack().captureFigure(captured);
            }
            captured.setCaptured();
            game.captureFigure(captured);
        }
    }

    public Game testMove(Game game) {
        Game clonedGame = game.clone();
        cloneWith(clonedGame).execute(clonedGame);
        return clonedGame;
    }

    public boolean isLegal(Game game) {
        return !testMove(game).getCurrentPlayer().getOpponent().isAbleToCaptureKing();
    }

    Move cloneWith(Game clonedGame) {
        return new Move(clonedGame.getField().get(figure.getPosition()),
                origin, destination, captures,
                clonedGame.getField().get(captured.getPosition()));
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

    void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Field getOrigin() {
        return origin;
    }

    public Field getDestination() {
        return destination;
    }
}
