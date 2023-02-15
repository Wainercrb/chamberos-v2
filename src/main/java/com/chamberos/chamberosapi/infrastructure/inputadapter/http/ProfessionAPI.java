package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import java.util.List;
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
import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.infrastructure.inputport.ProfessionInputPort;

@RestController
@RequestMapping("profession")
public class ProfessionAPI {
    @Autowired()
    ProfessionInputPort professionInputPort;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profession> create(@RequestBody Profession profession) {
        return new ResponseEntity<>(professionInputPort.save(profession), HttpStatus.CREATED);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Profession get(@RequestParam String professionId) {
        return professionInputPort.getById(professionId);
    }

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Profession> getAll(
            @RequestParam(defaultValue = "name") String sortKey,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort.Direction direction = sortDirection == "asc" ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return professionInputPort.getAll(name, pageable);
    }

}
