package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.Usergroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsergroupDao extends JpaRepository<Usergroup, Long> {
    Usergroup findByNameIgnoreCase(String name);
}
