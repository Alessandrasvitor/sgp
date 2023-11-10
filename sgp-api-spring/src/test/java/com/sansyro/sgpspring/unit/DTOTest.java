package com.sansyro.sgpspring.unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sansyro.sgpspring.build.CourseBuild;
import com.sansyro.sgpspring.build.LotteryBuild;
import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.entity.dto.TokenResponse;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import com.sansyro.sgpspring.exception.MessageError;
import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class DTOTest {

    @Test
    void UserDTOTest() {
        assertNotNull(UserDTO.mapper(UserBuild.getBuild()));
        assertNotNull(UserDTO.builder().init(FunctionalityEnum.HOME).toString());
        assertNotNull(UserDTO.builder().toString());
        assertNull(UserDTO.mapper(null));
    }

    @Test
    void UserTest() {
        assertNotNull(User.builder().token(RandomStringUtils.randomAlphanumeric(10)).toString());
        assertNotNull(User.mapper(UserDTO.mapper(UserBuild.getBuild())));
        assertNotNull(UserBuild.getBuild().toString());
        assertNull(User.mapper(null));
    }

    @Test
    void CourseDTOTest() {
        assertNotNull(CourseDTO.mapper(CourseBuild.getBuild()));
        assertNotNull(CourseDTO.mapper(CourseBuild.getBuild()).toString());
        assertNotNull(CourseDTO.builder().toString());
        assertNull(CourseDTO.mapper(null));
    }

    @Test
    void CourseTest() throws CloneNotSupportedException {
        Course course = CourseBuild.getBuild();
        course.setUser(UserBuild.getBuild());
        course.setEndDate(new Date());
        Course course2 = course.clone();
        Course course3 = CourseBuild.getBuild();
        course2.setUser(course.getUser());
        assertNotNull(Course.builder().toString());
        assertNotNull(Course.mapper(CourseDTO.mapper(course)));
        assertNotNull(course.toString());
        assertNotNull(course.hashCode());
        assertFalse(course.equals(null));
        assertTrue(course.equals(course2));
        assertFalse(course.equals(course3));
        assertFalse(course.equals(CourseDTO.mapper(CourseBuild.getBuild())));
        course2.setName(RandomStringUtils.randomAlphabetic(10));
        course3.setName(course.getName());
        assertFalse(course.equals(course2));
        assertFalse(course.equals(course3));
        assertNull(Course.mapper(null));

    }

    @Test
    void LotteryDTOTest() {
        assertNotNull(LotteryDTO.mapper(LotteryBuild.getBuild()));
        assertNotNull(LotteryDTO.mapper(LotteryBuild.getBuild()).toString());
        assertNotNull(LotteryDTO.builder().toString());
    }

    @Test
    void LotteryTest() {
        Lottery lottery = LotteryBuild.getBuild();
        lottery.setType(TypeLotteryEnum.LOTECA);
        assertNotNull(Lottery.builder().toString());
        assertNotNull(Lottery.builder().user(UserBuild.getBuild()).build());
        assertNotNull(Lottery.mapper(LotteryDTO.mapper(lottery)));
        assertNull(Lottery.mapper(LotteryDTO.mapper(lottery)).getUser());
    }

    @Test
    void TokenResponseTest() {
        assertNotNull(TokenResponse.builder().build().toString());
        assertNotNull(TokenResponse.builder().toString());
    }

    @Test
    void InstituitionTest() {
        assertNotNull(Instituition.builder().toString());
    }

    @Test
    void MessageErrorTest() {
        assertNotNull(MessageError.builder().toString());
    }

}
