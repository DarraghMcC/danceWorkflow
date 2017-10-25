package org.workflow;

import com.workflow.TestStartApp;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestStartApp.class)
public class InitialTest {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Test
	public void verifyProcessInstanceStarted() {
		Task task = taskService.createTaskQuery().taskName("Test_task").singleResult();
		assertThat(task, is(notNullValue()));
		taskService.complete(task.getId());

		assertThat(runtimeService.createProcessInstanceQuery().count(), is(0L));
	}

}