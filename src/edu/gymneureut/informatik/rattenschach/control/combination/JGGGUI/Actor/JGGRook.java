package edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI.Actor;

import ch.aplu.jgamegrid.Actor;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;

/**
 * Created by jcgruenhage on 4/28/16.
 */
public class JGGRook extends Actor {
    public JGGRook(Figure figure) {
        super((figure.getOwner().getColor() == 1) ? "sprites/rook_white.png" : "sprites/rook_black.png");
    }
}
