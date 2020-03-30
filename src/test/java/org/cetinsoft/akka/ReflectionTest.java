package org.cetinsoft.akka;

import static org.fest.reflect.core.Reflection.field;

import java.util.concurrent.TimeUnit;

import org.cetinsoft.akka.actor.BusinessActor;
import org.cetinsoft.akka.service.IBusinessService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import scala.concurrent.duration.FiniteDuration;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ReflectionTest {
	static ActorSystem actorSystem = ActorSystem.create("cetinsoft-test-system");

	@Mock
	protected IBusinessService mockService;

	private TestKit probe;
	private TestActorRef<BusinessActor> businessActor;

	@Before
	public void setup() {
		probe = new TestKit(actorSystem);
		businessActor = TestActorRef.create(actorSystem, Props.create(BusinessActor.class), "cetinsoftBusinessActor");

		field("actorCell._actor.businessService").ofType(IBusinessService.class).in(businessActor).set(mockService);
	}

	@After
	public void clean() {
		actorSystem.stop(businessActor);
	}

	@Test
	public void springInjectionTest() {

		System.out.println("springInjectionTest START !!!");

		Mockito.when(mockService.skipFunction()).thenReturn("Mock message injected !!!");

		businessActor.tell(new Object(), probe.getRef());

		String response = (String) probe.receiveOne(new FiniteDuration(500, TimeUnit.MILLISECONDS));

		System.out.println("RESPONSE = " + response);

		System.out.println("springInjectionTest END !!!");
	}
}
