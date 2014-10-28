/*
 * Copyright 2013 Himanshu Bhardwaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.himanshu.spring.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/hello")
@ResponseBody
public class HelloWorldController {
	
	@Value(value="${sample.anno}")
	private String dummySampleInjected;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String hello() {
    	System.out.println("Inside hello");
    	return "hardcoded in java file";
    }
    
    @RequestMapping(value = "/injected", method = RequestMethod.GET)
    public String helloInjected() {
    	System.out.println("Inside helloInjected "+dummySampleInjected);
    	return dummySampleInjected;
    }

}
