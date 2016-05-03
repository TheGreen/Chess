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

package edu.gymneureut.informatik.rattenschach.control;

import edu.gymneureut.informatik.rattenschach.control.combination.JGGGUI;
import edu.gymneureut.informatik.rattenschach.control.combination.TerminalCombination;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.controller.RandomCaptureController;
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
            capture: if the random controller can capture something, it does that
             */
            case "benchmark":
                controllerOne = new RandomController();
                controllerTwo = new RandomController();
                observer = new TerminalObserver();
                break;
            case "benchmark_capture":
                controllerOne = new RandomCaptureController();
                controllerTwo = new RandomCaptureController();
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
                controllerOne = new RandomCaptureController();
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
                controllerOne = new RandomCaptureController();
                controllerTwo = new RandomCaptureController();
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
