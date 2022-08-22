package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.entity.Usuario;
import com.sansyro.sgpspring.entity.dto.UsuarioRequest;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.UsuarioService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok().body(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body( usuarioService.buscar(id).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody Usuario usuario) {
        try {
            usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody UsuarioRequest usuario) {
        try {
            return ResponseEntity.ok().body(usuarioService.alterar(id, usuario).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity alterarSenha(@PathVariable Long id, @RequestBody String senha) {
        try {
            return ResponseEntity.ok().body(usuarioService.alterarSenha(id, senha).mapperDTP());
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/senha/{id}")
    public ResponseEntity buscarComSenha(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(usuarioService.buscar(id));
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
