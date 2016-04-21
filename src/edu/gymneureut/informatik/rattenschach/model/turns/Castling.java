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
 * The <tt>Castling</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class Castling extends Turn {
    private final Type type;
    private final King king;
    private final Rook rook;
    private int baseline;

    private Castling(Player executor, Type type, King king, Rook rook, int baseline) {
        super(executor);
        this.type = type;
        this.king = king;
        this.rook = rook;
        this.baseline = baseline;
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
        int baseline = (player.getColor() == 0) ? 8 : 1;
        if (queensideRook != null
                && !queensideRook.hasMoved()
                && game.getField().get(new Field(2, baseline)) == Figure.EMPTY
                && game.getField().get(new Field(3, baseline)) == Figure.EMPTY
                && game.getField().get(new Field(4, baseline)) == Figure.EMPTY
                && new Move(king, king.getPosition(), new Field(3, baseline), false, Figure.EMPTY).isLegal(game)
                && new Move(king, king.getPosition(), new Field(4, baseline), false, Figure.EMPTY).isLegal(game)) {
            castlings.add(new Castling(game.getCurrentPlayer(), Type.queenside, king, queensideRook, baseline));
        }
        if (kingsideRook != null
                && !kingsideRook.hasMoved()
                && game.getField().get(new Field(6, baseline)) == Figure.EMPTY
                && game.getField().get(new Field(7, baseline)) == Figure.EMPTY
                && new Move(king, king.getPosition(), new Field(6, baseline), false, Figure.EMPTY).isLegal(game)
                && new Move(king, king.getPosition(), new Field(7, baseline), false, Figure.EMPTY).isLegal(game)) {
            castlings.add(new Castling(game.getCurrentPlayer(), Type.kingside, king, kingsideRook, baseline));
        }
        return castlings;
    }

    @Override
    public void execute(Game game) {
        if (type == Type.queenside) {
            new Move(king, king.getPosition(), new Field(3, baseline), false, null).execute(game);
            new Move(rook, rook.getPosition(), new Field(4, baseline), false, null).execute(game);
        } else if (type == Type.kingside) {
            new Move(king, king.getPosition(), new Field(7, baseline), false, null).execute(game);
            new Move(rook, rook.getPosition(), new Field(6, baseline), false, null).execute(game);
        }
    }

    @Override
    public String toString() {
        return ""
                + ((type == Type.queenside) ? "Queenside" : "Kingside")
                + " Castling for "
                + ((king.getOwner().getColor() == 1) ? "White" : "Black");
    }

    public King getKing() {
        return king;
    }

    public Rook getRook() {
        return rook;
    }

    public enum Type {
        queenside, kingside
    }
}
