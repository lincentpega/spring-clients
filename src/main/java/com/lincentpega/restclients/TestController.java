package com.lincentpega.restclients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<Response> hello(@RequestParam(value = "name", defaultValue = "Pennie") String name) {
        return ResponseEntity.ok(new Response(14, name));
    }
}
