package com.chamberos.chamberosapi.application;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.utils.CustomObjectId;
import com.chamberos.chamberosapi.infrastructure.inputport.UserInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.UserRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

@Component
public class UserUseCase implements UserInputPort {

    @Autowired(required = true)
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getById(String userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getAll(String fullName, Pageable pageable) {
        return userRepository.findByFullName(fullName, pageable);
    }

    @Override
    public User authenticate(User user) {
        User storedUser = userRepository.findByEmail(user.getEmail());
        Boolean matched = passwordEncoder.matches(user.getPassword(), storedUser.getPassword());
        storedUser.setPassword(null);

        return matched == false ? null : storedUser;
    }

    @Override
    public List<User> findByLocationNear(
            double latitude,
            double longitude,
            double radiusInKilometers,
            String professionIds) {
        Point location = new Point(longitude, latitude);

        Distance distance = new Distance(radiusInKilometers, Metrics.KILOMETERS);

        CustomObjectId customObjectId = new CustomObjectId();

        List<ObjectId> professions = customObjectId.getObjectIdFromString(professionIds);

        return userRepository.findByLocationNearAndProfessionsIn(location, distance, professions);
    }
}
