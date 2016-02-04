package edu.gymneureut.informatik.rattenschach;

import java.util.List;
import java.util.Map;

/**
 * Created by green on 2/2/2016.
 */
public interface Controller {
    Turn pickMove(Map<Field, Figure> field, List<Turn> turns);

    void hasWon();

    void hasLost();

    void isPatt();
}
