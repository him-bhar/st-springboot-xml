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
package com.himanshu.spring.configurer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class CustomPropertyConfigurer extends PropertySourcesPlaceholderConfigurer {
	
	private DefaultResourceLoader resourceLoader;
	
	public CustomPropertyConfigurer() {
		this.resourceLoader = new DefaultResourceLoader();
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		System.out.println("Load file paths");
		StandardEnvironment env = arg0.getBean(StandardEnvironment.class);
		MutablePropertySources propSources = env.getPropertySources();
		for (String filePath : filePaths()) {
			Resource res = resourceLoader.getResource(filePath);
			if (res.exists() && res != null) {
				try {
					Properties props = PropertiesLoaderUtils.loadProperties(res);
					//PropertySourcesLoader l = new PropertySourcesLoader();
					//l.load(new ClassPathResource(filePath));
					PropertiesPropertySource source = new PropertiesPropertySource("app", props);
					System.out.printf("Props loaded from %s is %s\n", filePath, props.toString());
					propSources.addFirst(source);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		super.setPropertySources(propSources);
		super.postProcessBeanFactory(arg0);
	}
	
	private List<String> filePaths() {
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("application.properties");
		filePaths.add("config/custom-app.properties");
		filePaths.add("config/custom-app2.properties");
		return filePaths;
	}

}
