package io.siha.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
