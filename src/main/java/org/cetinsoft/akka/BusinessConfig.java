/*
 * Copyright (c) 2020 Bahattin Cetin <bhttnctn[AT]gmail[DOT]com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cetinsoft.akka;

import org.cetinsoft.akka.actor.BusinessActor;
import org.cetinsoft.akka.spring.SpringExtension;
import org.cetinsoft.akka.spring.SpringExtension.SpringExtensionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@Configuration
public class BusinessConfig {

	public static final String ACTOR_SYSTEM = "cetinsoftActorSystem";
	public static final String SPRING_EXT = "cetinsoftSpringExt";
	public static final String BUSINESS_SERVICE = "cetinsoftBusinessService";
	public static final String BUSINESS_ACTOR = "cetinsoftBusinessActor";

	private ActorSystem actorSystem;

	/** */
	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = ACTOR_SYSTEM)
	public ActorSystem actorSystem() {
		System.out.println(getClass().getSimpleName().concat(" = ").concat("ACTOR_SYSTEM bean initialized !!!"));
		actorSystem = ActorSystem.create(ACTOR_SYSTEM);
		return actorSystem;
	}

	@Bean(name = SPRING_EXT)
	@DependsOn({ ACTOR_SYSTEM })
	public SpringExtensionImpl springExtension() {
		System.out.println(getClass().getSimpleName().concat(" = ").concat("SPRING initialized !!!"));
		SpringExtensionImpl springExtension = SpringExtension.instance.get(actorSystem());
		springExtension.initialize(applicationContext);
		return springExtension;
	}

	@Bean(name = BUSINESS_ACTOR)
	@DependsOn({ ACTOR_SYSTEM, SPRING_EXT, BUSINESS_SERVICE })
	public ActorRef businessActor(SpringExtensionImpl springExtension) {
		System.out.println(getClass().getSimpleName().concat(" = ").concat("BUSINESS_ACTOR bean initialized !!!"));
		Props actorBackendProps = springExtension.props(BusinessActor.class);
		return actorSystem.actorOf(actorBackendProps, BUSINESS_ACTOR);
	}
}