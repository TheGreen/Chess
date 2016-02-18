package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.model.Game;

import java.util.LinkedList;

/**
 * Created by green on 2/4/2016.
 */
public class Main {
    public static void main(String[] args) {
        RandomController one = new RandomController();
        RandomController two = new RandomController();
        LinkedList<Observer> three = new LinkedList<>();
        three.add(new TerminalObserver());

        for (int i = 0; i < 10000; i++) {
            if (i % 2 == 0) {
                new Game(one, two, three).play();
            } else {
                new Game(two, one, three).play();
            }
            one.print();
        }
    }
}
