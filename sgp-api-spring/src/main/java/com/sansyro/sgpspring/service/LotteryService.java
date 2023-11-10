package com.sansyro.sgpspring.service;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_FIELDS_NOT_FILLED;
import static com.sansyro.sgpspring.constants.MessageEnum.MSG_LOTERY_NOT_FOUND;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.LotteryRepository;
import com.sansyro.sgpspring.util.DateUtil;
import com.sansyro.sgpspring.util.GeneralUtil;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserService userService;

    public LotteryService() {
    }

    public List<Lottery> list() {
        User user = userService.getUserLogger();
        if (nonNull(user) && nonNull(user.getId())) {
            return lotteryRepository.list(user.getId());
        }
        return Collections.emptyList();
    }

    public Page<Lottery> list(Pageable pageable) {
        User user = userService.getUserLogger();
        if (nonNull(user) && nonNull(user.getId())) {
            return lotteryRepository.list(user.getId(), pageable);
        }
        return new PageImpl<>(Collections.emptyList());
    }

    public Lottery getById(Long id) {
        Optional<Lottery> betOp = lotteryRepository.findById(id);
        if (betOp.isPresent()) {
            return betOp.get();
        }
        throw new ServiceException(MSG_LOTERY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public Lottery save(LotteryDTO dto) {
        validate(dto);
        Lottery lottery = Lottery.mapper(dto);
        lottery.setUser(userService.getUserLogger());
        return lotteryRepository.save(lottery);
    }

    public Lottery update(Long id, LotteryDTO lottery) {
        validate(lottery);
        Lottery lotteryUpdate = getById(id);
        lotteryUpdate.setBet(lottery.getBet());
        lotteryUpdate.setWon(lottery.getWon());
        lotteryUpdate.setResult(lottery.getResult());
        lotteryUpdate.setResultDate(DateUtil.formatDate(lottery.getResultDateType()));
        lotteryUpdate.setPaidOut(lottery.getPaidOut());

        return lotteryRepository.save(lotteryUpdate);
    }

    public void delete(Long id) {
        lotteryRepository.deleteById(id);
    }

    private void validate(LotteryDTO lottery) {
        if (GeneralUtil.stringNullOrEmpty(lottery.getBet())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
        if (isNull(lottery.getType())) {
            throw new ServiceException(MSG_FIELDS_NOT_FILLED);
        }
        if (isNull(lottery.getLotteryDateType())) {
            lottery.setLotteryDateType(new Date());
        }
    }

    public Lottery generate(TypeLotteryEnum typeLottery) {
        return Lottery.builder()
            .bet(GeneralUtil.getBet(typeLottery))
            .type(typeLottery)
            .build();
    }
}
