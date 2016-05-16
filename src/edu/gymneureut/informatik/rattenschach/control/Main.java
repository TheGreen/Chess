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
import edu.gymneureut.informatik.rattenschach.control.controller.AlwaysFirst;
import edu.gymneureut.informatik.rattenschach.control.controller.Cheater;
import edu.gymneureut.informatik.rattenschach.control.controller.Controller;
import edu.gymneureut.informatik.rattenschach.control.controller.RandomCaptureController;
import edu.gymneureut.informatik.rattenschach.control.observer.Observer;
import edu.gymneureut.informatik.rattenschach.model.Game;

import java.util.LinkedList;

/**
 * The <tt>Main</tt> class.
 * Add game modes by adding cases to the switch statement.
 * Such a case in this statement has to set both controllers and should add at least one controller.
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
            args[0] = "jgggui";
        }

        LinkedList<Observer> observers = new LinkedList<>();

        switch (args[0]) {
//            Template for new Game mode
//            case "":
//                controllerOne = new ;
//                controllerTwo = ;
//                observers.add();
//                break;
            /*
            A special challenge just for you.
             */
            case "cheater":
                controllerOne = new Cheater();
                controllerTwo = new JGGGUI();
                observers.add((JGGGUI) controllerTwo);
                break;
            /*
            Learn here first.
             */
            case "observe_cheater":
                controllerOne = new RandomCaptureController();
                controllerTwo = new Cheater();
                observers.add(new JGGGUI());
                break;
            /*
            Originally just a test case, but fun to look at.
             */
            case "first":
                controllerOne = new AlwaysFirst();
                controllerTwo = new JGGGUI();
                observers.add((JGGGUI) controllerTwo);
                break;
            /*
            Play in the Terminal
             */
            case "terminal":
                controllerOne = new RandomCaptureController();
                controllerTwo = new TerminalCombination();
                observers.add((TerminalCombination) controllerTwo);
                break;
            /*
             1v1 in GUI
             */
            case "dual":
                controllerOne = new JGGGUI();
                controllerTwo = controllerOne;
                observers.add((JGGGUI) controllerOne);
                break;
            /*
            1vAI in GUI
             */
            default:
                controllerOne = new RandomCaptureController();
                controllerTwo = new JGGGUI();
                observers.add((JGGGUI) controllerTwo);
                break;

//            The following commented-out code are mostly old test cases we used for finding bugs.

//            /*
//            Benchmark:
//            Lets two random controllers play against each other.
//            capture: if the random controller can capture something, it does that
//             */
//            case "benchmark":
//                controllerOne = new RandomController();
//                controllerTwo = new RandomController();
//                observers.add(new TerminalObserver());
//                break;
//            case "benchmark_capture":
//                controllerOne = new RandomCaptureController();
//                controllerTwo = new RandomCaptureController();
//                observers.add(new TerminalObserver());
//                break;
//            /*
//            JGGGUI:
//                    ****WORK IN PROGRESS****
//
//            Play vs a random capture controller in a GUI
//
//                    ****WORK IN PROGRESS****
//            */
//            case "jgggui":
//                controllerOne = new RandomCaptureController();
//                controllerTwo = new JGGGUI(80);
//                observers.add((JGGGUI) controllerTwo);
//                break;
//            /*
//            JGGGUI Benchmark:
//            Two random capture controllers in a GUI
//            */
//            case "jgggui_benchmark":
//                controllerOne = new RandomCaptureController();
//                controllerTwo = new RandomCaptureController();
//                observers.add(new JGGGUI(80));
//                observers.add(new TerminalObserverSlim());
//                break;
//            /*
//            JGGGUI Size Test:
//            Just a test for testing new sizing options.
//            */
//            case "jgggui_sizetest":
//                controllerOne = new RandomCaptureController();
//                controllerTwo = new RandomCaptureController();
//                for (int i = 20; i < 150; i += 10) {
//                    observers.add(new JGGGUI(i));
//                }
//                observers.add(new TerminalObserverSlim());
//                break;
//            /*
//            JGGGUI Size Test:
//            Just a test for testing new sizing options.
//            */
//            case "jgggui_draw_test":
//                controllerOne = new DrawOfferer();
//                controllerTwo = new JGGGUI(80);
//                observers.add(new TerminalObserverSlim());
//                break;
//            /*
//            JGGGUI Terminal Test:
//            Two random capture controllers in a GUI
//            */
//            case "jgggui_terminal_test":
//                controllerOne = new RandomController();
//                controllerTwo = new TerminalController();
//                observers.add(new TerminalObserverSlim());
//                observers.add(new JGGGUI(80));
//                break;
//            /*
//            Test:
//            Play vs a random controller in Terminal1
//
//             */
//            case "test":
//                controllerOne = new RandomController();
//                controllerTwo = new TerminalCombination();
//                observers.add((Observer) controllerTwo);
//                break;
//            /*
//            Default:
//            Play vs a random capture controller in Terminal
//             */
//            default:
//                controllerOne = new RandomCaptureController();
//                controllerTwo = new TerminalCombination();
//                observers.add((Observer) controllerTwo);
//                break;
        }

        Game game = new Game(controllerOne, controllerTwo, observers);
        for (Observer obs : observers) {
            obs.startGame(game);
        }
        game.play();
    }
}
