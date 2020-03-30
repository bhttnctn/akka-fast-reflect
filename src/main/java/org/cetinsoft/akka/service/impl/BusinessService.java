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

package org.cetinsoft.akka.service.impl;

import javax.annotation.PostConstruct;

import org.cetinsoft.akka.service.IBusinessService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Service("cetinsoftBusinessService")
@DependsOn({ "cetinsoftActorSystem", "cetinsoftSpringExt" })
public class BusinessService implements IBusinessService {

	@PostConstruct
	public void initialize() {
		System.out.println(getClass().getSimpleName().concat(" = ").concat("BusinessService is initialized !!!"));
	}

	@Override
	public void doBusiness(Object o) {
		System.out.println(getClass().getSimpleName().concat(" = ").concat("Doing business with = " + o));
	}

	@Override
	public String skipFunction() {
		String message = "Skip this function in testing";
		System.out.println(getClass().getSimpleName().concat(" = ").concat(message));
		return message;
	}
}
