package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.constants.TypeBetEnum;
import com.sansyro.sgpspring.entity.Bet;
import com.sansyro.sgpspring.entity.dto.BetResponse;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.BetRepository;
import com.sansyro.sgpspring.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserService userService;

    public List<Bet> list() {
        return betRepository.findAll(Sort.by(Sort.Direction.ASC, "betDate"));
    }

    public Bet getById(Long id) {
        Optional<Bet> betOp = betRepository.findById(id);
        if(betOp.isPresent()) {
            return betOp.get();
        }
        throw new ServiceException("Aposta não encontrada");
    }

    public Bet save(Bet bet) {
        validateNotNull(bet);
        bet.setUser(userService.getById(bet.getUser().getId()));
        return betRepository.save(bet);
    }

    public Bet update(Long id, Bet bet) {
        validateNotNull(bet);
        Bet betUpdate = getById(id);
        betUpdate.setType(bet.getType());
        betUpdate.setBet(bet.getBet());
        betUpdate.setBetDate(bet.getBetDate());
        betUpdate.setLucre(bet.getLucre());
        betUpdate.setResult(bet.getResult());
        betUpdate.setResultDate(bet.getResultDate());
        betUpdate.setPaidOut(bet.getPaidOut());
        betUpdate.setUser(userService.getById(bet.getUser().getId()));

        return betRepository.save(betUpdate);
    }

    public void delete(Long id) {
        betRepository.deleteById(id);
    }

    private void validateNotNull(Bet bet) {
        if(GeralUtil.stringNullOrEmpty(bet.getBet())){
            throw new ServiceException("Aposta é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(bet.getType())){
            throw new ServiceException("O tipo da aposta é obrigatório");
        }
    }

    public Bet gerar() {

        Bet bet = new Bet().toBuilder()
                .betDate(new Date())
                .bet(GeralUtil.getBet(6, 60))
                .type(TypeBetEnum.MEGA_SENA.getValue())
                .build();

        return bet;
    }
}
