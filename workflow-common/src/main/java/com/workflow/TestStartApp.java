package com.workflow;


import com.workflow.service.WorkflowEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TestStartApp {

	@Autowired
	private WorkflowEngineService workflowEngineService;

	public static void main(String[] args) {
		SpringApplication.run(TestStartApp.class, args);
	}

	@PostConstruct
	public void startProcess() {
		workflowEngineService.startWorkflow("test_flow");
	}

}