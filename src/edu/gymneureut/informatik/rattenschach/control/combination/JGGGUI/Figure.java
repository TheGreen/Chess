package edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI;

import ch.aplu.jgamegrid.Actor;
import edu.gymneureut.informatik.rattenschach.model.figures.*;

/**
 * Created by jcgruenhage on 4/28/16.
 */
public class Figure extends Actor {
    public Figure(edu.gymneureut.informatik.rattenschach.model.figures.Figure figure) {
        super(
                (figure instanceof Bishop)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/bishop_white.png" : "sprites/bishop_black.png"
                        : (figure instanceof King)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/king_white.png" : "sprites/king_black.png"
                        : (figure instanceof Knight)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/knight_white.png" : "sprites/knight_black.png"
                        : (figure instanceof Pawn)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/pawn_white.png" : "sprites/pawn_black.png"
                        : (figure instanceof Queen)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/queen_white.png" : "sprites/queen_black.png"
                        : (figure instanceof Rook)
                        ? (figure.getOwner().getColor() == 1)
                        ? "sprites/rook_white.png" : "sprites/rook_black.png"
                        : "sprites/error.png");
    }
}
