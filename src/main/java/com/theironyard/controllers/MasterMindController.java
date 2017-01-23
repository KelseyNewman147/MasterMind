package com.theironyard.controllers;

import com.theironyard.viewmodels.MasterMindViewModel;
import com.theironyard.entities.MasterMind;
import com.theironyard.services.MasterMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
        MasterMind masterMind = new MasterMind();
        while (masterMind.getRound() <= 12) {
            masterMind.setGuesses(guess);
            masterMind.setChecks(checkGuess(guess));
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
    public int[] checkGuess(int[] guess) {
        int red = 0;
        int white = 0;
        int noMatch = 0;
        int[] checks = new int[2];
        int[] correctAnswer = games.findByRound(1).guesses;
        for (int i = 0; i < guess.length; i++) {
            for (int j = 0; j < correctAnswer.length; j++) {
                if (guess[i] == correctAnswer[i]) {
                    red++;
                    correctAnswer[i] = 0;
                    //changes element that was matched to 0 so that repeated numbers in guess aren't checked against same number in correctAnswer
                    break;
                } else if (guess[i] == correctAnswer[0]) {
                    white++;
                    correctAnswer[0] = 0;
                    break;
                } else if (guess[i] == correctAnswer[1]) {
                    white++;
                    correctAnswer[1] = 0;
                    break;
                } else if (guess[i] == correctAnswer[2]) {
                    white++;
                    correctAnswer[2] = 0;
                    break;
                } else if (guess[i] == correctAnswer[3]) {
                    white++;
                    correctAnswer[3] = 0;
                    break;

                }
            }
        }
        return checks;
    }

    public void checkAgainstAnswer(int [] checks, int [] answers){}

}
