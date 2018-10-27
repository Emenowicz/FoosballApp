package com.michalowicz.inzynierka.repository;

import com.michalowicz.inzynierka.entity.TournamentModel;
import com.michalowicz.inzynierka.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TournamentDao extends JpaRepository<TournamentModel, Long> {
    Set<TournamentModel> getAllByOwner(UserModel userModel);
}
