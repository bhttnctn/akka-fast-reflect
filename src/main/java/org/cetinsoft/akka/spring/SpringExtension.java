package org.cetinsoft.akka.spring;

import org.springframework.context.ApplicationContext;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.Props;

public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExtensionImpl> {
	public static final SpringExtension instance = new SpringExtension();

	@Override
	public SpringExtensionImpl createExtension(ExtendedActorSystem system) {
		return new SpringExtensionImpl();
	}

	public static class SpringExtensionImpl implements Extension {
		private volatile ApplicationContext applicationContext;

		public void initialize(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		public Props props(String actorBeanName) {
			return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
		}

		public Props props(Class<?> actorBean) {
			return Props.create(SpringActorProducer.class, applicationContext, actorBean);
		}

		public static String classNameToSpringName(Class<?> clazz) {
			String simpleName = clazz.getSimpleName();
			return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
		}
	}
}