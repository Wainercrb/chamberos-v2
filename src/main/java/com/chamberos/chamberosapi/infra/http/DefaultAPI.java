package com.chamberos.chamberosapi.infra.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultAPI {
   
    @GetMapping("/")
    public ResponseEntity<String> getRoot() {
        return new ResponseEntity<>("Chamberos API", HttpStatus.OK);
    }
}
