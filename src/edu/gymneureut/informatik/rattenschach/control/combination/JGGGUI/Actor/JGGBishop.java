package edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI.Actor;

import ch.aplu.jgamegrid.Actor;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;

/**
 * Created by jcgruenhage on 4/28/16.
 */
public class JGGBishop extends Actor {
    public JGGBishop(Figure figure) {
        super((figure.getOwner().getColor() == 1) ? "sprites/bishop_white.png" : "sprites/bishop_black.png");
    }
}
