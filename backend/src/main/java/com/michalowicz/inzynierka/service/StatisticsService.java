package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dto.StatisticsData;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.populators.StatisticsPopulator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatisticsService {

    @Resource
    StatisticsPopulator statisticsPopulator;

    public StatisticsData getStatisticsForUser(User loggedUser ){
        return statisticsPopulator.populate(loggedUser);
    }
}
