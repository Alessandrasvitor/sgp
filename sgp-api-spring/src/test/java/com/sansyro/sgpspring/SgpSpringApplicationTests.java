package com.sansyro.sgpspring;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SgpSpringApplicationTests {

	@Test
	void main() {
		try (MockedStatic<SpringApplication> utilities = Mockito.mockStatic(SpringApplication.class)) {
			utilities.when(() -> SpringApplication.run(SgpSpringApplication.class))
					.thenReturn(null);
			SgpSpringApplication.main(new String[]{"0"});
			assertNotNull(new SgpSpringApplication());
		}
	}

}
