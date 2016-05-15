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

import ch.aplu.jgamegrid.*;
import ch.aplu.util.InputDialog;
import ch.aplu.util.MessagePane;
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
 * It provides the ability to control and observe a chess game in a graphical user interface.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public class JGGGUI extends GameGrid implements Controller, Observer, GGMouseListener {
    private Game game;
    private int turncounter;
    private int cellsize;
    private boolean isMyTurn = false;
    private boolean isTurnSelected = false;
    private boolean turnActorSelected = false;
    private List<Turn> turns;
    private Figure figure;
    private Turn turn;



    public JGGGUI(int cellsize) {
        super(16, 12, cellsize, false);
        this.cellsize = cellsize;
        this.setTitle("Rattenschach");

        addMouseListener(this, GGMouse.lClick);
        addMouseListener(this, GGMouse.rClick);


        drawBackground();
        drawChessboard();
        drawCaptured();
        drawStaticText();

        this.show();
    }

    public JGGGUI() {
        this(80);
    }

    public static void main(String[] args) {
        new JGGGUI(110).show();
    }

    static String getSpritePath(Figure figure, int cellsize) {
        return getSpritePath(figure, cellsize, (figure.getOwner().getColor()));
    }

    static String getSpritePath(Figure figure, int cellsize, int colorID) {
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
        String color = (colorID == 1)
                ? "_white.png"
                : "_black.png";
        return "sprites/" + size + figureType + color;
    }

    @Override
    public void act() {
        super.act();
        if (game != null && game.getTimer() != null) {
            drawText();
        }
    }

    private void drawText() {
        cleanTextArea();
        getBg().setFont(new Font("Monospaced", Font.PLAIN, 26));
        getBg().drawText("" + nanosecondsToReadableTime(game.getTimer().getRemainingTimeWhite()), new Point(30, 125));
        getBg().drawText("" + nanosecondsToReadableTime(game.getTimer().getRemainingTimeBlack()), new Point(990, 125));
        getBg().setFont(new Font("Monospaced", Font.PLAIN, 72));
        getBg().drawText(String.valueOf(turncounter), new Point(730, 100));
    }

    private void cleanTextArea() {
        drawBackgroundTile(0, 1);
        drawBackgroundTile(1, 1);
        drawBackgroundTile(2, 1);
        drawBackgroundTile(3, 1);

        drawBackgroundTile(12, 1);
        drawBackgroundTile(13, 1);
        drawBackgroundTile(14, 1);
        drawBackgroundTile(15, 1);

        drawBackgroundTile(9, 0);
        drawBackgroundTile(10, 0);
        drawBackgroundTile(11, 0);

        drawBackgroundTile(9, 1);
        drawBackgroundTile(10, 1);
        drawBackgroundTile(11, 1);
    }

    private void drawStaticText() {
        getBg().setFont(new Font("Monospaced", Font.PLAIN, 26));
        getBg().drawText("Time Left White:", new Point(30, 60));
        getBg().drawText("Time Left Black:", new Point(990, 60));
        getBg().setFont(new Font("Monospaced", Font.PLAIN, 72));
        getBg().drawText("Turn:", new Point(480, 100));
    }

    private String nanosecondsToReadableTime(long nanoseconds) {
        int seconds = (int) (nanoseconds / 1000000000L);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return "" + minutes + " min " + seconds + " sec";
    }

    @Override
    public boolean mouseEvent(GGMouse mouse) {
        act();
        //normal stuff:
        if (mouse.getEvent() == GGMouse.lClick) {
            System.out.println("Left click at: " + toLocationInGrid(mouse.getX(), mouse.getY()).toString());
            return handleLeftClick(mouse);

        }

        //right click:
        if (mouse.getEvent() == GGMouse.rClick) {
            System.out.println("Right click at: " + toLocationInGrid(mouse.getX(), mouse.getY()).toString());
            return handleRightClick(mouse);
        }
        return false;
    }

    private boolean handleRightClick(GGMouse mouse) {
        Location location = toLocationInGrid(mouse.getX(), mouse.getY());

        List<Actor> actors = getActorsAt(location);
        for (Actor actor : actors) {
            if (turnActorSelected && actor instanceof PromotionActor) {
                ((PromotionActor) actor).switchPromotions();
            }
            if (actor instanceof FigureActor
                    && ((FigureActor) actor).getFigure() instanceof King) {
                if (new InputDialog("Offer Draw", "Do you want to offer Draw?").readBoolean()) {
                    for (Turn turn : turns) {
                        if (turn instanceof DrawNotification
                                && ((DrawNotification) turn).getDrawType() == DrawNotification.DrawType.offers) {
                            this.turn = turn;
                            synchronized (this) {
                                isTurnSelected = true;
                                notifyAll();
                            }
                        }
                    }
                }

            }
        }
        return false;
    /*
     * TODO:
     * Right click king opens offer draw dialogue
     */
    }

    private boolean handleLeftClick(GGMouse mouse) {
        //Check if it is my turn
        if (!isMyTurn) return false;

        //Check if there is no actor selected yet
        if (!turnActorSelected) {
            selectActor(toLocationInGrid(mouse.getX(), mouse.getY()));
            return false;
        } else {
            selectTurn(toLocationInGrid(mouse.getX(), mouse.getY()));
            return false;
        }
    }

    private void selectActor(Location location) {
        //Get actors at the location
        List<Actor> actors = getActorsAt(location);

        FigureActor figureActor = null;

        while (!turnActorSelected) {
            //Check that actors is not empty
            if (actors.size() == 0) return;

            //Get first actor
            Actor actor = actors.remove(0);

            if (actor instanceof FigureActor) {
                turnActorSelected = true;
                figureActor = (FigureActor) actor;
            }
        }

        //Gets Figure from the Actor
        figure = figureActor.getFigure();

        //Colorize the Field where the Actor is standing on
        drawSelectedChessTile(location.getX(), location.getY(), 2);

        //Generates List of figure turns with the given figure and turns
        List<Turn> figureTurns = Controller.getTurnsForFigure(figure, turns);

        //Add Turn Actors
        addTurnActors(figureTurns);
    }

    private void addTurnActors(List<Turn> figureTurns) {
        for (Turn turn : figureTurns) {
            addTurnActor(turn);
        }
    }

    private void addTurnActor(Turn turn) {
        if (turn instanceof Promotion) {
            addTurnActor((Promotion) turn);
        } else if (turn instanceof Move) {
            addTurnActor((Move) turn);
        } else if (turn instanceof Castling) {
            addTurnActor((Castling) turn);
        }
    }

    private void addTurnActor(Promotion promotion) {
        int selectType = (promotion.getCaptures()) ? 0 : 1;
        int x = fieldToLocation(promotion.getDestination()).getX();
        int y = fieldToLocation(promotion.getDestination()).getY();
        drawSelectedChessTile(x, y, selectType);

        List<Actor> actors = getActorsAt(fieldToLocation(promotion.getDestination()));
        PromotionActor actor = null;
        for (Actor actor_ : actors) {
            if (actor_ instanceof PromotionActor) {
                actor = (PromotionActor) actor_;
            }
        }

        if (actor == null) {
            actor = new PromotionActor(figure.getOwner().getColor());
            addActor(actor, fieldToLocation(promotion.getDestination()));
        }

        actor.addPromotion(promotion);
    }

    private void addTurnActor(Move move) {
        int selectType = (move.getCaptures()) ? 0 : 1;
        int x = fieldToLocation(move.getDestination()).getX();
        int y = fieldToLocation(move.getDestination()).getY();
        drawSelectedChessTile(x, y, selectType);

        MoveActor actor = new MoveActor(move);
        addActor(actor, fieldToLocation(move.getDestination()));
    }

    private void addTurnActor(Castling castling) {
        int selectType = 1;
        int x = fieldToLocation(castling.getKingDestination()).getX();
        int y = fieldToLocation(castling.getKingDestination()).getY();

        drawSelectedChessTile(x, y, selectType);

        CastlingActor actor = new CastlingActor(castling);
        addActor(actor, fieldToLocation(castling.getKingDestination()));
    }

    private void selectTurn(Location location) {
        List<Actor> actors = getActorsAt(location);
        for (Actor actor : actors) {
            if (actor instanceof MoveActor) {
                turn = ((MoveActor) actor).getMove();
                break;
            } else if (actor instanceof PromotionActor) {
                turn = ((PromotionActor) actor).getPromotion();
                break;
            } else if (actor instanceof CastlingActor) {
                turn = ((CastlingActor) actor).getCastling();
                break;
            }
        }

        if (turn == null) {
            turnActorSelected = false;
            removeTurnActors();
            drawChessboard();
            return;
        }

        //After the turn has been selected:
        synchronized (this) {
            isTurnSelected = true;
            notifyAll();
        }
    }

    @Override
    public Turn pickMove(Map<Field, Figure> field, List<Turn> turns) {
        if (turns.size() == 2
                && turns.get(0) instanceof DrawNotification
                && ((DrawNotification) turns.get(0)).getDrawType()
                != DrawNotification.DrawType.offers) {
            if (new InputDialog("Accept Draw?", "Your opponent offers you a draw, do you want to accept?").readBoolean()) {
                return turns.stream().filter(turn ->
                        turn instanceof DrawNotification
                                && ((DrawNotification) turn).getDrawType()
                                == DrawNotification.DrawType.accepts)
                        .findFirst().get();
            }
        }

//        throw new FeatureNotImplementedYetException();
        isTurnSelected = false;
        turn = null;
        this.turns = turns;
        isMyTurn = true;

        synchronized (this) {
            while (!isTurnSelected) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        isMyTurn = false;
        turnActorSelected = false;
        drawChessboard();
        removeTurnActors();
        return turn;
    }

    private void removeTurnActors() {
        for (Actor actor : getActors()) {
            if (actor instanceof MoveActor
                    || actor instanceof PromotionActor
                    || actor instanceof CastlingActor) {
                removeActor(actor);
            }
        }
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

    private void drawBackgroundTile(int i, int j) {
        drawTile(new Location(i, j), new Color(0x65, 0x3E, 0x19));
    }

    private void drawChessTile(int i, int j) {
        if ((i + j) % 2 == 1) {
            drawTile(new Location(i, j), new Color(0xFF, 0xCE, 0x9E));
        } else {
            drawTile(new Location(i, j), new Color(0xD1, 0x8B, 0x47));
        }
    }

    private void drawSelectedChessTile(int i, int j, int type) {
        switch (type) {
            case 0: //Red
                if ((i + j) % 2 == 1) {
                    drawTile(new Location(i, j), new Color(0xFF, 0xB6, 0xB6));
                } else {
                    drawTile(new Location(i, j), new Color(0xD1, 0x69, 0x69));
                }
                break;
            case 1: //Green
                if ((i + j) % 2 == 1) {
                    drawTile(new Location(i, j), new Color(0xB6, 0xFF, 0xB6));
                } else {
                    drawTile(new Location(i, j), new Color(0x69, 0xD1, 0x69));
                }
                break;
            case 2: //Blue
                if ((i + j) % 2 == 1) {
                    drawTile(new Location(i, j), new Color(0xB6, 0xB6, 0xFF));
                } else {
                    drawTile(new Location(i, j), new Color(0x69, 0x69, 0xD1));
                }
                break;
            default: //Green
                if ((i + j) % 2 == 1) {
                    drawTile(new Location(i, j), new Color(0xB6, 0xFF, 0xB6));
                } else {
                    drawTile(new Location(i, j), new Color(0x69, 0xD1, 0x69));
                }
                break;
        }
    }

    private void drawTile(Location location, Color color) {
        super.getBg().fillCell(location, color);
        refresh();
    }

    private void drawBackground() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                drawBackgroundTile(i, j);
            }
        }
    }

    private void drawSelectedChessTile(int i, int j) {
        drawSelectedChessTile(i, j, 1);
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
    public void hasWon() {
        new MessagePane("You have won!");
    }

    @Override
    public void hasLost() {
        new MessagePane("You have lost..");
    }

    @Override
    public void isStalemate() {
        new MessagePane("Game is stalemate.");
    }

    @Override
    public void isDraw() {
        new MessagePane("Game is draw.");
    }

    @Override
    public void startGame(Game game) {
        this.game = game;
        updateUI();
    }

    @Override
    public void nextTurn(Turn turn) {
        turncounter++;
        updateUI(turn);
        act();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class FigureActor extends Actor {
        private Figure figure;

        FigureActor(Figure figure, int cellsize) {
            super(getSpritePath(figure, cellsize));
            this.figure = figure;
        }

        public Figure getFigure() {
            return figure;
        }
    }

    private class MoveActor extends Actor {
        private Move move;

        MoveActor(Move move) {
            super();
            this.move = move;
        }

        public Move getMove() {
            return move;
        }
    }

    private class PromotionActor extends Actor {
        private List<Promotion> promotions;

        PromotionActor(int color) {
            super(getSpritePath(new Queen(), cellsize, color),
                    getSpritePath(new Rook(), cellsize, color),
                    getSpritePath(new Knight(), cellsize, color),
                    getSpritePath(new Bishop(), cellsize, color));
            this.promotions = new LinkedList<>();
        }

        public void addPromotion(Promotion promotion) {
            this.promotions.add(promotion);
        }

        public void switchPromotions() {
            promotions.add(promotions.remove(0));
            showNextSprite();
            refresh();
        }

        public Promotion getPromotion() {
            return promotions.get(0);
        }
    }

    private class CastlingActor extends Actor {
        private Castling castling;

        CastlingActor(Castling castling) {
            super();
            this.castling = castling;
        }

        public Castling getCastling() {
            return castling;
        }
    }
}
