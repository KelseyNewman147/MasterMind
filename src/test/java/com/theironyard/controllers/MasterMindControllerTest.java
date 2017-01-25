package com.theironyard.controllers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kelseynewman on 1/22/17.
 */
public class MasterMindControllerTest {
    @Test
    public void checkGuess() throws Exception {
        assertArrayEquals(new int[]{1, 2, 0, 1}, MasterMindController.checkGuess(
                new int[] {1, 2, 3, 4},
                new int[] {4, 2, 2, 1}));
        assertArrayEquals(new int[]{2, 1, 0, 0}, MasterMindController.checkGuess(new int[] {1, 2, 3, 4}, new int[] {1, 3, 5, 7}));
        assertArrayEquals(new int[]{0, 0, 0, 2}, MasterMindController.checkGuess(new int[] {1, 2, 3, 4}, new int[] {5, 6, 7, 4}));
        assertArrayEquals(new int[]{0, 0, 0, 2}, MasterMindController.checkGuess(new int[] {1, 2, 3, 4}, new int[] {4, 4, 4, 4}));
        assertArrayEquals(new int[]{2, 2, 0, 2}, MasterMindController.checkGuess(new int[] {8, 6, 4, 4}, new int[] {8, 6, 1, 4}));
          }

}