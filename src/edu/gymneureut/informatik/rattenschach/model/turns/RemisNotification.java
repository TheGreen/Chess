package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;

/**
 * Created by green on 2/17/2016.
 */
public class RemisNotification extends Turn {
    private Type type;

    public RemisNotification(Player executor, Type type) {
        super(executor);
        this.type = type;
    }

    @Override
    public void execute(Game game) {
        if (type == Type.offers) {
            game.setStatus(Game.GameStatus.remisOffered);
        } else if (type == Type.accepts) {
            game.setStatus(Game.GameStatus.remis);
        } else if (type == Type.denies) {
            game.setStatus(Game.GameStatus.running);
        }
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        offers, accepts, denies
    }
}
