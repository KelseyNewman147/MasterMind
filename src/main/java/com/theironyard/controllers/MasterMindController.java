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
            masterMind.guesses = new int[4];
            masterMind.guesses[0] = randomNumber();
            masterMind.guesses[1] = randomNumber();
            masterMind.guesses[2] = randomNumber();
            masterMind.guesses[3] = randomNumber();
            masterMind.checks = new int[4];
            //no guess from FE, so it will initially be blank
            masterMind.checks[0] = 0;
            masterMind.checks[1] = 0;
            masterMind.checks[2] = 0;
            masterMind.checks[3] = 0;
            games.save(masterMind);
        }
    }
    //while round <= 12, check guess against correct answer
    //if round > 12 end game and return correct answer
    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public MasterMindViewModel postGuess(@RequestBody int[] guess) {
        int [] answer = games.findByRound(1).getGuesses();
        MasterMind masterMind = new MasterMind();
        while (masterMind.getRound() <= 12) {
            masterMind.setGuesses(guess);
            masterMind.setChecks(checkGuess(guess, answer));
            games.save(masterMind);
        }
        //when round > 12 -> end game and return correct correct answer ?
        return new MasterMindViewModel((List)games.findAll());
    }
    //we take in their guess and compare it to randomly generated guess in spot one of our guess table
    //store that guess in our table
    //check against our correct answer through our checkGuess method
    //store the checks array that is generated through that method in the checks column of our table
    //return the checks array to FE

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public MasterMindViewModel homePage() {
        return new MasterMindViewModel((List)games.findAll());
    }

//    @CrossOrigin
//    @RequestMapping(path = "/", method = RequestMethod.POST)
//    public MasterMindViewModel homePage(@RequestBody MasterMind newGame) {
//
//        games.save(newGame);
//
//        return new MasterMindViewModel((List)games.findAll());
//    }


    public static int randomNumber() {
        return (int) ((Math.random() * 8) + 1);
    }

    //doesn't work if a repeated number has already generated a white peg, but matches in position later in the array
    //is there a way to check each for  matched position in first if stmt before moving on to the rest?
    //if so, would that trigger the noMatch (because matches are now zero) as we iterate through the rest of the array and subsequently set checks back to 0?
    public int[] checkGuess(int[] guess, int[] answer) {
        answer = Arrays.copyOf(answer, answer.length);

        int [] results = new int[answer.length];

        for (int i = 0; i < results.length; i ++) {
            if (answer[i] == guess[i]){
                results[i] = 2;
                answer[i] = 0;
                continue;
            }

            int answerIndex = Arrays.binarySearch(answer, guess[i]);

            if( answerIndex > -1) {
                results[i] = 1;
                answer[answerIndex] = 0;
            }
        }

    return results;
    }

}
