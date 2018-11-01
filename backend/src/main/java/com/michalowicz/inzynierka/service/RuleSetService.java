package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.entity.RuleSet;
import com.michalowicz.inzynierka.entity.RuleSetType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleSetService {
    @Resource
    RuleSetDao ruleSetDao;

    public List<RuleSet> getDefaultRuleSets(){
        return ruleSetDao.findAllByRuleSetTypeIs(RuleSetType.DEFAULT);
    }
}
