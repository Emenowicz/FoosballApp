package com.michalowicz.inzynierka.populators;

import com.michalowicz.inzynierka.dto.StatisticsData;
import com.michalowicz.inzynierka.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StatisticsPopulatorTest {

    @Mock
    User user;

    @Test
    public void ShouldPopulateEmptyStatistics() {
//        given
        StatisticsPopulator statisticsPopulator = new StatisticsPopulator();
//        when
        StatisticsData result = statisticsPopulator.populate(user);
        assertEquals(0, result.getMatchesPlayed());
        assertEquals(0, result.getMatchesWon());
        assertEquals(0, result.getRoundsWon());
        assertEquals(0.0, result.getMatchWinRatio(), 0.1);
        assertEquals("Brak współgraczy w historii", result.getMostPlayedWith());
        assertEquals(0, result.getPointsScored());
        assertEquals(0, result.getRoundsPlayed());
        assertEquals(0, result.getTournamentsPlayed());
        assertEquals(0, result.getTournamentsWon());

    }
}