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
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The Class <tt>TerminalController</tt>.
 * Is a simple Controller inside the Terminal.
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

    private Menu generateMenu(List<Turn> turns) {
        //Generate basic menu
        Menu menu = new Menu("Select Figure", "Select your Figure:");
        Item<DrawNotification> offerDraw = drawPossible(turns);
        menu.add(offerDraw);

        //Generate List of Figures for sub menues
        List<Figure> figures = getFigures(turns);

        //Generate a submenu for each figure
        for (Figure figure : figures) {
            Menu figureMenu;
            figureMenu = new Menu(menu, figure.toString(), "Select your Destination:");
            menu.add(figureMenu);
            List<Turn> turnsForFigure = getTurnsForFigure(figure, turns);
            for (Turn turn : turnsForFigure) {
                figureMenu.add(new Item<>(turn.toString(), turn));
            }
        }
        return menu;
    }

    private List<Turn> getTurnsForFigure(Figure figure, List<Turn> turns) {
        List<Turn> turnsForFigure = new LinkedList<>();
        for (Turn turn : turns) {
            if ((turn instanceof Promotion
                    && ((Promotion) turn).getPawn() == figure)
                    || (turn instanceof Move
                    && ((Move) turn).getFigure() == figure)
                    || (turn instanceof Castling
                    && ((((Castling) turn).getKing() == figure)
                    || ((Castling) turn).getRook() == figure))) {
                turnsForFigure.add(turn);
            }
        }
        return turnsForFigure;
    }

    private List<Figure> getFigures(List<Turn> turns) {
        List<Figure> figures = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof Promotion) {
                Figure figure = ((Promotion) turn).getPawn();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            } else if (turn instanceof Move) {
                Figure figure = ((Move) turn).getFigure();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            } else if (turn instanceof Castling) {
                Figure figure = ((Castling) turn).getKing();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
                figure = ((Castling) turn).getRook();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            }
        }
        return figures;
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
