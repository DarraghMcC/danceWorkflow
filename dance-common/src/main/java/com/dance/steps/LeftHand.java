package com.dance.steps;

import com.workflow.task.AbstractJavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LeftHand extends AbstractJavaDelegate{

	private static final Logger LOG = LoggerFactory.getLogger(LeftHand.class);

	@Override
	protected void doExecute(String instanceId, DelegateExecution execution) throws Exception {
		LOG.info("Left hand up");
	}
}
