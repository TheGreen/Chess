package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;

/**
 * The <tt>Promotion</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Promotion extends Move {
    private final Figure replacement;

    public Promotion(Figure figure, Field origin,
                     Field destination, boolean captures,
                     Figure captured, Figure replacement) {
        super(figure, origin, destination, captures, captured);
        this.replacement = replacement;
    }

    @Override
    public void execute(Game game) {
        super.setFigure(replacement);
        super.execute(game);
    }

    @Override
    protected Move cloneWith(Game clonedGame) {
        return new Promotion(clonedGame.getField().get(figure.getPosition()),
                origin, destination, captures, clonedGame.getField().get(captured.getPosition()),
                clonedGame.getField().get(replacement.getPosition()));
    }

    public Figure getReplacement() {
        return replacement;
    }
}
