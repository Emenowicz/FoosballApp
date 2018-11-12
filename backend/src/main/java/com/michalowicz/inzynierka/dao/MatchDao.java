package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDao extends JpaRepository<Match, Long> {
    Match getById(Long id);
    List<Match> getAllByStatus(String status);
}
