package com.dance.steps;

import com.workflow.task.AbstractJavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class RightFoot extends AbstractJavaDelegate{

	private static final Logger LOG = LoggerFactory.getLogger(RightFoot.class);

	@Override
	protected void doExecute(String instanceId, DelegateExecution execution) throws Exception {
		LOG.info("Right foot forward");
	}
}
