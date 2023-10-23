package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.Bet;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.BetService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bet")
@PreAuthorize("hasAuthority('HOME')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class BetController {

    @Autowired
    private BetService betService;

    @ResponseBody
    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(betService.list());
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity list(@PageableDefault(sort = "betDate",
            direction = Sort.Direction.ASC,
            size = 5) Pageable pageable) {
        return ResponseEntity.ok().body(betService.list(pageable));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(betService.getById(id));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody Bet bet) {
        try {
            betService.save(bet);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Bet bet) {
        try {
            return ResponseEntity.ok().body(betService.update(id, bet));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            betService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("gerar")
    public ResponseEntity gerar() {
        try {
            return ResponseEntity.ok().body(betService.gerar());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
