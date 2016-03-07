package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.control.combination.TerminalCombination;
import edu.gymneureut.informatik.rattenschach.control.controller.RandomController;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Game;

import java.util.LinkedList;

/**
 * Created by green on 2/4/2016.
 */
class Main {
    public static void main(String[] args) {
        RandomController random = new RandomController();
        TerminalCombination combo = new TerminalCombination();
        LinkedList<Observer> three = new LinkedList<>();
        three.add(combo);

        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) {
                new Game(random, combo, three).play();
            } else {
                new Game(combo, random, three).play();
            }
            random.print();
        }
    }
}
