package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import java.util.List;
import javax.validation.Valid;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.chamberos.chamberosapi.domain.model.UserRole;
import com.chamberos.chamberosapi.infrastructure.inputport.RoleInputPort;

@RestController
@RequestMapping("role")
public class RoleAPI {
    @Autowired()
    RoleInputPort roleInputPort;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRole> create(@RequestBody @Valid UserRole role) {
        return new ResponseEntity<>(roleInputPort.save(role), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRole get(@RequestParam String roleId) {
        return roleInputPort.getById(roleId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRole> getAll(
            @RequestParam(defaultValue = "name") String sortKey,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort.Direction direction = sortDirection == "asc" ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return roleInputPort.getAll(name, pageable);
    }
}
