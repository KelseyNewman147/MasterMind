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
    @Autowired
    MasterMindRepository games;

    @PostConstruct
    public void init() {
        if (games.count() == 0) {
            MasterMind masterMind = new MasterMind();
            masterMind.setGuesses(new int[] {randomNumber(), randomNumber(), randomNumber(), randomNumber()});
            masterMind.setChecks( new int[] {0,0,0,0});
            //no guess from FE, so it will initially be blank
            games.save(masterMind);
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public MasterMindViewModel homePage() {
        return new MasterMindViewModel((List)games.findAll());
    }
    //while round <= 12, check guess against correct answer
    //if round > 12 end game and return correct answer
    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public MasterMindViewModel postGuess(@RequestBody int[] guess) {
        int [] answer = games.findByRound(1).getGuesses();
        MasterMind masterMind = new MasterMind();
            masterMind.setGuesses(guess);
            masterMind.setChecks(checkGuess(answer, guess));
            games.save(masterMind);
        return new MasterMindViewModel((List)games.findAll());
    }
    //we take in their guess and compare it to randomly generated guess in spot one of our guess table
    //store that guess in our table
    //check against our correct answer through our checkGuess method
    //store the checks array that is generated through that method in the checks column of our table
    //return the checks array to FE
    @CrossOrigin
    @RequestMapping(path = "/new-game", method = RequestMethod.POST)
    public String requestGame(){
        games.deleteAll();
        init();

        return "redirect:/";
    }


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
