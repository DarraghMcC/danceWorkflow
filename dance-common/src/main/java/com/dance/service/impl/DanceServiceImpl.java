package com.dance.service.impl;

import com.dance.config.Dance;
import com.dance.service.DanceService;
import com.workflow.service.WorkflowEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DanceServiceImpl implements DanceService {

	private final WorkflowEngineService workflowEngineService;

	@Autowired
	public DanceServiceImpl(WorkflowEngineService workflowEngineService) {
		this.workflowEngineService = workflowEngineService;
	}

	@Override
	public void startDancing(final String danceName) {
		final Dance dance = Dance.getDanceByName(danceName);

		this.workflowEngineService.startWorkflow(dance.getProcessName());
	}
}
