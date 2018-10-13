package com.michalowicz.inzynierka.repository;

import com.michalowicz.inzynierka.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
