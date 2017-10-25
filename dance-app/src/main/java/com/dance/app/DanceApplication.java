package com.dance.app;

import com.dance.service.DanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.dance", "com.workflow"})
public class DanceApplication {

	@Autowired
	private DanceService danceService;

	public static void main(String[] args) {
		SpringApplication.run(DanceApplication.class, args);
	}

	@PostConstruct
	public void startProcess() {
		danceService.startDancing("tango");
	}

}