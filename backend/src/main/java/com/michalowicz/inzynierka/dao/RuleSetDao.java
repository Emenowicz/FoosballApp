package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.RuleSet;
import com.michalowicz.inzynierka.entity.RuleSetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleSetDao extends JpaRepository<RuleSet, Long> {
    public List<RuleSet> findAllByRuleSetTypeIs(final RuleSetType ruleSetType);
}
