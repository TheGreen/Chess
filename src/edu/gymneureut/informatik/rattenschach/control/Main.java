package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI.JGGGUI;
import edu.gymneureut.informatik.rattenschach.control.combination.TerminalCombination;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.controller.RandomController;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserver;
import edu.gymneureut.informatik.rattenschach.model.Game;

import java.util.LinkedList;

/**
 * The <tt>Main</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
class Main {
    public static void main(String[] args) {
        Controller controllerOne;
        Controller controllerTwo;
        Observer observer;
        if (args.length == 0) {
            args = new String[1];
            args[0] = "default";
        }
        switch (args[0]) {
            /*
            Benchmark:
            Lets two random controllers play against each other.
             */
            case "benchmark":
                controllerOne = new RandomController();
                controllerTwo = new RandomController();
                observer = new TerminalObserver();
                break;
            /*
            JGGGUI:

            ****WORK IN PROGRESS****
            *
            Play vs a random capture controller in a GUI

                    ****WORK IN PROGRESS****
            */
            case "jgggui":
                controllerOne = new RandomController();
                controllerTwo = new JGGGUI();
                observer = (JGGGUI) controllerTwo;
                break;
            /*
            JGGGUI:

            ****WORK IN PROGRESS****
            *
            Play vs a random capture controller in a GUI

                    ****WORK IN PROGRESS****
            */
            case "jgggui_benchmark":
                controllerOne = new RandomController();
                controllerTwo = new RandomController();
                observer = new JGGGUI();
                break;
            /*
            Default:
            Play vs a random capture controller in Terminal
             */
            default:
                controllerOne = new RandomController();
                controllerTwo = new TerminalCombination();
                observer = (Observer) controllerTwo;
                break;

        }
        LinkedList<Observer> observers = new LinkedList<>();
        observers.add(observer);

        Game game;
        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) {
                game = new Game(controllerOne, controllerTwo, observers);
                for (Observer obs : observers) {
                    observer.startGame(game);
                }
                game.play();
            } else {
                game = new Game(controllerTwo, controllerOne, observers);
                observer.startGame(game);
                game.play();
            }
        }
    }
}
