package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleSetDao extends JpaRepository<RuleSet, Long> {
}
