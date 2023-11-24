package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.infrastructure.inputport.UserInputPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("user")
@Validated
public class UserAPI {
    @Autowired()
    UserInputPort customerInputPort;

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return new ResponseEntity<>(customerInputPort.register(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody User user) {
        User signedUser = customerInputPort.authenticate(user);
        if (signedUser == null) {
            return new ResponseEntity<>("Bad email or password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(signedUser, HttpStatus.OK);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(
            @RequestParam @NotNull(message = "Parameter 'userId' is required") String userId) {

        return customerInputPort.getById(userId);
    }

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll(
            @RequestParam(defaultValue = "") String fullName,
            @RequestParam(defaultValue = "fullName") String sortKey,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Sort.Direction direction = sortDirection == "asc" ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "fullName");
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerInputPort.getAll(fullName, pageable);
    }

    @GetMapping(value = "get-location-near", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByLocationNear(
            @RequestParam @NotNull(message = "Parameter 'latitude' is required") double latitude,
            @RequestParam @NotNull(message = "Parameter 'longitude' is required") double longitude,
            @RequestParam @NotNull(message = "Parameter 'radiusInKilometers' is required") double radiusInKilometers,
            @RequestParam @NotNull(message = "Parameter 'professionId' is required") String professionIds) {

        return new ResponseEntity<>(
                customerInputPort.findByLocationNear(latitude, longitude, radiusInKilometers, professionIds),
                HttpStatus.OK);
    }
}
