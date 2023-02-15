package com.chamberos.chamberosapi.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.infrastructure.inputport.UserInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.UserRepository;

@Component
public class UserUseCase implements UserInputPort {

    @Autowired(required=true)
    UserRepository userRepository;

    @Override
    public User register(User user) {
        String hashedPassword = user.getEncodePass();
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getById(String userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User authenticate(User user) {
        String email = user.getEmail();
        String password = user.getEncodePass();
        User signedUser = userRepository.findByEmailAndPassword(email, password);
        return signedUser;
    }
}
