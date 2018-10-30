package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.dao.TournamentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TournamentService {
    @Resource
    TournamentDao tournamentDao;

    public List<Tournament> getAllTournaments(){
        return tournamentDao.findAll();
    }
}
