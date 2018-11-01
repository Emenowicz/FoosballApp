package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.service.RuleSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rulesets")
public class RuleSetController {

    @Resource
    RuleSetService ruleSetService;

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ResponseEntity getDefaultRuleSets() {
        return new ResponseEntity(ruleSetService.getDefaultRuleSets(), HttpStatus.OK);
    }
}
