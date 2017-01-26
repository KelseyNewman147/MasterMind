package com.theironyard.entities;

import javax.persistence.*;
import java.lang.reflect.Array;

/**
 * Created by kelseynewman on 1/20/17.
 */
@Entity
@Table(name = "games")
public class MasterMind {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    public
    int round;

    @Column (nullable = false)
    public
    int [] guesses;

    @Column (nullable = false)
    public
    int [] checks;

    public MasterMind() {
    }

    public MasterMind(int round, int[] guesses, int[] checks) {
        this.round = round;
        this.guesses = guesses;
        this.checks = checks;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int[] getGuesses() {
        return guesses;
    }

    public void setGuesses(int[] guesses) {
        this.guesses = guesses;
    }

    public int[] getChecks() {
        return checks;
    }

    public void setChecks(int[] checks) {
        this.checks = checks;
    }
}
