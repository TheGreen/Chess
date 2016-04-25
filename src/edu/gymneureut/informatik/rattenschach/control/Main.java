package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.control.combination.TerminalCombination;
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
        RandomController randomOne = new RandomController();
        RandomController randomTwo = new RandomController();
        TerminalObserver observer = new TerminalObserver();
        LinkedList<Observer> three = new LinkedList<>();
        three.add(observer);

        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) {
                new Game(randomOne, randomTwo, three).play();
            } else {
                new Game(randomTwo, randomOne, three).play();
            }
            randomOne.print();
        }
    }
}
