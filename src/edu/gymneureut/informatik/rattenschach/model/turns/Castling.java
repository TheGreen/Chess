package edu.gymneureut.informatik.rattenschach.model.turns;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.Player;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.figures.King;
import edu.gymneureut.informatik.rattenschach.model.figures.Rook;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by green on 2/17/2016.
 */
public class Castling extends Turn {
    private Type type;
    private King king;
    private Rook rook;

    protected Castling(Player executor, Type type, King king, Rook rook) {
        super(executor);
        this.type = type;
        this.king = king;
        this.rook = rook;
    }

    public static List<Castling> possibleCastlings(Game game) {
        List<Castling> castlings = new LinkedList<>();
        Player player = game.getCurrentPlayer();
        List<Figure> figures = player.getFigures();
        King king = null;
        Rook queensideRook = null;
        Rook kingsideRook = null;
        for (Figure figure : figures) {
            if (figure instanceof King) {
                king = (King) figure;
                if (king.hasMoved()) {
                    return castlings;
                }
            } else if (figure instanceof Rook) {
                if (figure.getPosition().getFile() == 1) {
                    queensideRook = (Rook) figure;
                } else if (figure.getPosition().getFile() == 8) {
                    kingsideRook = (Rook) figure;
                }
            }
        }
        if (queensideRook != null
                && !queensideRook.hasMoved()
                && game.getField().get(new Field(2, 1)) == Figure.EMPTY
                && game.getField().get(new Field(3, 1)) == Figure.EMPTY
                && game.getField().get(new Field(4, 1)) == Figure.EMPTY
                && new Move(king, king.getPosition(), new Field(3, 1), false, null).isLegal(game)
                && new Move(king, king.getPosition(), new Field(4, 1), false, null).isLegal(game)) {
            castlings.add(new Castling(game.getCurrentPlayer(), Type.queenside, king, queensideRook));
        }
        if (kingsideRook != null
                && !kingsideRook.hasMoved()
                && game.getField().get(new Field(6, 1)) == Figure.EMPTY
                && game.getField().get(new Field(7, 1)) == Figure.EMPTY
                && new Move(king, king.getPosition(), new Field(6, 1), false, null).isLegal(game)
                && new Move(king, king.getPosition(), new Field(7, 1), false, null).isLegal(game)) {
            castlings.add(new Castling(game.getCurrentPlayer(), Type.kingside, king, kingsideRook));
        }
        return castlings;
    }

    @Override
    public void execute(Game game) {
        if (type == Type.queenside) {
            new Move(king, king.getPosition(), new Field(3, 1), false, null).execute(game);
            new Move(rook, rook.getPosition(), new Field(4, 1), false, null).execute(game);
        } else if (type == Type.kingside) {
            new Move(king, king.getPosition(), new Field(7, 1), false, null).execute(game);
            new Move(rook, rook.getPosition(), new Field(6, 1), false, null).execute(game);
        }
    }

    public enum Type {
        queenside, kingside
    }
}
