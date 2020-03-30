package org.cetinsoft.akka;

import static akka.pattern.Patterns.ask;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import akka.actor.ActorRef;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessAppMain.class)
@ComponentScan(basePackages = "org.cetinsoft.akka")
public class FunctionalTest {

	@Autowired
	@Qualifier("cetinsoftBusinessActor")
	protected ActorRef businessActor;

	@Test
	public void businessServiceTest() throws Exception {

		System.out.println("businessServiceTest START !!!");

		FiniteDuration duration = Duration.create(200, TimeUnit.SECONDS);
		Future<Object> result = ask(businessActor, "", Timeout.durationToTimeout(duration));
		Object actualResultContent = Await.result(result, duration);
		System.out.println("FunctionalTest.businessServiceTest RESULT = " + actualResultContent);

		System.out.println("businessServiceTest END !!!");
	}
}
