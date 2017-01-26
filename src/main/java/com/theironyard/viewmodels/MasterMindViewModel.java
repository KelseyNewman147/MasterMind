package com.theironyard.viewmodels;

import com.theironyard.entities.MasterMind;

import java.util.List;

/**
 * Created by kelseynewman on 1/23/17.
 */
public class MasterMindViewModel {
    private static int STATIC_ID = 0;

    private List<MasterMind> games;
    private int id = 0;

    public MasterMindViewModel() {
        id = STATIC_ID++;
        //keeps track of the number of times your object has been instantiated
    }

    public MasterMindViewModel(List<MasterMind> games) {
        this();
        this.games = games;
    }

    public static int getStaticId() {
        return STATIC_ID;
    }

    public static void setStaticId(int staticId) {
        STATIC_ID = staticId;
    }

    public List<MasterMind> getGames() {
        return games;
    }

    public void setGames(List<MasterMind> games) {
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
