package org.cetinsoft.akka.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;

public class SpringActorProducer implements IndirectActorProducer {
	static final Logger logger = LoggerFactory.getLogger(SpringActorProducer.class);
	private final ApplicationContext applicationContext;
	private final String actorBeanName;
	private final Class<? extends Actor> actorBean;

	public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName) {
		this.applicationContext = applicationContext;
		this.actorBeanName = actorBeanName;
		this.actorBean = null;
	}

	public SpringActorProducer(ApplicationContext applicationContext, Class<? extends Actor> actorBean) {
		this.applicationContext = applicationContext;
		this.actorBean = actorBean;
		this.actorBeanName = null;
	}

	@Override
	public Actor produce() {
		try {
			if (actorBean != null)
				return (Actor) applicationContext.getBean(actorBean);
			else
				return (Actor) applicationContext.getBean(actorBeanName);
		} catch (Throwable e) {
			System.out.print("Could not get actor bean");
			e.printStackTrace();
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Actor> actorClass() {
		if (actorBean != null) {
			return actorBean;
		}
		return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
	}
}