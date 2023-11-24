package com.chamberos.chamberosapi.domain;

import java.util.List;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UserTest {
    @Test
    void shouldRenderTheCorrectUserProperties() {
        String id = "1";
        String fullName = "user test";
        String email = "test@gmail.com";
        String password = "pass1234";
        Boolean isActive = false;
        Point location = new Point(0, 0);
        List<Profession> professionList = new LinkedList<Profession>();
        List<UserRole> rolesList = new LinkedList<UserRole>();

        User user = new User(
                id,
                fullName,
                email,
                password,
                isActive,
                location,
                professionList,
                rolesList
        );

        assertEquals(id, user.getId());
        assertEquals(fullName, user.getFullName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
        assertEquals(location, user.getLocation());
        assertEquals(professionList, user.getProfessions());
        assertEquals(rolesList, user.getRoles());
    }
}
