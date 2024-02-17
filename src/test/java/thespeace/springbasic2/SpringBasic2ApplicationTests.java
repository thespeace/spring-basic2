package thespeace.springbasic2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {AutoAppConfig.class})
class SpringBasic2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
