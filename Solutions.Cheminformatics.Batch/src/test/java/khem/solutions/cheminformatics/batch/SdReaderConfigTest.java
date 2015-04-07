package khem.solutions.cheminformatics.batch;

import khem.solutions.cheminformatics.data.Molecule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/khem-spring-batch-test.xml"})
public class SdReaderConfigTest
{
	Molecule m;
	
	@Autowired
	PollableChannel output;

	@Autowired
	ConfigurableApplicationContext applicationContext;

	@Test
	public void test() {
		applicationContext.start();
		Message message = output.receive(10000);
		
		while(message == null)
		{
			message =output.receive(10000);
		}
		Assert.assertNotNull(message);
		Assert.assertTrue(message.getPayload() instanceof Iterable);
	}

}
