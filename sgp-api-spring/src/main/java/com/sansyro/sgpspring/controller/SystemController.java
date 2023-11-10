package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.util.DateUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Bearer Auth")
public class SystemController {

    @ResponseBody
    @GetMapping("/status")
    public ResponseEntity statusApi() {
        return ResponseEntity.ok().body("Online em " + DateUtil.formatDate(new Date()));
    }

}
