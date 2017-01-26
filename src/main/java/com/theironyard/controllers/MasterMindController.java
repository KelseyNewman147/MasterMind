package com.theironyard.controllers;

import com.theironyard.viewmodels.MasterMindViewModel;
import com.theironyard.entities.MasterMind;
import com.theironyard.services.MasterMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kelseynewman on 1/20/17.
 */

@RestController
public class MasterMindController {
    int [] answer = new int[4]; // set answer each game
    boolean flag = true; // to create new game

    @Autowired
    MasterMindRepository games;

    @PostConstruct
    public void init() {
        if (flag) {
            games.deleteAll();
            answer = new int[] {randomNumber(), randomNumber(), randomNumber(), randomNumber()};
            flag = false;
        }
    }

    //while round <= 12, check guess against correct answer
    //if round > 12 end game and return correct answer

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public MasterMindViewModel postGuess(@RequestBody int[] guess) {
        init();
        MasterMind masterMind = new MasterMind();
        MasterMindViewModel model = new MasterMindViewModel();
        masterMind.setRound(model.getId());
        masterMind.setGuesses(guess); // sets their guess
        masterMind.setChecks(checkGuess(answer, guess)); //this compares answer with guess and returns checks array

            if (Arrays.equals(masterMind.getGuesses(), answer) || (model.getId() == 12)) { // round is less or equal to 12 DO DIS) {
                games.save(masterMind);
                MasterMind answerMind = new MasterMind();
                answerMind.setGuesses(answer);
                answerMind.setChecks(checkGuess(answer, guess));
                games.save(answerMind);
                MasterMindViewModel.setStaticId(0);
                flag = true;
            }else {
                games.save(masterMind);
            }
        model.setGames((List)games.findAll());
        return model;
    }
    //we take in their guess and compare it to randomly generated guess in spot one of our guess table
    //store that guess in our table
    //check against our correct answer through our checkGuess method
    //store the checks array that is generated through that method in the checks column of our table
    //return the checks array to FE

    public static int randomNumber() {
        return (int) ((Math.random() * 8) + 1);
    }

    public static int[] checkGuess(int[] answer, int[] guess) {
        answer = Arrays.copyOf(answer, answer.length);
        guess = Arrays.copyOf(guess, guess.length);

        int [] results = new int[answer.length];


        for (int i = 0; i < results.length; i ++) {
            if (answer[i] == guess[i]) {
                results[i] = 2;
                answer[i] = 0;
                guess[i] = 0;
            }
        }

        for (int i = 0; i < results.length; i ++) {
            int answerIndex = findIndexOfValue(answer, guess[i]);

            if (answerIndex > -1 && answer[answerIndex] > 0) {
                results[i] = 1;
                answer[answerIndex] = 0;
            }
        }

    return results;
    }

    public static int findIndexOfValue(int [] array, int value) {
        for (int i = 0;i < array.length;i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

}
