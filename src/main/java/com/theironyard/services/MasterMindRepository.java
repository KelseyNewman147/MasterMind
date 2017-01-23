package com.theironyard.services;

import com.theironyard.entities.MasterMind;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kelseynewman on 1/20/17.
 */
public interface MasterMindRepository extends CrudRepository<MasterMind, Integer> {
    MasterMind findByRound(int round);
}
