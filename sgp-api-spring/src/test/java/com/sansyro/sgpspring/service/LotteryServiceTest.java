package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.LotteryRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceTest {

    @InjectMocks
    private LotteryService service;

    @Mock
    private UserService userservice;

    @Mock
    private LotteryRepository repository;

    @Mock
    private PageRequest pageRequest;

    private final Long ID = 1L;

    private User userBuild;

    @BeforeEach
    void setUp() {
        userBuild = UserBuild.getBuild();
    }

    @Test
    void listAllTest() {
        when(userservice.getUserLogger()).thenReturn(userBuild);
        when(repository.list(anyLong())).thenReturn(Collections.emptyList());
        assertNotNull(service.list());
        verify(repository, times(1)).list(anyLong());
    }

    @Test
    void listAllNotUserTest() {
        when(userservice.getUserLogger()).thenReturn(null);
        assertNotNull(service.list());
    }

    @Test
    void listAllNotUserIdTest() {
        when(userservice.getUserLogger()).thenReturn(new User());
        assertNotNull(service.list());
    }

    @Test
    void listTest() {
        when(userservice.getUserLogger()).thenReturn(userBuild);
        lenient().when(repository.list(anyLong(), any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        assertNotNull(service.list(pageRequest));
        verify(repository, times(1)).list(anyLong(), any());
    }

    @Test
    void listNotUserTest() {
        when(userservice.getUserLogger()).thenReturn(null);
        assertNotNull(service.list(pageRequest));
    }

    @Test
    void listNotUserIdTest() {
        when(userservice.getUserLogger()).thenReturn(new User());
        assertNotNull(service.list(pageRequest));
    }

    @Test
    void getByIdTest() {
        when(repository.findById(any())).thenReturn(Optional.of(service.generate(TypeLotteryEnum.LOTECA)));
        assertNotNull(service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getByIdWithErrorTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getById(ID));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void saveTest() {
        Lottery lottery = service.generate(TypeLotteryEnum.DUPLA_SENA);
        when(repository.save(any())).thenReturn(lottery);
        service.save(LotteryDTO.mapper(lottery));
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveWithErrorNotBetTest() {
        assertThrows(ServiceException.class, () -> service.save(new LotteryDTO()));
    }

    @Test
    void saveWithErrorNotTypeTest() {
        LotteryDTO lottery = new LotteryDTO();
        lottery.setBet(RandomStringUtils.randomAlphabetic(8));
        assertThrows(ServiceException.class, () -> service.save(lottery));
    }

    @Test
    void updateTest() {
        Lottery lottery = service.generate(TypeLotteryEnum.MEGA_SENA);
        when(repository.findById(any())).thenReturn(Optional.of(lottery));
        when(repository.save(any())).thenReturn(lottery);
        assertNotNull(service.update(ID, LotteryDTO.mapper(lottery)));
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateWithErrorNotTypeTest() {
        Lottery lottery = new Lottery();
        lottery.setBet(RandomStringUtils.randomAlphabetic(8));
        assertThrows(ServiceException.class, () -> service.update(ID, LotteryDTO.mapper(lottery)));
    }

    @Test
    void deleteTest() {
        service.delete(ID);
        verify(repository, times(1)).deleteById(any());
    }

}
