package edu.gymneureut.informatik.rattenschach.control;

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
            Default:
            Play vs a random controller in Terminal
             */
            default:
                controllerOne = new RandomController();
                controllerTwo = new TerminalCombination();
                observer = (Observer) controllerTwo;
                break;

        }
        LinkedList<Observer> observers = new LinkedList<>();
        observers.add(observer);

        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) {
                new Game(controllerOne, controllerTwo, observers).play();
            } else {
                new Game(controllerTwo, controllerOne, observers).play();
            }
        }
    }
}
