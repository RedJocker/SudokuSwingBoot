package org.play.sudokuSwingBoot.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.qqwing.QQWing;

@Configuration
public class ServiceBeans {

	@Bean
	@Scope("singleton")
	public QQWing qqwing() {
		return new QQWing();
	}
}
