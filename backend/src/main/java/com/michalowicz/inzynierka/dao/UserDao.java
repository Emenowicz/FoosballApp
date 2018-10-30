package com.michalowicz.inzynierka.dao;

import com.michalowicz.inzynierka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
