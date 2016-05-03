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

package edu.gymneureut.informatik.rattenschach.model;

/**
 * Created by jcgruenhage on 4/30/16.
 */
public class Timer {
    private long timeWhite;
    private long timeBlack;
    private long increment;
    private long time;
    private boolean playerWhite;

    Timer(long time, long increment) {
        this.timeWhite = time;
        this.timeBlack = time;
        this.increment = increment;
    }

    void startGame() {
        time = System.nanoTime();
        playerWhite = true;
    }

    public long getRemainingTimeWhite() {
        return timeWhite - (System.nanoTime() - time) + increment;
    }

    public long getRemainingTimeBlack() {
        return timeBlack - (System.nanoTime() - time) + increment;
    }

    /**
     * Determines, how much time has passed since the last time, the method was called or the game started.
     *
     * @param player the player, whichs turn should bne finished.
     * @return returns true, if the player still has time left.
     */
    boolean switchPlayer(Player player) {
        if (player.getColor() == 1) {
            if (playerWhite) {
                long tempTime = System.nanoTime();
                timeWhite = timeWhite - (tempTime - time) + increment;
                time = tempTime;
                playerWhite = !playerWhite;
                return timeWhite > 0;
            } else {
                throw new IllegalStateException();
            }
        } else {
            if (!playerWhite) {
                long tempTime = System.nanoTime();
                timeBlack = timeBlack - (tempTime - time);
                time = tempTime;
                playerWhite = !playerWhite;
                return timeBlack > 0;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
