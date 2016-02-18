package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * Created by green on 2/17/2016.
 */
public class Notification extends Turn {
    private Type type;

    public Notification(Player executor, Type type) {
        super(executor);
        this.type = type;
    }

    @Override
    public void execute(Game game) {
        if (type == Type.hasLost) {
            if (executor == game.getWhite()) {
                game.setStatus(Game.GameStatus.blackWon);
            } else if (executor == game.getBlack()) {
                game.setStatus(Game.GameStatus.whiteWon);
            }
        } else if (type == Type.isPatt) {
            game.setStatus(Game.GameStatus.patt);
        }
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        hasLost, isPatt
    }
}
