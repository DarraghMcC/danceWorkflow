package com.workflow.service.impl;


import com.logging.annotation.InjectLogger;
import com.workflow.service.WorkflowEngineService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WorkflowEngineServiceImpl implements WorkflowEngineService {

	@InjectLogger
	private static Logger LOG;

	private final RuntimeService runtimeService;

	@Autowired
	public WorkflowEngineServiceImpl(final RuntimeService runtimeService){
		this.runtimeService = runtimeService;
	}

	@Override
	public String startWorkflow(String processId) {
		return runtimeService.startProcessInstanceByKey(processId).getProcessInstanceId();
	}


}
