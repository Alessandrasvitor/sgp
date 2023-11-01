package com.sansyro.sgpspring;

import com.sansyro.sgpspring.build.UserBuild;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.util.EmailUtil;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SgpSpringApplicationTests {

//	@Test
//	void main() {
//		try (MockedStatic<SpringApplication> utilities = Mockito.mockStatic(SpringApplication.class)) {
//			utilities.when(() -> SpringApplication.run(SgpSpringApplication.class))
//					.thenReturn(null);
//			SgpSpringApplication.main(new String[]{"0"});
//			assertNotNull(new SgpSpringApplication());
//		}
//	}

	public static void main(String[] args) {

		User user = UserBuild.getBuild();
		user.setEmail("sansyro@gmail.com");
		EmailUtil.sendMail(user);

	}


}
