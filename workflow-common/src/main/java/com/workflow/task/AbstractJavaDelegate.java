package com.workflow.task;


import com.common.timer.ProcessTimer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJavaDelegate implements JavaDelegate {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractJavaDelegate.class);

	@Override
	public final void execute(final DelegateExecution execution) throws Exception {
		final String instanceId = execution.getProcessInstanceId();
		final ProcessTimer processTimer = new ProcessTimer().startOverall();

		LOG.trace("[WORKFLOW][InstanceID:{}][ExecutionID:{}] - Executing Service task (java delegate): {}.",
				instanceId, execution.getId(), this.getClass().getSimpleName());

		this.doExecute(instanceId, execution);
		processTimer.stopOverall();

		LOG.debug("[WORKFLOW][InstanceID:{}][ExecutionID:{}] - Execution of Service task (java delegate): {} done in {}.",
				instanceId, execution.getId(), this.getClass().getSimpleName(), processTimer.report());
	}

	protected abstract void doExecute(final String instanceId, final DelegateExecution execution) throws Exception;

}
