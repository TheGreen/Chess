package edu.gymneureut.informatik.rattenschach;

/**
 * Created by green on 2/4/2016.
 */
public class Turn {
    private Move move;
    private TurnStatus status;

    public Turn(Move move, TurnStatus status) {
        this.move = move;
        this.status = status;
    }

    public TurnStatus getStatus() {
        return status;
    }

    public Move getMove() {
        return move;
    }

    public enum TurnStatus {
        hasLost, isPatt, offersRemis, acceptsRemis, deniesRemis, normallyRunning
    }
}
