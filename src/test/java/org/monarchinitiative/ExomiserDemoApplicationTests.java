package org.monarchinitiative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.monarchinitiative.exomiser.test.ExomiserStubDataConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExomiserDemoApplication.class, ExomiserStubDataConfig.class})
public class ExomiserDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
