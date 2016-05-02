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
