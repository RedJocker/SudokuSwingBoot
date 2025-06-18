package org.play.sudokuSwingBoot.gui;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


import java.awt.BorderLayout;


@Configuration
public class Beans {

	@Bean
	@Scope("prototype")
	public BorderLayout borderLayout() {
		return new BorderLayout();
	}
}
