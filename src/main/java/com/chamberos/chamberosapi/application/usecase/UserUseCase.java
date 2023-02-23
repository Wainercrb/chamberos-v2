package com.chamberos.chamberosapi.application.usecase;

import java.util.List;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.model.User;
import com.chamberos.chamberosapi.domain.model.UserRole;
import com.chamberos.chamberosapi.infrastructure.inputport.UserInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.RefreshTokenRepository;
import com.chamberos.chamberosapi.infrastructure.outputport.UserRepository;
import com.chamberos.chamberosapi.infrastructure.outputport.RoleRepository;
import com.chamberos.chamberosapi.infrastructure.security.jwt.JwtUtils;
import com.chamberos.chamberosapi.utils.CustomObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

@Component
public class UserUseCase implements UserInputPort {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired(required = true)
    UserRepository userRepository;

    @Autowired(required = true)
    RoleRepository userRoleRepository;

    @Autowired(required = true)
    RefreshTokenRepository refreshTokenRepository;

    @Autowired(required = true)
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        List<UserRole> roles = user
                .getRoles()
                .stream()
                .reduce(new ArrayList<>(), (acc, role) -> {
                    UserRole storedRole = userRoleRepository.findByName(role.getName());
                    if (storedRole != null) {
                        acc.add(storedRole);
                    }
                    return acc;
                }, (acc1, acc2) -> {
                    acc1.addAll(acc2);
                    return acc1;
                });

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User getById(String userId) {
        return userRepository.getById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAll(String fullName, Pageable pageable) {
        return userRepository.findByFullName(fullName, pageable);
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
