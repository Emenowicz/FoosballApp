package com.michalowicz.inzynierka.repository;

import com.michalowicz.inzynierka.entity.UsergroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsergroupDao extends JpaRepository<UsergroupModel, Long> {
    UsergroupModel findByName(String name);
}
