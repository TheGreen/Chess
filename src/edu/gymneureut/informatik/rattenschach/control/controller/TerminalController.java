/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.gymneureut.informatik.rattenschach.control.controller;

import de.janchristiangruenhage.util.terminal.menu.Item;
import de.janchristiangruenhage.util.terminal.menu.Menu;
import de.janchristiangruenhage.util.terminal.menu.MenuItem;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.DrawNotification;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.List;
import java.util.Map;

/**
 * The <tt>TerminalController</tt> controller,
 * that allows the user to the game via terminal.
 *
 * @author Jan Christian Grünhage, Alex Klug
 * @version 0.1
 */
public class TerminalController implements Controller {
    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        Menu menu = generateMenu(turns);

        MenuItem item = menu;

        while (item instanceof Menu) {
            item = item.select();
        }

        return ((Turn) ((Item) item).getContent());
    }


    /**
     * Generates a menu of possible Turns according to the given list.
     * Instead of just giving a list of all possible turns,
     * they are sorted into sub-menus according to the figures executing them.
     *
     * The Menu is generated using a small Menu framework written to be reusable later.
     *
     * @param turns list of turns
     * @return the Menu
     */
    private Menu generateMenu(List<Turn> turns) {
        //Generate basic menu
        Menu menu = new Menu("Select Figure", "Select your Figure:");
        Item<DrawNotification> offerDraw = drawPossible(turns);
        menu.add(offerDraw);

        //Generate List of Figures for sub menues
        List<Figure> figures = Controller.getFigures(turns);

        //Generate a submenu for each figure
        for (Figure figure : figures) {
            Menu figureMenu = new Menu(menu, figure.toString(), "Select your Destination:");
            menu.add(figureMenu);
            List<Turn> turnsForFigure = Controller.getTurnsForFigure(figure, turns);
            for (Turn turn : turnsForFigure) {
                figureMenu.add(new Item<>(turn.toString(), turn));
            }
        }

        //Generate a submenu for all figures
        Menu figureMenu = new Menu(menu, "All figures", "Select your Destination:");
        menu.add(figureMenu);
        for (Figure figure : figures) {
            List<Turn> turnsForFigure = Controller.getTurnsForFigure(figure, turns);
            for (Turn turn : turnsForFigure) {
                figureMenu.add(new Item<>(turn.toString(), turn));
            }
        }

        return menu;
    }


    private Item<DrawNotification> drawPossible(List<Turn> turns) {
        for (Turn turn : turns) {
            if (turn instanceof DrawNotification
                    && ((DrawNotification) turn).getDrawType() == DrawNotification.DrawType.offers) {
                return new Item<>("Offer Draw", (DrawNotification) turn);
            }
        }
        return Item.NULL;
    }

    @Override
    public void hasWon() {
        System.out.println("You have Won!");
    }

    @Override
    public void hasLost() {
        System.out.println("You have Lost!");
    }

    @Override
    public void isStalemate() {
        System.out.println("Game is Stalemate!");
    }

    @Override
    public void isDraw() {
        System.out.println("Game is Draw!");
    }
}
