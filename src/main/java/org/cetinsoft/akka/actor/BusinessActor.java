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

package org.cetinsoft.akka.actor;

import org.cetinsoft.akka.service.IBusinessService;
import org.cetinsoft.akka.spring.ActorComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import akka.actor.AbstractActorWithTimers;

@ActorComponent
public class BusinessActor extends AbstractActorWithTimers {

	@Autowired
	@Qualifier("cetinsoftBusinessService")
	private IBusinessService businessService;

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
				.matchAny(any -> handleAnyway(any)) //
				.build();
	}

	public void handleAnyway(Object o) {

		System.out.println(getClass().getSimpleName().concat(" = ").concat(businessService.getClass().getSimpleName())
				.concat(" = ").concat("Spring Service doBusiness will call here !!!"));
		businessService.doBusiness("Service called in this line !!!");

		System.out.println(getClass().getSimpleName().concat(" = ").concat(businessService.getClass().getSimpleName())
				.concat(" = ").concat("Service call skipFunction !!!"));
		String skipFunctionMessage = businessService.skipFunction();

		sender().tell(skipFunctionMessage, self());
	}
}
