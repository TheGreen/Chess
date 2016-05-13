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
import edu.gymneureut.informatik.rattenschach.control.controller.*;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserver;
import edu.gymneureut.informatik.rattenschach.control.observer.TerminalObserverSlim;
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
        if (args.length == 0) {
            args = new String[1];
            args[0] = "default";
        }

        LinkedList<Observer> observers = new LinkedList<>();

        switch (args[0]) {
            /*
            Benchmark:
            Lets two random controllers play against each other.
            capture: if the random controller can capture something, it does that
             */
            case "benchmark":
                controllerOne = new RandomController();
                controllerTwo = new RandomController();
                observers.add(new TerminalObserver());
                break;
            case "benchmark_capture":
                controllerOne = new RandomCaptureController();
                controllerTwo = new RandomCaptureController();
                observers.add(new TerminalObserver());
                break;
            /*
            JGGGUI:
                    ****WORK IN PROGRESS****

            Play vs a random capture controller in a GUI

                    ****WORK IN PROGRESS****
            */
            case "jgggui":
                controllerOne = new RandomCaptureController();
                controllerTwo = new JGGGUI(80);
                observers.add((JGGGUI) controllerTwo);
                break;
            /*
            JGGGUI Benchmark:
            Two random capture controllers in a GUI
            */
            case "jgggui_benchmark":
                controllerOne = new RandomCaptureController();
                controllerTwo = new RandomCaptureController();
                observers.add(new JGGGUI(80));
                observers.add(new TerminalObserverSlim());
                break;
            /*
            JGGGUI Size Test:
            Just a test for testing new sizing options.
            */
            case "jgggui_sizetest":
                controllerOne = new RandomCaptureController();
                controllerTwo = new RandomCaptureController();
                for (int i = 20; i < 150; i += 10) {
                    observers.add(new JGGGUI(i));
                }
                observers.add(new TerminalObserverSlim());
                break;
            /*
            JGGGUI Size Test:
            Just a test for testing new sizing options.
            */
            case "jgggui_draw_test":
                controllerOne = new DrawOfferer();
                controllerTwo = new JGGGUI(80);
                observers.add(new TerminalObserverSlim());
                break;
            /*
            JGGGUI Terminal Test:
            Two random capture controllers in a GUI
            */
            case "jgggui_terminal_test":
                controllerOne = new RandomController();
                controllerTwo = new TerminalController();
                observers.add(new TerminalObserverSlim());
                observers.add(new JGGGUI(80));
                break;
            /*
            Test:
            Play vs a random controller in Terminal1

             */
            case "test":
                controllerOne = new RandomController();
                controllerTwo = new TerminalCombination();
                observers.add((Observer) controllerTwo);
                break;
            /*
            Default:
            Play vs a random capture controller in Terminal
             */
            default:
                controllerOne = new RandomCaptureController();
                controllerTwo = new TerminalCombination();
                observers.add((Observer) controllerTwo);
                break;

        }

        Game game = new Game(controllerOne, controllerTwo, observers);
        for (Observer obs : observers) {
            obs.startGame(game);
        }
        game.play();
    }
}
