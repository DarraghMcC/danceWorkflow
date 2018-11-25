package com.dance.service;

import com.dance.config.Dance;
import com.dance.service.impl.DanceServiceImpl;
import com.workflow.service.impl.WorkflowEngineServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DanceServiceTest {

	private DanceService danceService;

	@Mock
	WorkflowEngineServiceImpl workflowEngineService;

	@Before
	public void initiateMocks(){
		danceService = new DanceServiceImpl(workflowEngineService);
	}

	@Test
	public void validDance(){
		danceService.startDancing("tango");
		verify(workflowEngineService, times(1)).startWorkflow(Dance.TANGO.getProcessName());
	}

	@Test
	public void validDance_allCaps(){
		danceService.startDancing("TANGO");
		verify(workflowEngineService, times(1)).startWorkflow(Dance.TANGO.getProcessName());
	}

	@Test
	public void invalidDance(){
		danceService.startDancing("bleh");
		verify(workflowEngineService, times(1)).startWorkflow(Dance.WALL_HUGGER.getProcessName());
	}

}
