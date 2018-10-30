package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TournamentDao extends JpaRepository<Tournament, Long> {
    Set<Tournament> getAllByOwner(User user);
}
