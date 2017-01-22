package com.theironyard.controllers;

import com.theironyard.entities.MasterMind;
import com.theironyard.services.MasterMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;

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
            int a = randomNumber();
            masterMind.guesses = new int[4];
            masterMind.guesses[0] = a;
            masterMind.guesses[1] = a;
            masterMind.guesses[2] = a;
            masterMind.guesses[3] = a;
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

    @RequestMapping(path = "/guess", method = RequestMethod.POST)
    //we take in their guess and compare it to randomly generated guess in spot one of our guess table
    //store that guess in our table
    //check against our correct answer through our checkGuess method
    //store the checks array that is generated through that method in the checks column of our table
    //return the checks array to FE


//    @RequestMapping(path = "/answer", method = RequestMethod.GET)


    public static int randomNumber() {
        return (int)((Math.random()* 8) + 1);
    }

    public int [] checkGuess(int [] guess) {
        int red = 2;
        int white = 1;
        int noMatch = 0;
        int [] checks = new int [4];
        int [] correctAnswer = games.findById(1).guesses;
        for (int i = 0; i < guess.length; i ++) {
            for (int j = 0; j < correctAnswer.length; j++) {
                if (guess[i] == correctAnswer[j]) {
                    checks[i] = red;
                    //take j out of the array that is being compared so that repeated numbers in guess aren't checked against same number in correctAnswer
                    Arrays.asList(correctAnswer).remove(correctAnswer[j]);
                    break;
                } else if (Arrays.asList(correctAnswer).contains(guess[i])) {
                    checks[i] = white;
                    Arrays.asList(correctAnswer).remove(correctAnswer[j]==guess[i]);
                    break;
                } else {
                    checks[i] = noMatch;
                    break;
                }
            }
        }
        return checks;
    }

}
