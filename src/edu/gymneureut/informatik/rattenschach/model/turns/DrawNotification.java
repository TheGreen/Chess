package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * The <tt>DrawNotification
 * </tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class DrawNotification extends Notification {
    private final DrawType drawType;

    public DrawNotification(Player executor, DrawType drawType) {
        super(executor);
        this.drawType = drawType;
    }

    @Override
    public void execute(Game game) {
        if (drawType == DrawType.offers) {
            game.setStatus(Game.GameStatus.remisOffered);
        } else if (drawType == DrawType.accepts) {
            game.setStatus(Game.GameStatus.draw);
        } else if (drawType == DrawType.denies) {
            game.setStatus(Game.GameStatus.running);
        }
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public enum DrawType {
        offers, accepts, denies
    }
}
