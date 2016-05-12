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

package edu.gymneureut.informatik.rattenschach.control.combination;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import de.janchristiangruenhage.util.exceptions.FeatureNotImplementedYetException;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.Game;
import edu.gymneureut.informatik.rattenschach.model.figures.*;
import edu.gymneureut.informatik.rattenschach.model.turns.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>JGameGridGraphicalUserInterface</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class JGGGUI extends GameGrid implements Controller, Observer {
    private Game game;
    private int cellsize;

    public JGGGUI(int cellsize) {
        super(16, 12, cellsize, false);
        this.cellsize = cellsize;

        drawBackground();
        drawChessboard();
        drawCaptured();

        this.show();
    }

    public static void main(String[] args) {
        new JGGGUI(110).show();
    }

    private void drawCaptured() {
        drawCaptured(1);
        drawCaptured(13);
    }

    private void drawCaptured(int n) {
        for (int i = n; i < n + 2; i++) {
            for (int j = 2; j < 10; j++) {
                drawChessTile(i, j);
            }
        }
    }

    private void drawChessboard() {
        for (int i = 4; i < 12; i++) {
            for (int j = 2; j < 10; j++) {
                drawChessTile(i, j);
            }
        }
    }

    private void drawChessTile(int i, int j) {
        if ((i + j) % 2 == 1) {
            super.getBg().fillCell(new Location(i, j), new Color(0xFF, 0xCE, 0x9E));
        } else {
            super.getBg().fillCell(new Location(i, j), new Color(0xD1, 0x8B, 0x47));
        }
    }

    private void drawBackground() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                super.getBg().fillCell(new Location(i, j), new Color(0x65, 0x3E, 0x19));
            }
        }
    }

    private Location fieldToLocation(Field field) {
        return new Location(field.getFile() + 3, 10 - field.getRank());
    }

    private void updateUI() {
        super.removeAllActors();
        List<Figure> livingFigures = game.getLivingFigures();
        for (Figure figure : livingFigures) {
            placeFigure(new FigureActor(figure, cellsize));
        }
        updateUICaptured();
    }

    private void updateUI(Turn turn) {
        if (turn instanceof Promotion) {
            updateUI((Promotion) turn);

        } else if (turn instanceof Move) {
            updateUI((Move) turn);

        } else if (turn instanceof Castling) {
            updateUI((Castling) turn);

        } else if (turn instanceof DrawNotification) {
            //TODO: Missing Implementation
            System.out.println("TODO:Missing Implementation at JGGGUI.updateUI(Turn turn)");
            updateUI();

        } else if (turn instanceof Notification) {
            //TODO: Missing Implementation
            System.out.println("TODO:Missing Implementation at JGGGUI.updateUI(Turn turn)");
            updateUI();
        }
    }

    private void updateUI(Promotion promotion) {
        if (promotion.getCaptures()) {
            super.removeActorsAt(fieldToLocation(promotion.getDestination()));
        }
        super.removeActorsAt(fieldToLocation(promotion.getOrigin()));
        super.addActor(new FigureActor(promotion.getReplacement(), cellsize),
                fieldToLocation(promotion.getDestination()));
    }

    private void updateUI(Move move) {
        if (move.getCaptures()) {
            super.removeActorsAt(fieldToLocation(move.getDestination()));
            updateUICaptured();
        }
        super.removeActorsAt(fieldToLocation(move.getOrigin()));
        super.addActor(new FigureActor(move.getFigure(), cellsize), fieldToLocation(move.getDestination()));
    }

    private void updateUI(Castling castling) {
        super.removeActorsAt(fieldToLocation(castling.getKingOrigin()));
        super.removeActorsAt(fieldToLocation(castling.getRookOrigin()));
        super.addActor(new FigureActor(castling.getKing(), cellsize),
                fieldToLocation(castling.getKingDestination()));
        super.addActor(new FigureActor(castling.getRook(), cellsize),
                fieldToLocation(castling.getRookDestination()));
    }


    private void updateUICaptured() {
        List<Figure> capturedFigures = game.getCapturedFigures();
        List<Figure> capturedWhiteFigures = new LinkedList<>();
        List<Figure> capturedBlackFigures = new LinkedList<>();
        for (Figure figure : capturedFigures) {
            if (figure.getOwner().getColor() == 1) {
                capturedWhiteFigures.add(figure);
            } else {
                capturedBlackFigures.add(figure);
            }
        }
        updateUICaptured(capturedBlackFigures, false);
        updateUICaptured(capturedWhiteFigures, true);
    }

    private void updateUICaptured(List<Figure> capturedFigures, boolean isWhite) {
        int x, y;
        if (isWhite) {
            x = 13;
            y = 2;
        } else {
            x = 1;
            y = 2;
        }
        for (Figure figure : capturedFigures) {
            super.addActor(new FigureActor(figure, cellsize), new Location(x, y));
            y += 1;
            if (y >= 10) {
                x += 1;
                y = 2;
            }
        }
    }


    private void placeFigure(FigureActor figureActor) {
        super.addActor(figureActor, fieldToLocation(figureActor.getFigure().getPosition()));
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        throw new FeatureNotImplementedYetException();
    }

    @Override
    public void hasWon() {
        //TODO popup instead
        System.out.println("You have won!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hasLost() {
        //TODO popup instead
        System.out.println("You have lost..");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isStalemate() {
        //TODO popup instead
        System.out.println("Game is stalemate.");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isDraw() {
        //TODO popup instead
        System.out.println("Game is draw.");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(Game game) {
        this.game = game;
        updateUI();
    }

    @Override
    public void nextTurn(Turn turn) {
        updateUI(turn);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class FigureActor extends Actor {
        private Figure figure;

        FigureActor(Figure figure, int cellsize) {
            super(getSpritePath(figure, cellsize));
            this.figure = figure;
        }

        private static String getSpritePath(Figure figure, int cellsize) {
            String size = (cellsize < 65)
                    ? "50_"
                    : (cellsize < 105)
                    ? "90_"
                    : "";
            String figureType = (figure instanceof Bishop)
                    ? "bishop"
                    : (figure instanceof King)
                    ? "king"
                    : (figure instanceof Knight)
                    ? "knight"
                    : (figure instanceof Pawn)
                    ? "pawn"
                    : (figure instanceof Queen)
                    ? "queen"
                    : (figure instanceof Rook)
                    ? "rook"
                    : "error";
            String color = (figure.getOwner().getColor() == 1)
                    ? "_white.png"
                    : "_black.png";
            return "sprites/" + size + figureType + color;
        }

        public Figure getFigure() {
            return figure;
        }
    }
}
