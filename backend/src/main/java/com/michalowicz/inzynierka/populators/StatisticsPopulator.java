package com.michalowicz.inzynierka.populators;

import com.michalowicz.inzynierka.dto.StatisticsData;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.TournamentStatus;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StatisticsPopulator {
    public StatisticsData populate(User user) {
        StatisticsData stats = new StatisticsData();
        stats.setTournamentsPlayed((int) user.getJoinedTournaments()
                .stream()
                .filter(tournament -> !tournament.getStatus().equals(TournamentStatus.Otwarty))
                .count()
        );
        stats.setTournamentsWon((int) user.getJoinedTournaments()
                .stream()
                .filter(tournament -> tournament.getWinner()
                        .getPlayers()
                        .contains(user))
                .count()
        );
        stats.setMatchesPlayed(user.getTeams().stream().mapToInt(Team::getPlayed).sum());
        stats.setMatchesWon(user.getTeams().stream().mapToInt(Team::getWins).sum());
        stats.setRoundsWon(user.getTeams().stream().mapToInt(Team::getRoundsWin).sum());
        stats.setRoundsPlayed(stats.getRoundsWon() + user.getTeams().stream().mapToInt(Team::getRoundsLost).sum());
        stats.setPointsScored(user.getJoinedTournaments()
                .stream()
                .mapToInt(tournament -> tournament.getMatches()
                        .stream()
                        .filter(match ->
                                match.getStatus().equals("Closed") && (match.getTeamOne().getPlayers().contains(user) || match.getTeamTwo().getPlayers().contains(user)))
                        .mapToInt(match -> {
                            if (match.getTeamOne().getPlayers().contains(user)) {
                                return match.getScoreOne();
                            } else if (match.getTeamTwo().getPlayers().contains(user)) {
                                return match.getScoreTwo();
                            } else {
                                return 0;
                            }
                        })
                        .sum())
                .sum()
        );
        stats.setMatchWinRatio(stats.getMatchesWon() / stats.getMatchesPlayed());

        Map<User, Long> users = new HashMap<>();

        user.getTeams().stream().map(team -> team.getPlayers()).flatMap(players -> players.stream()).filter(player -> !player.equals(user)).forEach(player -> {
            if (users.containsKey(player)) {
                users.put(user, users.get(player) + 1);
            } else {
                users.put(user, 1L);
            }
        });
        Optional<Map.Entry<User,Long>> mostPlayedUserEntry = users.entrySet().stream().max(Map.Entry.comparingByValue());
        if(mostPlayedUserEntry.isPresent()){
            stats.setMostPlayedWith(mostPlayedUserEntry.get().getKey().getUsername());
        } else {
            stats.setMostPlayedWith("Brak współgraczy w historii");
        }
        return stats;
    }

}
