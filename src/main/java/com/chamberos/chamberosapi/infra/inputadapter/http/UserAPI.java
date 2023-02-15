package com.chamberos.chamberosapi.infra.inputadapter.http;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chamberos.chamberosapi.domain.Location;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.infra.inputport.UserInputPort;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("user")
public class UserAPI {
    @Autowired(required = true)
    UserInputPort customerInputPort;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestParam String fullName, @RequestParam String password, @RequestParam Location location) {
        User user = new User("", fullName, password, location);
        return customerInputPort.createUser(user);
    }

    @PostMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@RequestParam String userId) {
        return customerInputPort.getById(userId);
    }

    @PostMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return customerInputPort.getAll();
    }
}
