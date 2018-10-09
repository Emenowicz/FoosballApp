package com.michalowicz.inzynierka.repository;

import com.michalowicz.inzynierka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
