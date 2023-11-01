package com.sansyro.sgpspring.controller;

import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.service.LotteryService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/lottery")
@PreAuthorize("hasAuthority('LOTTERY')")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @ResponseBody
    @GetMapping("/all")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(lotteryService.list().stream().map(LotteryDTO::mapper).toList());
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity list(@PageableDefault(sort = "lotteryDate",
            direction = Sort.Direction.ASC,
            size = 5) Pageable pageable) {
        return ResponseEntity.ok().body(lotteryService.list(pageable).map(LotteryDTO::mapper));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(LotteryDTO.mapper(lotteryService.getById(id)));
        } catch (ServiceException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity save(@RequestBody LotteryDTO lottery) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(LotteryDTO.mapper(lotteryService.save(lottery)));
        } catch (ServiceException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody LotteryDTO lottery) {
        try {
            return ResponseEntity.ok().body(LotteryDTO.mapper(lotteryService.update(id, lottery)));
        } catch (ServiceException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            lotteryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/generate/{typeLottery}")
    public ResponseEntity generate(@PathVariable String typeLottery) {
        try {
            return ResponseEntity.ok().body(LotteryDTO.mapper(lotteryService.generate(TypeLotteryEnum.valueOf(typeLottery))));
        } catch (ServiceException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessageError());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
