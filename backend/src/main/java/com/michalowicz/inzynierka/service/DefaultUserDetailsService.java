package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Resource
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Zły login lub hasło");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(),
                        user.getUsergroups()
                                .stream()
                                .map(usergroup -> new SimpleGrantedAuthority("ROLE_" + usergroup.getName()
                                        .toUpperCase()))
                                .collect(Collectors.toList())
        );
    }
}
