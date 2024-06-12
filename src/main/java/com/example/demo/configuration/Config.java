package com.example.demo.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
@Setter
public class Config {


	@Value( "${live-programma.url}" )
	private String urlProgram;

	@Value( "${stoixima-live.url}" )
	private String urlLive;

	@Value( "${waittimeoutSeconds}" )
	private int waittimeoutSeconds;



}
