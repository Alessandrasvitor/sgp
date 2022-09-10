package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.util.DataUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))

public class SystemController {

    @ResponseBody
    @GetMapping("/status")
    public ResponseEntity statusApi() {
        return ResponseEntity.ok().body("Online em " + DataUtil.formataDataPadrao(new Date()));
    }

}
