package com.chamberos.chamberosapi.infrastructure.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.chamberos.chamberosapi.domain.model.User;
import com.chamberos.chamberosapi.domain.model.UserDetailsImpl;
import com.chamberos.chamberosapi.infrastructure.outputport.UserRepository;
import com.chamberos.chamberosapi.utils.HttpErrorMessages;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(HttpErrorMessages.USER_USERNAME_NOT_FOUND +  ": " + username);
        }

        return UserDetailsImpl.build(user);
    }
}
