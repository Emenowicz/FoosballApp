package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.TournamentModel;
import com.michalowicz.inzynierka.repository.TournamentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TournamentService {
    @Resource
    TournamentDao tournamentDao;

    public List<TournamentModel> getAllTournaments(){
        return tournamentDao.findAll();
    }
}
